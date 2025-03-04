package com.example.gustavioandroidstudio.api;

import java.util.List;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
            "Accept: application/json"
    })
    @POST("games")
    Call<List<Game>> getVideojuegos(@Body RequestBody query); // ✅ Debe recibir `RequestBody`
}
