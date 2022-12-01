package com.example.buscaminas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements EventClickCelda{
        RecyclerView recyclerView;
        RedMinasRecyclerAdapter redMinasRecyclerAdapter;
        BuscaMinas buscaMinas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.activity_main_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 8));
        buscaMinas = new BuscaMinas(8);
        redMinasRecyclerAdapter = new RedMinasRecyclerAdapter(buscaMinas.getRedMinas().getCeldas(), this);
        recyclerView.setAdapter(redMinasRecyclerAdapter);

    }

    @Override
    public void ClickCelda(Celda celda) {
        Toast.makeText(getApplicationContext(), "Celda clickada", Toast.LENGTH_SHORT).show();
    }


}