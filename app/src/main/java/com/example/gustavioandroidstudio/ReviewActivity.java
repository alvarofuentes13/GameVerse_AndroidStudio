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

public class ReviewActivity extends AppCompatActivity {

    private ImageView gameImage;
    private TextView gameTitle, gameYear;
    private RatingBar ratingBar;
    private EditText reviewText;
    private Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        gameImage = findViewById(R.id.gameImage);
        gameTitle = findViewById(R.id.gameTitle);
        gameYear = findViewById(R.id.gameYear);
        ratingBar = findViewById(R.id.ratingBar);
        reviewText = findViewById(R.id.reviewText);
        submitButton = findViewById(R.id.submitButton);

        // Obtener datos del Intent
        String title = getIntent().getStringExtra("GAME_TITLE");
        String year = getIntent().getStringExtra("GAME_YEAR");
        String imageUrl = getIntent().getStringExtra("GAME_IMAGE_URL");

        // Log para verificar que los datos llegan correctamente
        Log.d("DEBUG", "Recibido en ReviewActivity - Título: " + title);
        Log.d("DEBUG", "Recibido en ReviewActivity - Año: " + year);
        Log.d("DEBUG", "Recibido en ReviewActivity - Imagen: " + imageUrl);

        // Asegurar que los datos no sean nulos
        gameTitle.setText(title != null ? title : "Título desconocido");
        gameYear.setText(year != null ? year : "Fecha no disponible");

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.game_placeholder)
                    .error(R.drawable.logo)
                    .into(gameImage);
        } else {
            gameImage.setImageResource(R.drawable.game_placeholder);
        }

        submitButton.setOnClickListener(view -> {
            String review = reviewText.getText().toString();
            float rating = ratingBar.getRating();
            Toast.makeText(this, "Reseña enviada: " + review + " con " + rating + " estrellas", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
