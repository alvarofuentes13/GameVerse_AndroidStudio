package com.example.gustavioandroidstudio.api;

import java.util.List;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @Headers({
            "Accept: application/json"
    })
    @GET("games/{id}")
    Call<Game> getJuegoPorId(@Path("id") int id);

    @Headers({
            "Accept: application/json"
    })
    @POST("games")
    Call<List<Game>> getVideojuegos(@Body RequestBody query);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("http://192.168.1.43:8080/api/reviews")
    Call<Review> createReview(@Body RequestBody requestBody);
}