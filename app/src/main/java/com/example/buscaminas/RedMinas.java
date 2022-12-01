package com.example.buscaminas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 *
 * @author amna
 * @version 1.0
 */
public class RedMinas {
    private final List<Celda> celdas;
    private final int tamanio;

    public RedMinas(int tamanio) {
        this.tamanio = tamanio;
        celdas = new ArrayList<>();
        for (int i = 0; i < tamanio * tamanio; i++) {
            //generación del campo de minas a partir de un tamaño determinado
            celdas.add(new Celda(Celda.VACIO));
        }
    }

    /**
     * Convierte ambas coordenadas en un índice que será la posición de la mina
     * en la lista donde lo incluiremos
     * @param x coordenada x de la red de minas
     * @param y coordenada y de la red de minas
     * @return devuelve la posición donde está situada la mina
     */
    public int toIndex(int x, int y) {
        return x + (y * tamanio);
    }

    /**
     * Se le pasa un índice y lo convertimos en coordenadas x,y
     * para facilitar la creación de minas, números y celdas
     * adyacentes
     * @param indice indice
     * @return contiene las coordenadas de x,y
     */
    public int[] toXY(int indice){
        int y = indice / tamanio;
        int x = indice - (y*tamanio);
        return new int[]{x, y};
    }

    /**
     * Generador de minas que las posiciona de forma
     * aleatoria a partir de los índices generados
     * en métodos anteriores
     * @param numBombas bombas que habrán en la red o tablero
     */
    public void generacionRed(int numBombas){
        int bombas = 0;
        while(bombas < numBombas){
            int x = new Random().nextInt(tamanio);
            int y = new Random().nextInt(tamanio);

            int indice = toIndex(x, y);
            if(celdas.get(indice).getNum() == Celda.VACIO){
                celdas.set(indice, new Celda(Celda.BOMBA));
                numBombas++;
            }
        }
    }


    public List<Celda> getCeldas() {
        return celdas;
    }
}
