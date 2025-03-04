package com.example.gustavioandroidstudio.api;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Game implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("summary") // Descripción breve del juego
    private String summary;

    @SerializedName("genres") // IGDB devuelve una lista de IDs de géneros
    private List<Integer> genres;

    @SerializedName("release_dates") // Lista de fechas de lanzamiento
    private List<ReleaseDate> releaseDates;

    @SerializedName("cover") // Objeto anidado para la imagen de portada
    private Cover cover;

    // ✅ Getters con manejo de datos nulos
    public int getId() {
        return id;
    }

    public String getName() {
        return name != null ? name : "Nombre no disponible";
    }

    public String getSummary() {
        return summary != null ? summary : "Sin descripción";
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public String getFormattedGenres() {
        if (genres == null || genres.isEmpty()) return "Sin género disponible";
        return genres.toString(); // Devuelve los IDs como string por ahora
    }

    public List<ReleaseDate> getReleaseDates() {
        return releaseDates;
    }

    public String getFirstReleaseDate() {
        if (releaseDates != null && !releaseDates.isEmpty()) {
            for (ReleaseDate date : releaseDates) {
                if (date.getHumanDate() != null) {
                    return date.getHumanDate();
                }
            }
        }
        return "Fecha no disponible";
    }

    public Cover getCover() {
        return cover;
    }

    public String getCoverUrl() {
        return (cover != null && cover.getUrl() != null)
                ? cover.getUrl().replace("t_thumb", "t_cover_big")
                : null;
    }

    // 📌 MODELO PARA LA PORTADA DEL JUEGO
    public static class Cover implements Serializable {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }
    }

    // 📌 MODELO PARA LAS FECHAS DE LANZAMIENTO
    public static class ReleaseDate implements Serializable {
        @SerializedName("human") // Fecha en formato legible
        private String humanDate;

        public String getHumanDate() {
            return humanDate;
        }
    }
}
