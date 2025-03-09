package com.example.gustavioandroidstudio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gustavioandroidstudio.api.ApiClient;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        EditText etUsuario = findViewById(R.id.etUsuarioRegistro);
        EditText etEmail = findViewById(R.id.etEmailRegistro);
        EditText etContrasena = findViewById(R.id.etContrasenaRegistro);
        Button btnRegistrarse = findViewById(R.id.btnRegistrarse);
        TextView tvIniciarSesion = findViewById(R.id.tvIniciarSesion);



        btnRegistrarse.setOnClickListener(view -> {

            String jsonUsuario = "{"
                    + "\"email\":\"camilo@gmail.com\","
                    + "\"name\":\"Álvaro\","
                    + "\"password\":\"1234\","
                    + "\"fechaRegistro\":\"2025-03-06T12:17:22\","
                    + "\"avatar\":null,"
                    + "\"biografia\":\"Me gustan los videojuegos\""
                    + "}";

            ApiClient.registrarUsuario(jsonUsuario);
            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();
        });

        tvIniciarSesion.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
