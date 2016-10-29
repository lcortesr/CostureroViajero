package com.costurero.picolab.costureroviajero;

/**
 * Created by PiCoLab on 2016-10-11.
 */

public class Encuentro {
    private String nombreCosturero;
    private String fecha;
    private int id;
    Encuentro(String nomCosturero, String fe, int i){
        nombreCosturero=nomCosturero;
        fecha=fe;
        id=i;
    }
    public String getNombreCosturero(){
        return nombreCosturero;
    }
    public String getFecha(){
        return fecha;
    }
    public int getId(){
        return id;
    }

}
