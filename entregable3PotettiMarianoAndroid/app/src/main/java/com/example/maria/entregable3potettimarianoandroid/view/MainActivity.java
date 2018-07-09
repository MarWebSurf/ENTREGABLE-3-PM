package com.example.maria.entregable3potettimarianoandroid.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.maria.entregable3potettimarianoandroid.R;
import com.example.maria.entregable3potettimarianoandroid.controller.ControllerObras;
import com.example.maria.entregable3potettimarianoandroid.model.POJO.Obra;
import com.example.maria.entregable3potettimarianoandroid.utils.ResultListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ObrasAdapter.NotificadorCeldaClickeada{

    private RecyclerView recyclerView;
    private ObrasAdapter adapter;
    private ControllerObras controllerObras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        adapter = new ObrasAdapter(this);
        recyclerView.setAdapter(adapter);
        controllerObras = new ControllerObras();
        ObtenerObras();


    }

    private void ObtenerObras() {
        controllerObras.ObtenerObrasJsonController(new ResultListener<List<Obra>>() {
            @Override
            public void finish(List<Obra> listaDeObrasObtenidaDelController) {
                adapter.agregarObras(listaDeObrasObtenidaDelController);
            }

        });
    }

    @Override
    public void notificarCeldaClickeada(Obra obraClickeada) {
        Intent intent = new Intent(MainActivity.this,DetalleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetalleActivity.CLAVE_OBRA,obraClickeada);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
