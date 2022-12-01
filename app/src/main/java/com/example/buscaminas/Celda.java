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
    private boolean marcado;

    public Celda(int num) {
        this.num = num;
        this.revelado = false;
        this.marcado = false;
    }

    public int getNum() {
        return num;
    }

    public boolean getRevelado() {
        return revelado;
    }

    public boolean getMarcado() {
        return marcado;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setRevelado(boolean revelado) {
        this.revelado = revelado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }
}
