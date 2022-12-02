package com.example.buscaminas;


/**
 * Clase que contiene las propiedades principales de las celdas
 * @author amna
 * @version 1.0
 */
public class Celda {
    public static final int BOMBA = -1;
    public static final int VACIO = 0;

    private int num;
    private boolean revelado;
    private boolean isBandera;

    /**
     * Constructor de la clase
     * @param num celda
     */
    public Celda(int num) {
        this.num = num;
        this.revelado = false;
        this.isBandera = false;
    }

    /**
     *
     * @return num celdas
     */
    public int getNum() {
        return num;
    }

    /**
     *
     * @return casilla revelada
     */
    public boolean getRevelado() {
        return revelado;
    }

    /**
     *
     * @return activada o desactivada modo bandera
     */
    public boolean isBandera() {
        return isBandera;
    }

    /**
     *
     * @param revelado casilla revelada
     */
    public void setRevelado(boolean revelado) {
        this.revelado = revelado;
    }

    /**
     *
     * @param marcado casillas marcadas
     */
    public void setBandera(boolean marcado) {
        isBandera = marcado;
    }
}