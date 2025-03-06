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

        recyclerView = findViewById(R.id.recyclerViewGames); // Verifica que coincida con el XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Recibir la lista de juegos y verificar si está vacía
        List<Game> games = (List<Game>) getIntent().getSerializableExtra("games_list");
        if (games == null || games.isEmpty()) {
            Toast.makeText(this, "No se encontraron juegos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Configurar el adaptador correctamente con el listener para abrir detalles
        gameAdapter = new GameAdapter(this, games, game -> {
            // Abrir GameDetailActivity para mostrar los detalles del juego
            Intent intent = new Intent(GamesActivity.this, GameDetailActivity.class);
            intent.putExtra("game", game);
            startActivity(intent);
        });

        recyclerView.setAdapter(gameAdapter);
    }
}
