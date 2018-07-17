package com.example.maria.entregable3potettimarianoandroid.model.POJO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Obra implements Serializable {

    @SerializedName("image")
    private String rutaImagen;
    private String name;
    private String artistId;

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getName() {
        return name;
    }

    public String getArtistId() {
        return artistId;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    @Override
    public String toString() {
        return "Titulo: " + name;
    }



    //ver si esta bien el equals despues...
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Obra)) {
            return false;
        }
        Obra obraAComparar = (Obra) obj;
        return (obraAComparar.getArtistId().equals(this.artistId) &&
                obraAComparar.getName().equals(this.name));
    }
}
