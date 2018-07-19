package com.example.maria.entregable3potettimarianoandroid.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.maria.entregable3potettimarianoandroid.model.POJO.Artista;
import com.example.maria.entregable3potettimarianoandroid.model.dao.DaoArtistaDatabase;
import com.example.maria.entregable3potettimarianoandroid.model.dao.Respuesta;
import com.example.maria.entregable3potettimarianoandroid.utils.ResultListener;

public class ControllerArtistaDatabase {
    private Context context;

    public ControllerArtistaDatabase(Context context) {
        this.context = context;

    }

    public void obtenerDatosArtistaDatabase( String id, final ResultListener<Respuesta> resultListenerDeLaVista) {

        if (hayInternet()) {

            DaoArtistaDatabase daoArtistaDatabase = new DaoArtistaDatabase();
            daoArtistaDatabase.obtenerDatosArtista(id, new ResultListener<Respuesta>() {
                @Override
                public void finish(Respuesta resultado) {
                    resultListenerDeLaVista.finish(resultado);
                }
            });
        } else {
            //aca iria room pero aprovecho el listener para mostrar que no hay internet por pantalla en Detalleactivity si se corta internet cuando estamos
            //en el MainActivity( del Recycler) cuando selleccionamos una obra de la lista.
            Respuesta respuestaNoHayInternet = new Respuesta();
            respuestaNoHayInternet.setError("No hay internet");
            resultListenerDeLaVista.finish(respuestaNoHayInternet);
       }
    }

    private boolean hayInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
