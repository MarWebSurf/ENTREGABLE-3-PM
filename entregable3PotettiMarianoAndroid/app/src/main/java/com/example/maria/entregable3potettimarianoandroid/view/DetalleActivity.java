package com.example.maria.entregable3potettimarianoandroid.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maria.entregable3potettimarianoandroid.R;
import com.example.maria.entregable3potettimarianoandroid.model.POJO.Artista;
import com.example.maria.entregable3potettimarianoandroid.model.POJO.Obra;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DetalleActivity extends AppCompatActivity {

    public static final String CLAVE_OBRA = "clave obra";
    private static final String ARTISTA = "artists";
    private TextView textViewDetalleNombreObra;
    private ImageView imageViewDetalle;
    private TextView textViewDetalleNombreArtista;
    private TextView textViewDetalleNacionalidad;
    private TextView textViewDetalleInfluenced_By;


    FirebaseDatabase database;

    Obra obraRecibidaPorBundleDesdeRecyclerMainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        database = FirebaseDatabase.getInstance();

        textViewDetalleNombreObra = findViewById(R.id.textView_detalle_nombre_obra);
        imageViewDetalle = findViewById(R.id.imageView_Detalle);
        textViewDetalleNombreArtista = findViewById(R.id.textView_detalle_nombreArtista);
        textViewDetalleNacionalidad = findViewById(R.id.textView_detalle_Nacionalidad);
        textViewDetalleInfluenced_By = findViewById(R.id.textView_detalle_Influenced_By);

        Intent intentRecibido = getIntent();
        Bundle bundleRecibido = intentRecibido.getExtras();
        obraRecibidaPorBundleDesdeRecyclerMainActivity = (Obra) bundleRecibido.getSerializable(CLAVE_OBRA);
        textViewDetalleNombreObra.setText(obraRecibidaPorBundleDesdeRecyclerMainActivity.getName());

        traerArtistaPorId(obraRecibidaPorBundleDesdeRecyclerMainActivity.getArtistId());
        traerImagenCuadroElegido(obraRecibidaPorBundleDesdeRecyclerMainActivity.getRutaImagen());


    }

    private void traerImagenCuadroElegido(String rutaImagen) {
        if(TextUtils.isEmpty(rutaImagen)){
            return;
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference();
        reference = reference.child(rutaImagen);
        //opcion 1

        try {
            final File archivo = File.createTempFile("fotoandroid", "jpg");
            final StorageReference finalReference = reference;
            reference.getFile(archivo).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Glide.with(DetalleActivity.this)
                            .using(new FirebaseImageLoader())
                            .load(finalReference)
                            .placeholder(R.drawable.placeholder16_9)
                            .error(R.drawable.error)
                            .into(imageViewDetalle);



                   // Picasso.get().load(archivo.getAbsoluteFile()).into(imagenContacto);
                }
            });
        } catch (Exception e) {

        }

    }


    public void traerArtistaPorId(final String artistIdObraRecibidaPorBundle) {
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
                            textViewDetalleNombreArtista.setText(artista.getName());
                            textViewDetalleNacionalidad.setText(artista.getNationality());
                            textViewDetalleInfluenced_By.setText(artista.getInfluenced_by());
                            break;
                        }
                    }


                } else {
                    Toast.makeText(DetalleActivity.this, "id inexistente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetalleActivity.this, "error, intente nuevamente", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
