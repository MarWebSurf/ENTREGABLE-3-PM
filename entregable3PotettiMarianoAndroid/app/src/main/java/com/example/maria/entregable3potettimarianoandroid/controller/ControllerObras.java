package com.example.maria.entregable3potettimarianoandroid.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.maria.entregable3potettimarianoandroid.model.POJO.Obra;
import com.example.maria.entregable3potettimarianoandroid.model.dao.DaoObras;
import com.example.maria.entregable3potettimarianoandroid.utils.ResultListener;

import java.util.List;

public class ControllerObras {
    private Context context;

    public ControllerObras(Context context) {
        this.context = context;
    }

    public void ObtenerObrasJsonController(final ResultListener resultListenerDeLaVista) {
        if (hayInternet()) {
            DaoObras daoObras = new DaoObras();
            daoObras.obtenerObrasJsonDao(new ResultListener<List<Obra>>() {
                @Override
                public void finish(List<Obra> listaDeObrasObtenidaDelDao) {
                    resultListenerDeLaVista.finish(listaDeObrasObtenidaDelDao);

                }
            });
        } else {
            Log.i("naranja,", ", no hay internet, iria room");//iria room , puse ese log que probe y muestra esos strings en la consola de debug

        }
    }


    private boolean hayInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
