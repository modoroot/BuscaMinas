package com.example.buscaminas;


/**
 *
 * @author amna
 * @version 1.0
 */
public class Celda {
    public static final int BOMBA = -1;
    public static final int VACIO = 0;

    private int num;
    private boolean revelado;
    private boolean isMarcado;

    public Celda(int num) {
        this.num = num;
        this.revelado = false;
        this.isMarcado = false;
    }

    public int getNum() {
        return num;
    }

    public boolean getRevelado() {
        return revelado;
    }

    public boolean getMarcado() {
        return isMarcado;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * para casillas reveladas
     * @param revelado casilla revelada
     */
    public void setRevelado(boolean revelado) {
        this.revelado = revelado;
    }

    /**
     * casillas marcadas con el banderín
     * @param marcado casillas marcadas
     */
    public void setMarcado(boolean marcado) {
        isMarcado = marcado;
    }
}
