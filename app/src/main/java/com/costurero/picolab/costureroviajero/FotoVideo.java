package com.costurero.picolab.costureroviajero;

/**
 * Created by PiCoLab on 2016-10-13.
 */

public class FotoVideo {
    private String path;
    private String etiquetaP;
    private String etiquetaS;
    private int tipo;
    private int sincronizado;

    private String pathD;
    private String etiquetaPD;
    private String etiquetaSD;
    private int tipoD;
    private int sincronizadoD;

    FotoVideo(String pat, String etP, String etS, int t,String patD, String etPD, String etSD, int tD){
        path=pat;
        etiquetaP=etP;
        etiquetaS=etS;
        tipo=t;
        pathD=patD;
        etiquetaPD=etPD;
        etiquetaSD=etSD;
        tipoD=tD;
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

