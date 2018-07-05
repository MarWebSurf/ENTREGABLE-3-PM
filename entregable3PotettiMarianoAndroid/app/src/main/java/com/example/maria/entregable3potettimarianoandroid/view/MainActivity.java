package com.example.maria.entregable3potettimarianoandroid.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.maria.entregable3potettimarianoandroid.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ObrasAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        adapter = new ObrasAdapter();
        recyclerView.setAdapter(adapter);




    }
}
