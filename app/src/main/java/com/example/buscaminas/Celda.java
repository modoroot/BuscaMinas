package com.example.buscaminas;

import android.telephony.CellIdentity;

public class Celda {
    public static final int BOMBA = -1;
    public static final int VACIO = 0;

    private int num;
    private boolean revelado;
    private boolean marcado;

    public Celda(int num){
        this.num = num;
        this.revelado = false;
        this.marcado = false;
    }
}
