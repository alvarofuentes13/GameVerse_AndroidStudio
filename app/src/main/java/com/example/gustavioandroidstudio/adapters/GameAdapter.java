package com.example.gustavioandroidstudio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gustavioandroidstudio.R;
import com.example.gustavioandroidstudio.api.Game;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private final Context context;
    private final List<Game> videojuegos;
    private final OnItemClickListener onItemClickListener;

    // ✅ Se recibe el Contexto y el OnItemClickListener en el constructor
    public GameAdapter(Context context, List<Game> videojuegos, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.videojuegos = videojuegos;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_game_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game videojuego = videojuegos.get(position);
        holder.textViewNombre.setText(videojuego.getName());

        // ✅ Se maneja la imagen correctamente para evitar fallos
        String imageUrl = videojuego.getCover() != null ?
                videojuego.getCover().getUrl().replace("t_thumb", "t_cover_big") :
                null;

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.logo) // Imagen de carga
                .error(R.drawable.logo) // Imagen en caso de error
                .into(holder.imageView);

        // ✅ Se verifica que OnItemClickListener no sea null antes de ejecutarlo
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(videojuego);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videojuegos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.gameTitle);
            imageView = itemView.findViewById(R.id.gameImage);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Game videojuego);
    }
}
