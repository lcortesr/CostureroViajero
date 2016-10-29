package com.costurero.picolab.costureroviajero;

/**
 * Created by PiCoLab on 2016-10-10.
 */

public class Costurero {
    private String nombre;
    private String municipio;
    private String lugar;
    private float latitud;
    private float longitud;
    Costurero(String nom, String mun, String lug, float lat, float lng){
        nombre=nom;
        municipio=mun;
        lugar=lug;
        latitud=lat;
        longitud=lng;
    }
    public String getNombre(){
        return nombre;
    }
    public String getMunicipio(){
        return municipio;
    }
    public String getLugar(){
        return lugar;
    }
    public float getLatitud(){
        return latitud;
    }

    public float getLongitud(){
        return longitud;
    }
}
