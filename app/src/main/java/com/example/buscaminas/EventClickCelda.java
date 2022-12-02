package com.example.buscaminas;

import android.view.Menu;

/**
 * Interfaz para manejar los clicks en la app
 * @author amna
 * @version 1.0
 */
public interface EventClickCelda {
    void clickCelda(Celda celda);
    boolean OnCreateOptionsMenu(Menu menu);
}
