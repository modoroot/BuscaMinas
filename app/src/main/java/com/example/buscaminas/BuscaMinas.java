package com.example.buscaminas;


import java.util.ArrayList;
import java.util.List;

/**
 * @author amna
 * @version 1.0
 */
public class BuscaMinas {
    private RedMinas redMinas;

    private boolean descubrirCasilla;
    private boolean juegoTerminado;
    private boolean isTiempoAcabado;
    private boolean banderaActivada;

    private int numBanderas;
    private int numeroBombas;

    /**
     * Constructor de la clase
     *
     * @param tamanio      tablero (8x8, etc.)
     * @param numeroBombas número bombas en el tablero
     */
    public BuscaMinas(int tamanio, int numeroBombas) {
        this.descubrirCasilla = true;
        this.juegoTerminado = false;
        this.isTiempoAcabado = false;
        this.banderaActivada = false;
        this.numBanderas = 0;
        this.numeroBombas = numeroBombas;
        redMinas = new RedMinas(tamanio);
        redMinas.generacionRed(numeroBombas);
    }

    /**
     * destapa la celda
     *
     * @param celda celda en el tablero
     */
    public void manejadorClickCeldas(Celda celda) {
        if (!juegoTerminado && !isPartidaGanada() && !isTiempoAcabado) {
            if (descubrirCasilla) {
                clear(celda);
            }else if(banderaActivada){
                bandera(celda);
            }
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
            //el usuario clicka una bomba. El juego termina
        } else if (celda.getNum() == Celda.BOMBA) {
            juegoTerminado = true;
        }
    }

    public boolean isPartidaGanada() {
        int celdasPorRevelar = 0;
        for (Celda c : getRedMinas().getCeldas()) {
            if (c.getNum() != Celda.BOMBA && c.getNum() != Celda.VACIO && !c.getRevelado()) {
                celdasPorRevelar++;
            }
        }
        if (celdasPorRevelar == 0)
            return true;
        else return false;
    }

    public void sinTiempo(){
        isTiempoAcabado = true;
    }

    public void bandera(Celda celda){
        if (!celda.isBandera()){
            celda.setBandera(!celda.isBandera());
            //contador del TextView
            int contador = 0;
            for (Celda c : getRedMinas().getCeldas()) {
                if (c.isBandera()) {
                    contador++;
                }
            }
            numBanderas = contador;
        }
    }

    public void activarDesactivarModoBandera(){
        descubrirCasilla = !descubrirCasilla;
        banderaActivada = !banderaActivada;
    }



    public RedMinas getRedMinas() {
        return redMinas;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public boolean isBanderaActivada() {
        return banderaActivada;
    }

    public int getNumBanderas() {
        return numBanderas;
    }

    public int getNumeroBombas() {
        return numeroBombas;
    }
}
