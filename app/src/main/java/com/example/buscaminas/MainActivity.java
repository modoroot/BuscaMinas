package com.example.buscaminas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
 *
 * @author amna
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements EventClick {
    RecyclerView recyclerView;
    RedMinasRecyclerAdapter redMinasRecyclerAdapter;
    BuscaMinas buscaMinas;
    TextView check, tiempo, bandera;
    CountDownTimer cdt;
    AlertDialog.Builder builder;
    int segundos;
    boolean contadorEmpezado;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //asignación de id's a variables
        check = findViewById(R.id.activity_main_check);
        bandera = findViewById(R.id.activity_main_bandera);
        builder = new AlertDialog.Builder(this);
        //activar-desactivar modo bandera
        bandera.setOnClickListener(v -> buscaMinas.activarDesactivarModoBandera());

        //creación/reseteo del juego clickando el check del layout
        check.setOnClickListener(view -> {
            buscaMinas = new BuscaMinas(8, 10);
            redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
            contadorEmpezado = false;
            //resetea el contador a 0
            cdt.cancel();
            segundos = 0;
            tiempo.setText(R.string.contador);
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
        buscaMinas = new BuscaMinas(8, 10);
        redMinasRecyclerAdapter = new RedMinasRecyclerAdapter(buscaMinas.getRedMinas().getCeldas(),
                this);
        recyclerView.setAdapter(redMinasRecyclerAdapter);

    }

    /**
     * Método que maneja diferentes posibles eventos en las celdas una vez
     * se clicka en una de ellas
     *
     * @param celda celda tablero
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void clickCelda(Celda celda) {
        //instaciamos el método
        buscaMinas.manejadorClickCeldas(celda);
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
            cdt.cancel();
        }
        //si el usuario ha destapado todas las casillas sin pulsar en ninguna bomba
        if (buscaMinas.isPartidaGanada()) {
            Toast.makeText(getApplicationContext(), "Has ganado amego", Toast.LENGTH_SHORT).show();
            buscaMinas.getRedMinas().revelarTodasLasBombas();
            cdt.cancel();
        }
        redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
    }

    /**
     * Infla el menú de menu.xml para que se muestre en activity_main.xml, es decir,
     * el layout principal de la aplicación.
     * @param menu menú
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Menú que maneja los diferentes ítems. Contiene las 3 dificultades y las instrucciones
     * @param item subítem
     * @return subítem seleccionado
     */
    @SuppressLint({"NonConstantResourceId", "DefaultLocale"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.instrucciones:
                //ajustes alertdialog
                builder.setTitle(R.string.instrucciones);
                builder.setMessage(R.string.instrucciones_texto);
                builder.setPositiveButton("Aceptar",null);
                AlertDialog dialog = builder.create();
                //lo muestra
                dialog.show();
                return true;

            case R.id.modoPrincipiante:
                //resetea el tablero cuando se pulsa el check con lo siguiente
                check.setOnClickListener(view -> {
                    buscaMinas = new BuscaMinas(8, 10);
                    redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
                    contadorEmpezado = false;
                    //resetea el contador a 0
                    cdt.cancel();
                    segundos = 0;
                    //muestreo del contador
                    tiempo.setText(R.string.contador);
                });
                //borra las vistas
                recyclerView.removeAllViews();
                recyclerView = findViewById(R.id.activity_main_grid);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 8));
                buscaMinas = new BuscaMinas(8, 10);
                //asigna las minas y celdas al tablero
                redMinasRecyclerAdapter = new RedMinasRecyclerAdapter(buscaMinas.getRedMinas().getCeldas(),
                        this);
                recyclerView.setAdapter(redMinasRecyclerAdapter);

                redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
                contadorEmpezado = false;
                //resetea el contador a 0
                cdt.cancel();
                segundos = 0;
                tiempo.setText(R.string.contador);
                Toast.makeText(getApplicationContext(),
                        "Modo de juego cambiado: Principiante", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.modoAmateur:
                //creación/reseteo del juego clickando el check del layout
                check.setOnClickListener(view -> {
                    buscaMinas = new BuscaMinas(12, 30);
                    redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
                    contadorEmpezado = false;
                    //resetea el contador a 0
                    cdt.cancel();
                    segundos = 0;
                    tiempo.setText(R.string.contador);
                });
                recyclerView.removeAllViews();
                recyclerView = findViewById(R.id.activity_main_grid);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 12));
                buscaMinas = new BuscaMinas(12, 30);

                redMinasRecyclerAdapter = new RedMinasRecyclerAdapter(buscaMinas.getRedMinas().getCeldas(),
                        this);
                recyclerView.setAdapter(redMinasRecyclerAdapter);

                redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
                contadorEmpezado = false;
                //resetea el contador a 0
                cdt.cancel();
                segundos = 0;
                tiempo.setText(R.string.contador);
                Toast.makeText(getApplicationContext(),
                        "Modo de juego cambiado: Amateur", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.modoAvanzado:
                check.setOnClickListener(view -> {
                    buscaMinas = new BuscaMinas(16, 60);
                    redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
                    contadorEmpezado = false;
                    //resetea el contador a 0
                    cdt.cancel();
                    segundos = 0;
                    tiempo.setText(R.string.contador);
                });
                recyclerView.removeAllViews();
                recyclerView = findViewById(R.id.activity_main_grid);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 16));
                buscaMinas = new BuscaMinas(16, 60);

                redMinasRecyclerAdapter = new RedMinasRecyclerAdapter(buscaMinas.getRedMinas().getCeldas(), this);
                recyclerView.setAdapter(redMinasRecyclerAdapter);

                redMinasRecyclerAdapter.setCeldas(buscaMinas.getRedMinas().getCeldas());
                contadorEmpezado = false;
                //resetea el contador a 0
                cdt.cancel();
                segundos = 0;
                tiempo.setText(R.string.contador);
                Toast.makeText(getApplicationContext(),
                        "Modo de juego cambiado: Avanzado", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}