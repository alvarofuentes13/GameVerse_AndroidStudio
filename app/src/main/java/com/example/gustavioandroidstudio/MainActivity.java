package com.example.gustavioandroidstudio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gustavioandroidstudio.api.ApiService;
import com.example.gustavioandroidstudio.api.ApiClient;
import com.example.gustavioandroidstudio.api.Game;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 2000; // Tiempo de la pantalla de carga (2 segundos)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // ✅ Pantalla de bienvenida

        // Cargar juegos después del Splash
        new Handler().postDelayed(this::fetchGames, SPLASH_TIME_OUT);
    }

    private void fetchGames() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        String query = "fields name,summary,cover.url; limit 20;";
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), query);
        Call<List<Game>> call = apiService.getVideojuegos(requestBody);

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Game> games = response.body();

                    // Log para depuración
                    for (Game game : games) {
                        Log.d("IGDB", "Juego: " + game.getName() + " - " + game.getSummary());
                    }

                    // ✅ Pasar la lista de juegos a `PaginaPrincipal`
                    Intent intent = new Intent(MainActivity.this, PaginaPrincipal.class);
                    intent.putExtra("games_list", (java.io.Serializable) games);
                    startActivity(intent);
                    finish(); // Cierra `MainActivity`
                } else {
                    Log.e("IGDB", "Error en la respuesta: " + response.errorBody());
                    showErrorAndExit();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e("IGDB", "Error en la conexión: " + t.getMessage());
                showErrorAndExit();
            }
        });
    }

    private void showErrorAndExit() {
        Toast.makeText(MainActivity.this, "Error al obtener datos. Verifique su conexión.", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(this::finish, 2000); // Cierra la app después de 2 segundos
    }
}
