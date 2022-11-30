package com.example.buscaminas;

import java.util.ArrayList;
import java.util.List;

public class RedMinas {
    private List<Celda> celdas;
    private int tamanio;

    public RedMinas(int tamanio){
        this.tamanio = tamanio;
        celdas = new ArrayList<>();
        for (int i = 0; i < tamanio * tamanio; i++) {
            //generación del campo de minas a partir de un tamaño determinado
            celdas.add(new Celda(Celda.VACIO));
        }
    }

}
