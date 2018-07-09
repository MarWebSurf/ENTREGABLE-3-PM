package com.example.maria.entregable3potettimarianoandroid.model.dao;

import com.example.maria.entregable3potettimarianoandroid.model.POJO.ContenedorObras;
import com.example.maria.entregable3potettimarianoandroid.model.POJO.Obra;
import com.example.maria.entregable3potettimarianoandroid.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaoObras {
    private static final String BASE_URL = "https://api.myjson.com/bins/";
    private Retrofit retrofit;
    private ServiceRetrofitObras serviceRetrofitObras;

    public DaoObras(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        serviceRetrofitObras = retrofit.create(ServiceRetrofitObras.class);
    }


    public void obtenerObrasJsonDao(final ResultListener<List<Obra>> resultListenerDelController){
        serviceRetrofitObras.obtenerObras().enqueue(new Callback<ContenedorObras>() {
            @Override
            public void onResponse(Call<ContenedorObras> call, Response<ContenedorObras> response) {
                ContenedorObras contenedorObrasObtenido = response.body();
                if(contenedorObrasObtenido != null && contenedorObrasObtenido.getPaints() != null){
                    List<Obra> listaDeObrasObtenida = contenedorObrasObtenido.getPaints();
                    resultListenerDelController.finish(listaDeObrasObtenida);
                }else{
                    resultListenerDelController.finish(new ArrayList<Obra>());
                }
            }

            @Override
            public void onFailure(Call<ContenedorObras> call, Throwable t) {
                resultListenerDelController.finish(new ArrayList<Obra>());

            }
        });
    }

}
