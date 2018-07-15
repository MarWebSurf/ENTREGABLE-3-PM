package com.example.maria.entregable3potettimarianoandroid.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.maria.entregable3potettimarianoandroid.R;
import com.example.maria.entregable3potettimarianoandroid.model.POJO.Obra;

public class DetalleActivity extends AppCompatActivity {

    public static final String CLAVE_OBRA = "clave obra" ;
    private TextView textViewDetalleObra;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        textViewDetalleObra = findViewById(R.id.textView_detalle_obra);
        Intent intentRecibido = getIntent();
        Bundle bundleRecibido = intentRecibido.getExtras();
        Obra obraRecibida = (Obra) bundleRecibido.getSerializable(CLAVE_OBRA);
        textViewDetalleObra.setText(obraRecibida.getName());


    }

    public void traerArtistaPorId(String id){

    }
}
