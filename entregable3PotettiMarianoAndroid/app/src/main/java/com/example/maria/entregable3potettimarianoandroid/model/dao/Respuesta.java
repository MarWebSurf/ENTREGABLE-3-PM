package com.example.maria.entregable3potettimarianoandroid.model.dao;

import com.example.maria.entregable3potettimarianoandroid.model.POJO.Artista;

public class Respuesta {
    private Artista artista;
    private String error;

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
