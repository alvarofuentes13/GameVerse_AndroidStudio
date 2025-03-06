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

import java.util.ArrayList;
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

        // Inicializa la lista (vacía por ahora, o puedes llenarla con datos)
        popularGames = new ArrayList<>();  // Asegúrate de que esta lista no sea null

        // Configura el RecyclerView
        popularGamesRecycler = findViewById(R.id.recyclerViewGames);
        popularGamesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Inicializa el adaptador con la lista no nula
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
                }
                return false;
            }
        });
    }
}
