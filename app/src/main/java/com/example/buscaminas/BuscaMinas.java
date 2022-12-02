package com.example.buscaminas;


import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la lógica de las minas
 * y banderas
 * @author amna
 * @version 1.0
 */
public class BuscaMinas {
    private RedMinas redMinas;

    private boolean descubrirCasilla;
    private boolean juegoTerminado;
    private boolean isTiempoAcabado;
    private boolean banderaActivada;

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

        redMinas = new RedMinas(tamanio);
        redMinas.generacionRed(numeroBombas);
    }

    /**
     * Método que comprueba cada vez que se pulsa una celda
     * las posibles condiciones que podrían darse (
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
     * Revela la celda clickada y sus adyacentes
     *
     * @param celda celda en el tablero
     */
    public void clear(Celda celda) {
        //celda seleccionada
        int indice = getRedMinas().getCeldas().indexOf(celda);
        //revela las celdas según la celda seleccionada
        getRedMinas().getCeldas().get(indice).setRevelado(true);

        if (celda.getNum() == Celda.VACIO) {
            //para destapar en conjunto
            List<Celda> destapar = new ArrayList<>();
            //celdas adyacentes de la celda seleccionada hasta las bombas
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

    /**
     * comprueba si quedan 0 celdas por revelar (sin contar celdas con bomba)
     * @return devuelve true si quedan 0 (ganar), false si es distinto a 0 (aún no ha ganado)
     */
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

    /**
     * método que maneja una variable booleana que cuando se invoca a dicho método,
     * la activa
     */
    public void sinTiempo(){
        isTiempoAcabado = true;
    }

    /**
     * método que maneja las banderas de las celdas
     * @param celda celda del buscaminas
     */
    public void bandera(Celda celda){
        //si una celda ya tiene una bandera, no deja colocar otra
        if (!celda.isBandera()){
            celda.setBandera(!celda.isBandera());
        }
    }

    /**
     * Método que controla el modo click-bandera
     */
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

}
