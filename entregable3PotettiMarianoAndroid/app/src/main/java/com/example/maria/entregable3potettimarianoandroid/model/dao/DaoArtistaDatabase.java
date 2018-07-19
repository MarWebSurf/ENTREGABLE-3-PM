package com.example.maria.entregable3potettimarianoandroid.model.dao;

import android.content.Context;
import android.widget.Toast;

import com.example.maria.entregable3potettimarianoandroid.model.POJO.Artista;
import com.example.maria.entregable3potettimarianoandroid.utils.ResultListener;
import com.example.maria.entregable3potettimarianoandroid.view.DetalleActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DaoArtistaDatabase {
    FirebaseDatabase database;
    private static final String ARTISTA = "artists";
    private Respuesta respuesta;


    public DaoArtistaDatabase(){
        database = FirebaseDatabase.getInstance();
    }


    public void obtenerDatosArtista(final String artistIdObraRecibidaPorBundle, final ResultListener<Respuesta> resultlistenerDelControllerArtista) {

        DatabaseReference reference = database.getReference().child(ARTISTA);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //List<Artista> artistaArrayList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Artista artista = snapshot.getValue(Artista.class);
                        if (artista.getArtistId().equals(artistIdObraRecibidaPorBundle)) {
                            //artistaArrayList.add(artista);
                            respuesta = new Respuesta();
                            respuesta.setArtista(artista);
                            resultlistenerDelControllerArtista.finish(respuesta);
                            break;
                        }
                    }
                } else {
                    respuesta.setError("id inexistente");
                    resultlistenerDelControllerArtista.finish(respuesta);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                respuesta.setError("id inexistente");
                resultlistenerDelControllerArtista.finish(respuesta);

            }
        });


    }
}
