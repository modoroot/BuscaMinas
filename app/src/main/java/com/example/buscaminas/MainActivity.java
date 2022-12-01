package com.example.buscaminas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author amna
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements EventClickCelda {
    RecyclerView recyclerView;
    RedMinasRecyclerAdapter redMinasRecyclerAdapter;
    BuscaMinas buscaMinas;
    TextView check, tiempo;
    CountDownTimer cdt;
    int segundos;
    boolean contadorEmpezado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check = findViewById(R.id.activity_main_check);

        //creación/reseteo del juego clickando el check del layout
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscaMinas = new BuscaMinas(8, 2);
                redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
                contadorEmpezado = false;
                cdt.cancel();
                segundos = 0;
                tiempo.setText(R.string.contador);
            }
        });
        tiempo = findViewById(R.id.activity_main_contador);
        contadorEmpezado = false;
        cdt = new CountDownTimer(10000L, 1000) {
            @Override
            public void onTick(long l) {
                segundos += 1;
                tiempo.setText(String.format("%03d", segundos));
            }

            @Override
            public void onFinish() {
                buscaMinas.sinTiempo();
                Toast.makeText(getApplicationContext(),
                        "Se te acabó el tiempo amego", Toast.LENGTH_SHORT).show();
                buscaMinas.getRedMinas().revelarTodasLasBombas();
                redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
            }
        };

        recyclerView = findViewById(R.id.activity_main_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 8));
        //tamaño tablero y número de bombas en él
        buscaMinas = new BuscaMinas(8, 2);
        redMinasRecyclerAdapter = new RedMinasRecyclerAdapter(buscaMinas.getRedMinas().getCeldas(), this);
        recyclerView.setAdapter(redMinasRecyclerAdapter);
    }

    @Override
    public void clickCelda(Celda celda) {
        buscaMinas.manejadorClickCeldas(celda);
        if(!contadorEmpezado){
            cdt.start();
            contadorEmpezado = true;
        }
        if (buscaMinas.isJuegoTerminado()) {
            Toast.makeText(getApplicationContext(), "Juego terminado amego", Toast.LENGTH_SHORT).show();
            buscaMinas.getRedMinas().revelarTodasLasBombas();
        }
        if (buscaMinas.isPartidaGanada()) {
            Toast.makeText(getApplicationContext(), "Has ganado amego", Toast.LENGTH_SHORT).show();
            buscaMinas.getRedMinas().revelarTodasLasBombas();
        }
        redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
    }


}