package com.example.buscaminas;


import java.util.ArrayList;
import java.util.List;

/**
 * @author amna
 * @version 1.0
 */
public class BuscaMinas {
    private RedMinas redMinas;
    private boolean destapado;
    private boolean juegoTerminado;

    /**
     * Constructor de la clase
     *
     * @param tamanio      tablero (8x8, etc.)
     * @param numeroBombas número bombas en el tablero
     */
    public BuscaMinas(int tamanio, int numeroBombas) {
        this.destapado = true;
        this.juegoTerminado = false;
        redMinas = new RedMinas(tamanio);
        redMinas.generacionRed(numeroBombas);
    }

    /**
     * destapa la celda
     *
     * @param celda celda en el tablero
     */
    public void manejadorClickCeldas(Celda celda) {
        if (destapado) {
            clear(celda);
        }
    }

    /**
     * Revela la celda clickada
     *
     * @param celda celda en el tablero
     */
    public void clear(Celda celda) {
        int indice = getRedMinas().getCeldas().indexOf(celda);
        getRedMinas().getCeldas().get(indice).setRevelado(true);


        if (celda.getNum() == Celda.VACIO) {
            //para destapar en conjunto
            List<Celda> destapar = new ArrayList<>();
            List<Celda> comprobarCeldasAdyacentes = new ArrayList<>();

            comprobarCeldasAdyacentes.add(celda);

            while (comprobarCeldasAdyacentes.size() > 0) {
                Celda cell = comprobarCeldasAdyacentes.get(0);
                int indiceCelda = getRedMinas().getCeldas().indexOf(cell);
                int[] posicionCelda = getRedMinas().toXY(indiceCelda);

                for (Celda adyacente : getRedMinas().celdasAdyacentes(posicionCelda[0], posicionCelda[1])) {
                    if (adyacente.getNum() == Celda.VACIO) {
                        if (!destapar.contains(adyacente)) {
                            if (!comprobarCeldasAdyacentes.contains(adyacente)) {
                                comprobarCeldasAdyacentes.add(adyacente);
                            }
                        }
                    } else {
                        if (!destapar.contains(adyacente)) {
                            destapar.add(adyacente);
                        }
                    }
                }
                comprobarCeldasAdyacentes.remove(cell);
                destapar.add(cell);
            }
            for (Celda celdas : destapar) {
                celdas.setRevelado(true);
            }
        }
    }

    public RedMinas getRedMinas() {
        return redMinas;
    }
}
