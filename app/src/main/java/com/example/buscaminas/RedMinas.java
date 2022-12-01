package com.example.buscaminas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author amna
 * @version 1.0
 */
public class RedMinas {
    private List<Celda> celdas;
    private int tamanio;

    /**
     * Constructor de la clase
     *
     * @param tamanio parámetro para el tamaño del tablero
     */
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
     *
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
     *
     * @param indice indice
     * @return contiene las coordenadas de x,y
     */
    public int[] toXY(int indice) {
        int y = indice / tamanio;
        int x = indice - (y * tamanio);
        return new int[]{x, y};
    }

    /**
     * Generador de minas que las posiciona de forma
     * aleatoria a partir de los índices generados
     * en métodos anteriores
     *
     * @param numBombas bombas que habrán en la red o tablero
     */
    public void generacionRed(int numBombas) {
        int bombas = 0;
        //generador bombas hasta que sea mayor que el parámetro
        //del método
        while (bombas < numBombas) {
            //número entre 0 y 8
            int x = new Random().nextInt(tamanio);
            int y = new Random().nextInt(tamanio);
            int indice = toIndex(x, y);

            if (celdas.get(indice).getNum() == Celda.VACIO) {
                celdas.set(indice, new Celda(Celda.BOMBA));
                bombas++;
            }
        }
        /*
         * i: x ; j: y
         * Por cada celda comprobamos que la celda pasada no es una bomba
         * Si no es una bomba, calculamos las celdas adyacentes y la recorremos
         * comprobando por bombas. Se añaden las que encuentre al contador de bombas
         */
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                if (posCelda(i, j).getNum() != Celda.BOMBA) {
                    List<Celda> celdasAdyacentes = celdasAdyacentes(i, j);
                    int cantBombas = 0;
                    for (Celda celda : celdasAdyacentes) {
                        if (celda.getNum() == Celda.BOMBA) {
                            cantBombas++;
                        }
                    }
                    if (cantBombas > 0) {
                        //número de bombas tocando determinada celda
                        celdas.set(i + (j * tamanio), new Celda(cantBombas));
                    }
                }
            }
        }
    }

    /**
     * Método que comprueba la posición de
     * la celda según los parámetros de x,y y
     * los transforma en un índice
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return devuelve el índice y lo guarda en la lista de las celdas
     */
    public Celda posCelda(int x, int y) {
        if (x < 0 || x >= tamanio || y < 0 || y >= tamanio) {
            return null;
        }
        return celdas.get(toIndex(x, y));
    }

    /**
     * Rodea la celda seleccionada en el método posCelda()
     * a partir de las 8 posibles direcciones
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return lista de celdas para la red
     */
    public List<Celda> celdasAdyacentes(int x, int y) {
        List<Celda> celdasAdyacentes = new ArrayList<>();
        List<Celda> listaCeldas = new ArrayList<>();


        listaCeldas.add(posCelda(x - 1, y));
        listaCeldas.add(posCelda(x + 1, y));
        listaCeldas.add(posCelda(x - 1, y - 1));
        listaCeldas.add(posCelda(x, y - 1));
        listaCeldas.add(posCelda(x + 1, y - 1));
        listaCeldas.add(posCelda(x - 1, y + 1));
        listaCeldas.add(posCelda(x, y + 1));
        listaCeldas.add(posCelda(x + 1, y + 1));
        //rodea en las 8 distintas direcciones
//        listaCeldas.add(posCelda(x + 1, y + 1));
//        listaCeldas.add(posCelda(x - 1, y - 1));
//        listaCeldas.add(posCelda(x + 1, y));
//        listaCeldas.add(posCelda(x - 1, y));
//        listaCeldas.add(posCelda(x, y + 1));
//        listaCeldas.add(posCelda(x, y - 1));
//        listaCeldas.add(posCelda(x + 1, y - 1));
//        listaCeldas.add(posCelda(x - 1, y + 1));
        //guarda celdas adyacentes
        for (Celda celda : listaCeldas) {
            if (celda != null) {
                //añade a la lista
                celdasAdyacentes.add(celda);
            }
        }
        //las devuelve
        return celdasAdyacentes;
    }


    public List<Celda> getCeldas() {
        return celdas;
    }
}
