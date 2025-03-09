package com.example.gustavioandroidstudio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gustavioandroidstudio.adapters.GameAdapter;
import com.example.gustavioandroidstudio.api.Game;

import java.util.List;

public class GamesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        recyclerView = findViewById(R.id.recyclerViewGames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Game> games = (List<Game>) getIntent().getSerializableExtra("games_list");
        if (games == null || games.isEmpty()) {
            Toast.makeText(this, "No se encontraron juegos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Modificado para abrir ReviewActivity en lugar de GameDetailActivity
        gameAdapter = new GameAdapter(this, games, game -> {
            Intent intent = new Intent(GamesActivity.this, ReviewActivity.class);
            intent.putExtra("GAME_TITLE", game.getName());
            intent.putExtra("GAME_YEAR", game.getFirstReleaseDate());
            intent.putExtra("GAME_IMAGE", R.drawable.game_placeholder); // Sustituye con la imagen real si la tienes
            startActivity(intent);
        });

        recyclerView.setAdapter(gameAdapter);
    }
}
