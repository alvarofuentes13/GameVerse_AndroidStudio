package com.example.gustavioandroidstudio.api;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;

public class ApiClient {
    private static final String BASE_URL = "https://api.igdb.com/v4/";
    private static final String CLIENT_ID = "19mj5nfs06lbn6pmkeasqq0ugdjel6"; // ✅ Cambia esto por el correcto
    private static final String AUTH_TOKEN = "5z7z8z1ca0ruzkciwqroyoqbrzv88m"; // ✅ Usa un token actualizado dinámicamente

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .addHeader("Client-ID", CLIENT_ID)
                                    .addHeader("Authorization", "Bearer " + AUTH_TOKEN)
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
