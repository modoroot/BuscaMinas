package com.example.buscaminas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    TextView check, tiempo, bandera, contadorBandera;
    CountDownTimer cdt;
    int segundos;
    boolean contadorEmpezado;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check = findViewById(R.id.activity_main_check);

        bandera = findViewById(R.id.activity_main_bandera);
        contadorBandera = findViewById(R.id.activity_main_flagsleft);
        bandera.setOnClickListener(v -> {
            buscaMinas.activarDesactivarModoBandera();
        });

        //creación/reseteo del juego clickando el check del layout
        check.setOnClickListener(view -> {
            buscaMinas = new BuscaMinas(8, 2);
            redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
            contadorEmpezado = false;
            //resetea el contador a 0
            cdt.cancel();
            segundos = 0;
            tiempo.setText(R.string.contador);
            contadorBandera.setText(String.format("%03d",
                    buscaMinas.getNumeroBombas() - buscaMinas.getNumBanderas()));
        });
        tiempo = findViewById(R.id.activity_main_contador);
        contadorEmpezado = false;
        cdt = new CountDownTimer(10000L, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long l) {
                segundos++;
                tiempo.setText(String.format("%03d", segundos));
            }

            @Override
            public void onFinish() {
                buscaMinas.sinTiempo();
                Toast.makeText(getApplicationContext(),
                        "Se te acabó el tiempo amego", Toast.LENGTH_SHORT).show();
                //revela bombas
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
        contadorBandera.setText(String.format("%03d", buscaMinas.getNumeroBombas() - buscaMinas.getNumBanderas()));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void clickCelda(Celda celda) {
        buscaMinas.manejadorClickCeldas(celda);
        contadorBandera.setText(String.format("%03d", buscaMinas.getNumeroBombas() - buscaMinas.getNumBanderas()));
        //si no ha empezado el contador una vez se clickea una celda, lo empieza. Es decir,
        //la primera celda empezará siempre el contador
        if (!contadorEmpezado) {
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