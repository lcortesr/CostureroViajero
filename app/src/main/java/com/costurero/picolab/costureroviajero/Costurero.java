package com.costurero.picolab.costureroviajero;

/**
 * Created by PiCoLab on 2016-10-10.
 */

public class Costurero {
    private String nombre;
    private String municipio;
    private String lugar;
    private String historia;
    private String foto;
    private float latitud;
    private float longitud;
    Costurero(String nom, String mun, String lug, String fot, float lat, float lng, String hist){
        nombre=nom;
        municipio=mun;
        lugar=lug;
        foto=fot;
        latitud=lat;
        longitud=lng;
        historia=hist;
    }
    public String getNombre(){
        return nombre;
    }
    public String getMunicipio(){return municipio;}
    public String getLugar(){
        return lugar;
    }
    public String getFoto(){
        return foto;
    }
    public float getLatitud(){
        return latitud;
    }
    public String getHistoria(){return historia;}
    public float getLongitud(){
        return longitud;
    }
}
