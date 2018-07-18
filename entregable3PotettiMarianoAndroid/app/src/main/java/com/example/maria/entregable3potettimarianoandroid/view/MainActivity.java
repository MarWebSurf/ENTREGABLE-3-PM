package com.example.maria.entregable3potettimarianoandroid.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maria.entregable3potettimarianoandroid.R;
import com.example.maria.entregable3potettimarianoandroid.controller.ControllerObras;
import com.example.maria.entregable3potettimarianoandroid.model.POJO.Obra;
import com.example.maria.entregable3potettimarianoandroid.utils.ResultListener;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ObrasAdapter.NotificadorCeldaClickeada, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private ObrasAdapter adapter;
    private ControllerObras controllerObras;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


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
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);


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
        Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetalleActivity.CLAVE_OBRA, obraClickeada);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.cerrar_sesion_NavigationView):

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        //si es que esta logueado con facebook, tengo que desloguearlo
                        LoginManager.getInstance().logOut();
                    }
                    //esto es para desloguearlo de firebase, ya se  que entro con facebok o nativo
                    FirebaseAuth.getInstance().signOut();
                    onBackPressed();
                    break;
                } else{
                    Toast.makeText(this, "no hay una sesion iniciada", Toast.LENGTH_SHORT).show();
                    break;

                }

            case (R.id.login_NavigationView):

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Toast.makeText(this, " Ya estas logueado como "  + user.getEmail()+
                            ", para iniciar sesion con otro usuario cerra sesion", Toast.LENGTH_LONG).show();
                    break;

                }else  {
                    Intent intent = new Intent(MainActivity.this, LoginFaceBookActivity.class);
                    startActivity(intent);
                    break;
                }
        }
        drawerLayout.closeDrawers();
        return true;
    }
}

