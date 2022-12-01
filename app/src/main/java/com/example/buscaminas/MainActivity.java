package com.example.buscaminas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 *
 * @author amna
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements EventClickCelda{
        RecyclerView recyclerView;
        RedMinasRecyclerAdapter redMinasRecyclerAdapter;
        BuscaMinas buscaMinas;
        TextView check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check = findViewById(R.id.activity_main_check);

        //creación/reseteo del juego clickando el check del layout
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscaMinas = new BuscaMinas(8, 10);
                redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
            }
        });

        recyclerView = findViewById(R.id.activity_main_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 8));
        //tamaño tablero y número de bombas en él
        buscaMinas = new BuscaMinas(8, 10);
        redMinasRecyclerAdapter = new RedMinasRecyclerAdapter(buscaMinas.getRedMinas().getCeldas(), this);
        recyclerView.setAdapter(redMinasRecyclerAdapter);

    }

    @Override
    public void clickCelda(Celda celda) {
        buscaMinas.manejadorClickCeldas(celda);
        redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
    }


}