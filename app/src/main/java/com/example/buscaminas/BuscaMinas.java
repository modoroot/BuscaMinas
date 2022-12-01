package com.example.buscaminas;


/**
 *
 * @author amna
 * @version 1.0
 */
public class BuscaMinas {
    private final RedMinas redMinas;

    public BuscaMinas(int tamanio){
        redMinas = new RedMinas(tamanio);
    }
    public RedMinas getRedMinas(){
        return redMinas;
    }
}
