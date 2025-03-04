package com.example.gustavioandroidstudio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gustavioandroidstudio.adapters.GameAdapter;
import com.example.gustavioandroidstudio.api.ApiClient;
import com.example.gustavioandroidstudio.api.ApiService;
import com.example.gustavioandroidstudio.api.Game;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaginaPrincipal extends AppCompatActivity {
    private ApiService apiService;
    private GameAdapter gameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        // Configurar el RecyclerView
        RecyclerView juegosRecyclerView = findViewById(R.id.popularGamesRecycler);
        juegosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Inicializar Retrofit
        apiService = ApiClient.getClient().create(ApiService.class);
        String query = "fields name,summary,cover.url; limit 20;";
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), query);

        // ✅ Llamada correcta a la API
        Call<List<Game>> call = apiService.getVideojuegos(requestBody);

        call.enqueue(new Callback<List<Game>>() { // ✅ Cambiar de Game a List<Game>
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Game> juegosList = response.body(); // ✅ Obtener la lista de juegos correctamente

                    for (Game juego : juegosList) {
                        Log.d("API_RESPONSE", "ID: " + juego.getId() +
                                ", Nombre: " + juego.getName() +
                                ", Descripción: " + juego.getSummary() +
                                ", Género: " + juego.getGenres() +
                                ", Fecha de lanzamiento: " + juego.getFirstReleaseDate() +
                                ", Imagen: " + juego.getCoverUrl());
                    }

                    // ✅ Configurar el adaptador y mostrar los juegos en RecyclerView
                    gameAdapter = new GameAdapter(PaginaPrincipal.this, juegosList, game -> {
                        Intent intent = new Intent(PaginaPrincipal.this, GamesActivity.class);
                        intent.putExtra("game", game);
                        startActivity(intent);
                    });
                    juegosRecyclerView.setAdapter(gameAdapter);
                } else {
                    Log.e("API_ERROR", "Error en la respuesta: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e("API_ERROR", "Error al obtener datos", t);
            }
        });

        // ✅ Configurar BottomNavigationView correctamente
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_search) {
                startActivity(new Intent(PaginaPrincipal.this, BuscarJuegosActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(PaginaPrincipal.this, PaginaUsuario.class));
                return true;
            }
            return false;
        });
    }
}
