package com.example.gustavioandroidstudio;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gustavioandroidstudio.api.ApiClient;
import com.example.gustavioandroidstudio.api.ApiService;
import com.example.gustavioandroidstudio.api.Game;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {

    private ImageView gameImage;
    private TextView gameTitle, gameYear, gameDescription;
    private RatingBar ratingBar;
    private EditText reviewText;
    private Button submitButton;
    private ApiService apiService;
    private int gameId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        gameImage = findViewById(R.id.gameImage);
        gameTitle = findViewById(R.id.gameTitle);
        gameYear = findViewById(R.id.gameYear);
        gameDescription = findViewById(R.id.gameDescription);
        ratingBar = findViewById(R.id.ratingBar);
        reviewText = findViewById(R.id.reviewText);
        submitButton = findViewById(R.id.submitButton);

        // Obtener el ID del juego desde el Intent
        gameId = getIntent().getIntExtra("GAME_ID", -1);

        if (gameId == -1) {
            Toast.makeText(this, "Error: No se pudo obtener la información del juego", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d("DEBUG", "ID del juego recibido: " + gameId);

        // Inicializar servicio de API
        apiService = ApiClient.getClient().create(ApiService.class);

        // Llamar a la API para obtener los detalles del juego
        obtenerDetallesJuego(gameId);

        submitButton.setOnClickListener(view -> {
            String review = reviewText.getText().toString();
            float rating = ratingBar.getRating();
            Toast.makeText(this, "Reseña enviada: " + review + " con " + rating + " estrellas", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void obtenerDetallesJuego(int id) {
        String query = "fields name,summary,cover.url,release_dates.human; where id = " + id + ";";
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), query);

        apiService.getVideojuegos(requestBody).enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Game game = response.body().get(0);

                    // Mostrar la información en la UI
                    gameTitle.setText(game.getName() != null ? game.getName() : "Título desconocido");
                    gameYear.setText(game.getFirstReleaseDate() != null ? game.getFirstReleaseDate() : "Fecha no disponible");
                    gameDescription.setText(game.getSummary() != null ? game.getSummary() : "Sin descripción disponible");

                    if (game.getCoverUrl() != null && !game.getCoverUrl().isEmpty()) {
                        Glide.with(ReviewActivity.this)
                                .load(game.getCoverUrl())
                                .placeholder(R.drawable.game_placeholder)
                                .error(R.drawable.logo)
                                .into(gameImage);
                    } else {
                        gameImage.setImageResource(R.drawable.game_placeholder);
                    }
                } else {
                    Log.e("API_ERROR", "No se pudo obtener la información del juego");
                    Toast.makeText(ReviewActivity.this, "No se pudo obtener la información del juego", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e("API_ERROR", "Error al obtener datos del juego", t);
                Toast.makeText(ReviewActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
