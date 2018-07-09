package com.example.maria.entregable3potettimarianoandroid.model.dao;

import com.example.maria.entregable3potettimarianoandroid.model.POJO.ContenedorObras;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceRetrofitObras {

    @GET("x858r")
    Call<ContenedorObras>obtenerObras();
}
