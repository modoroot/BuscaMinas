package com.example.buscaminas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Clase lanzadora de la aplicación. Lanza todos los métodos creados
 * en las demás clases, lógica, y control de errores
 * @author amna
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements EventClick {
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
        bandera.setOnClickListener(v -> buscaMinas.activarDesactivarModoBandera());

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
        //contador de tiempo para el buscaminas. 300 segundos de tiempo
        cdt = new CountDownTimer(300000L, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            /*
             *Cada tick (en este caso cada segundo) suma 1 al contador hasta llegar
             * a 300 segundos
             */
            public void onTick(long l) {
                segundos++;
                tiempo.setText(String.format("%03d", segundos));
            }

            /*
             *Una vez llega al tiempo determinado, nos lo indica la aplicación por pantalla
             * y revela todas las bombas del tablero
             */
            @Override
            public void onFinish() {
                //variable isTiempoAcabo a true
                buscaMinas.sinTiempo();
                //muestra por pantalla
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

    /**
     * Método que maneja diferentes posibles eventos en las celdas una vez
     * se clicka en una
     * @param celda celda tablero
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void clickCelda(Celda celda) {
        //instaciamos el métood
        buscaMinas.manejadorClickCeldas(celda);
        //contador de banderas según la cantidad de bombas en el tablero
        contadorBandera.setText(String.format("%03d", buscaMinas.getNumeroBombas() - buscaMinas.getNumBanderas()));
        //si no ha empezado el contador una vez se clickea una celda, lo empieza. Es decir,
        //la primera celda empezará siempre el contador
        if (!contadorEmpezado) {
            cdt.start();
            contadorEmpezado = true;
        }
        //Si el usuario ha pulsado en una bomba, el juego termina y no deja clickar en más celdas.
        //Revela todas las bombas
        if (buscaMinas.isJuegoTerminado()) {
            //muestra por pantalla
            Toast.makeText(getApplicationContext(), "Perdiste amego", Toast.LENGTH_SHORT).show();
            buscaMinas.getRedMinas().revelarTodasLasBombas();
        }
        //si el usuario ha destapado todas las casillas sin pulsar en ninguna bomba
        if (buscaMinas.isPartidaGanada()) {
            Toast.makeText(getApplicationContext(), "Has ganado amego", Toast.LENGTH_SHORT).show();
            buscaMinas.getRedMinas().revelarTodasLasBombas();
        }
        redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint({"NonConstantResourceId", "DefaultLocale"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.modoPrincipiante:
                recyclerView.removeAllViews();
                recyclerView.setLayoutManager(new GridLayoutManager(this, 8));
                buscaMinas = new BuscaMinas(8, 2);

                redMinasRecyclerAdapter = new RedMinasRecyclerAdapter(buscaMinas.getRedMinas().getCeldas(),
                        this);
                recyclerView.setAdapter(redMinasRecyclerAdapter);
                contadorBandera.setText(String.format("%03d",
                        buscaMinas.getNumeroBombas() - buscaMinas.getNumBanderas()));

                redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
                contadorEmpezado = false;
                //resetea el contador a 0
                cdt.cancel();
                segundos = 0;
                tiempo.setText(R.string.contador);
                contadorBandera.setText(String.format("%03d",
                        buscaMinas.getNumeroBombas() - buscaMinas.getNumBanderas()));
                Toast.makeText(getApplicationContext(),
                        "Modo de juego cambiado: Principiante", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.modoAmateur:
                recyclerView.removeAllViews();
                recyclerView.setLayoutManager(new GridLayoutManager(this, 12));
                buscaMinas = new BuscaMinas(12, 2);

                redMinasRecyclerAdapter = new RedMinasRecyclerAdapter(buscaMinas.getRedMinas().getCeldas(), this);
                recyclerView.setAdapter(redMinasRecyclerAdapter);
                contadorBandera.setText(String.format("%03d", buscaMinas.getNumeroBombas() - buscaMinas.getNumBanderas()));

                redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
                contadorEmpezado = false;
                //resetea el contador a 0
                cdt.cancel();
                segundos = 0;
                tiempo.setText(R.string.contador);
                contadorBandera.setText(String.format("%03d",
                        buscaMinas.getNumeroBombas() - buscaMinas.getNumBanderas()));
                Toast.makeText(getApplicationContext(),
                        "Modo de juego cambiado: Amateur", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.modoAvanzado:
                recyclerView.removeAllViews();
                recyclerView.setLayoutManager(new GridLayoutManager(this, 16));
                buscaMinas = new BuscaMinas(16, 2);

                redMinasRecyclerAdapter = new RedMinasRecyclerAdapter(buscaMinas.getRedMinas().getCeldas(), this);
                recyclerView.setAdapter(redMinasRecyclerAdapter);
                contadorBandera.setText(String.format("%03d", buscaMinas.getNumeroBombas() - buscaMinas.getNumBanderas()));

                redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
                contadorEmpezado = false;
                //resetea el contador a 0
                cdt.cancel();
                segundos = 0;
                tiempo.setText(R.string.contador);
                contadorBandera.setText(String.format("%03d",
                        buscaMinas.getNumeroBombas() - buscaMinas.getNumBanderas()));
                Toast.makeText(getApplicationContext(),
                        "Modo de juego cambiado: Amateur", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}