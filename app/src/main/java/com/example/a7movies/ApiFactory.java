package com.example.a7movies;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/";
    private static ApiService apiService;   // переменная для доступа к apiService. Одна на все приложение (синглтон)


    public static ApiService getApiService() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl( BASE_URL )
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                                        .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
