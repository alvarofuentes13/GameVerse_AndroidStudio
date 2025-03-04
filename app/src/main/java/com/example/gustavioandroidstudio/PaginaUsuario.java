package com.example.gustavioandroidstudio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gustavioandroidstudio.PopularGamesAdapter;
import com.example.gustavioandroidstudio.api.Game;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import android.view.MenuItem;
import com.google.android.material.navigation.NavigationBarView;

public class PaginaUsuario extends AppCompatActivity {

    private RecyclerView popularGamesRecycler;
    private PopularGamesAdapter popularGamesAdapter;
    private List<Game> popularGames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_usuario);

        /* Inicializa los datos
        popularGames = new ArrayList<>();
        popularGames.add(new Game("GTA V", R.drawable.gta_v));
        popularGames.add(new Game("Cyberpunk 2077", R.drawable.cyberpunk));
        popularGames.add(new Game("Red Dead Redemption 2", R.drawable.red_dead));
        popularGames.add(new Game("The Witcher 3", R.drawable.witcher_3));
*/
        // Configura el RecyclerView
        popularGamesRecycler = findViewById(R.id.recyclerViewGames);
        popularGamesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        popularGamesAdapter = new PopularGamesAdapter(this, popularGames, game -> {
            Toast.makeText(this, "Peor que el overcooked", Toast.LENGTH_SHORT).show();
        });
        popularGamesRecycler.setAdapter(popularGamesAdapter);

// Inicializa el BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    Intent intent = new Intent(PaginaUsuario.this, PaginaPrincipal.class);
                    finish();
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_search) {
                    Intent intent = new Intent(PaginaUsuario.this, BuscarJuegosActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    Intent intent = new Intent(PaginaUsuario.this, PaginaUsuario.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }
}