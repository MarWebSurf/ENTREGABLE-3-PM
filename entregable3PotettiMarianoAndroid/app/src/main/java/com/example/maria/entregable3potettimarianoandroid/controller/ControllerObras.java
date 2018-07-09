package com.example.maria.entregable3potettimarianoandroid.controller;

import com.example.maria.entregable3potettimarianoandroid.model.POJO.Obra;
import com.example.maria.entregable3potettimarianoandroid.model.dao.DaoObras;
import com.example.maria.entregable3potettimarianoandroid.utils.ResultListener;

import java.util.List;

public class ControllerObras {
    public void ObtenerObrasJsonController(final ResultListener resultListenerDeLaVista) {

        DaoObras daoObras = new DaoObras();
        daoObras.obtenerObrasJsonDao(new ResultListener<List<Obra>>() {
            @Override
            public void finish(List<Obra> listaDeObrasObtenidaDelDao) {
                resultListenerDeLaVista.finish(listaDeObrasObtenidaDelDao);

            }
        });
    }
}
