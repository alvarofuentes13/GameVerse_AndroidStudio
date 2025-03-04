package com.example.gustavioandroidstudio;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.gustavioandroidstudio.api.Game;

public class GameDetailActivity extends AppCompatActivity {
    private ImageView gameImage;
    private TextView gameTitle, gameSummary, gameGenres, gameReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        // Referencias a los elementos de la UI
        gameImage = findViewById(R.id.gameImage);
        gameTitle = findViewById(R.id.gameTitle);
        gameSummary = findViewById(R.id.gameSummary);
        gameGenres = findViewById(R.id.gameGenres);
        gameReleaseDate = findViewById(R.id.gameReleaseDate);

        // Obtener el objeto Game desde el Intent
        Game game = (Game) getIntent().getSerializableExtra("game");

        if (game != null) {
            gameTitle.setText(game.getName());
            gameSummary.setText(game.getSummary());
            gameGenres.setText("Género: " + game.getFormattedGenres());
            gameReleaseDate.setText("Fecha de lanzamiento: " + game.getFirstReleaseDate());

            // Cargar imagen con Glide
            Glide.with(this)
                    .load(game.getCoverUrl())
                    .placeholder(R.drawable.logo) // Imagen de carga
                    .error(R.drawable.logo) // Imagen si falla la carga
                    .into(gameImage);
        }
    }
}
