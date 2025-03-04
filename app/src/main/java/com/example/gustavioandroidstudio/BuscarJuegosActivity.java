package com.example.gustavioandroidstudio;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gustavioandroidstudio.api.ApiClient;
import com.example.gustavioandroidstudio.api.ApiService;
import com.example.gustavioandroidstudio.api.Game;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarJuegosActivity extends AppCompatActivity {
    private PopularGamesAdapter popularGamesAdapter;
    private List<Game> juegos;
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_juegos);

        EditText edtBuscar = findViewById(R.id.edtBuscar);

        RecyclerView juegosRecyclerView = findViewById(R.id.popularGamesRecycler);
        juegosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        juegos = new ArrayList<>();

        apiService = ApiClient.getClient().create(ApiService.class);

        // ✅ Prepara la consulta en formato `RequestBody`
        String query = "fields name,summary,cover.url; limit 20;";
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), query);

        // ✅ Llamada correcta a la API
        Call<List<Game>> call = apiService.getVideojuegos(requestBody);


        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    juegos.clear();
                    juegos.addAll(response.body());

                    // ✅ Inicializa el adaptador con los juegos recibidos
                    popularGamesAdapter = new PopularGamesAdapter(BuscarJuegosActivity.this, juegos, game -> {
                        Intent intent = new Intent(BuscarJuegosActivity.this, GamesActivity.class);
                        intent.putExtra("game", game);
                        startActivity(intent);
                    });

                    juegosRecyclerView.setAdapter(popularGamesAdapter);

                    // ✅ Log para depuración
                    for (Game juego : juegos) {
                        Log.d("API_RESPONSE", "ID: " + juego.getId() +
                                ", Nombre: " + juego.getName() +
                                ", Descripción: " + juego.getSummary() +
                                ", Imagen: " + juego.getCoverUrl());
                    }
                } else {
                    Log.e("API_ERROR", "Error en la respuesta: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e("API_ERROR", "Error al obtener datos", t);
            }
        });

        // ✅ Configuración correcta del `BottomNavigationView`
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(BuscarJuegosActivity.this, PaginaPrincipal.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_search) {
                return true; // ✅ No hacer nada si ya estamos en esta pantalla
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(BuscarJuegosActivity.this, PaginaUsuario.class));
                return true;
            }
            return false;
        });

        // ✅ Filtrado en tiempo real
        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (popularGamesAdapter != null) {
                    popularGamesAdapter.filtrar(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
