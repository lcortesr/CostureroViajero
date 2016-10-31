package com.costurero.picolab.costureroviajero;

/**
 * Created by PiCoLab on 2016-10-13.
 */

public class FotoVideo {
    private String id;
    private String path;
    private String etiquetaP;
    private String etiquetaS;
    private int tipo;
    private int sincronizado;

    private String idD;
    private String pathD;
    private String etiquetaPD;
    private String etiquetaSD;
    private int tipoD;
    private int sincronizadoD;

    FotoVideo(String i, String pat, String etP, String etS, int t,String iD, String patD, String etPD, String etSD, int tD){
        id=i;
        path=pat;
        etiquetaP=etP;
        etiquetaS=etS;
        tipo=t;
        idD=iD;
        pathD=patD;
        etiquetaPD=etPD;
        etiquetaSD=etSD;
        tipoD=tD;
    }

    public String getId(){
        return id;
    }
    public String getPath(){
        return path;
    }
    public String getEtiquetaP(){
        return etiquetaP;
    }
    public String getEtiquetaS(){
        return etiquetaS;
    }
    public int getTipo(){
        return tipo;
    }
    public int getSincronizado(){
        return sincronizado;
    }

    public String getIdD(){return idD; }
    public String getPathD(){
        return pathD;
    }
    public String getEtiquetaPD(){
        return etiquetaPD;
    }
    public String getEtiquetaSD(){
        return etiquetaSD;
    }
    public int getTipoD(){
        return tipoD;
    }
    public int getSincronizadoD(){
        return sincronizadoD;
    }

}

