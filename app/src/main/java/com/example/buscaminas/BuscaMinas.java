package com.example.buscaminas;


/**
 *
 * @author amna
 * @version 1.0
 */
public class BuscaMinas {
    private RedMinas redMinas;
    private boolean destapado;


    /**
     * Constructor de la clase
     * @param tamanio tablero (8x8, etc.)
     * @param numeroBombas número bombas en el tablero
     */
    public BuscaMinas(int tamanio, int numeroBombas){
        this.destapado = true;
        redMinas = new RedMinas(tamanio);
        redMinas.generacionRed(numeroBombas);
    }

    public void manejadorClickCeldas(Celda celda){
        if(destapado){
            clear(celda);
        }
    }

    public void clear(Celda celda){
            int indice = getRedMinas().getCeldas().indexOf(celda);
            getRedMinas().getCeldas().get(indice).setRevelado(true);
    }

    public RedMinas getRedMinas(){
        return redMinas;
    }
}
