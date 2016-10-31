package com.costurero.picolab.costureroviajero;

/**
 * Created by PiCoLab on 2016-10-30.
 */

public class Participante {
    private String nombre;
    private String path;
    private int id;
    private int idCosturero;
    private String historia;
    Participante(String nom, String his, String pa, int idP, int idC){
        nombre=nom;
        historia=his;
        path=pa;
        idP=id;
        idCosturero=idC;
    }
    public String getNombre(){return nombre;}
    public String getPath(){return path;}
    public String getHistoria(){return historia;}
    public int getId(){return id;}
    public int getIdCosturero(){return idCosturero;}
}
