package com.costurero.picolab.costureroviajero;

/**
 * Created by PiCoLab on 2016-10-11.
 */

public class Localizacion {
    private float latitud;
    private float longitud;
    private String municipio;
    private String lugar;

    Localizacion(String lug, String mun, float lat, float lng){
        lugar=lug;
        municipio=mun;
        latitud=lat;
        longitud=lng;
    }
}
