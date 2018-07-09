package com.example.maria.entregable3potettimarianoandroid.model.POJO;

import java.io.Serializable;

public class Obra implements Serializable {

    private String image;
    private String name;
    private String artistId;

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getArtistId() {
        return artistId;
    }


    public void setImage(String image) {
        this.image = image;
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
