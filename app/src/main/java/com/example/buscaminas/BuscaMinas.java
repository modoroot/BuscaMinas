package com.example.buscaminas;

public class BuscaMinas {
    private final RedMinas redMinas;

    public BuscaMinas(int tamanio){
        redMinas = new RedMinas(tamanio);
    }
    public RedMinas getRedMinas(){
        return redMinas;
    }
}
