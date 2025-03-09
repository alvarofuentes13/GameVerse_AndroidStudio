package com.example.gustavioandroidstudio;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
    private RecyclerView juegosRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_juegos);

        EditText edtBuscar = findViewById(R.id.edtBuscar);
        juegosRecyclerView = findViewById(R.id.recyclerBuscar);

        if (juegosRecyclerView == null) {
            Log.e("ERROR", "RecyclerView no encontrado en activity_buscar_juegos.xml");
            return;
        }

        juegosRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        juegos = new ArrayList<>();
        apiService = ApiClient.getClient().create(ApiService.class);

        String query = "fields name,summary,cover.url; limit 500;";
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), query);

        apiService.getVideojuegos(requestBody).enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    juegos.clear();
                    juegos.addAll(response.body());

                    if (!juegos.isEmpty()) {
                        // Modificado para abrir ReviewActivity con datos reales
                        popularGamesAdapter = new PopularGamesAdapter(BuscarJuegosActivity.this, juegos, game -> {
                            // Verificar datos antes de enviarlos
                            String gameTitle = (game.getName() != null) ? game.getName() : "Título desconocido";
                            String gameYear = (game.getFirstReleaseDate() != null) ? game.getFirstReleaseDate() : "Fecha no disponible";
                            String gameImageUrl = (game.getCoverUrl() != null) ? game.getCoverUrl() : "";

                            // Log para verificar qué datos se están enviando
                            Log.d("DEBUG", "Juego seleccionado: " + gameTitle);
                            Log.d("DEBUG", "Fecha de lanzamiento: " + gameYear);
                            Log.d("DEBUG", "URL de imagen: " + gameImageUrl);

                            Intent intent = new Intent(BuscarJuegosActivity.this, ReviewActivity.class);
                            intent.putExtra("GAME_TITLE", gameTitle);
                            intent.putExtra("GAME_YEAR", gameYear);
                            intent.putExtra("GAME_IMAGE_URL", gameImageUrl);

                            startActivity(intent);
                        });
                        juegosRecyclerView.setAdapter(popularGamesAdapter);
                    } else {
                        Log.e("API_RESPONSE", "La API no devolvió juegos.");
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(BuscarJuegosActivity.this, PaginaPrincipal.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_search) {
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(BuscarJuegosActivity.this, PaginaUsuario.class));
                finish();
                return true;
            }
            return false;
        });

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
