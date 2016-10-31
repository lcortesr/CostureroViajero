package com.costurero.picolab.costureroviajero;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GaleriaActivity extends AppCompatActivity {
    Context context;
    RecyclerView rvFotos;
    FotoVideoAdapter adapter;
    ArrayList<FotoVideo> listaFotos=new ArrayList<FotoVideo>();
    ArrayList<FotoVideo> listaFotosAux=new ArrayList<FotoVideo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        context= getApplicationContext();
        SearchView buscador=(SearchView) findViewById(R.id.buscadorEtiquetas_srch);
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listaFotos.clear();
                int id=0;
                String idIA="NA", idDA= "NA", pathIA="NA", pathDA="NA", etiquetasPIA="NA", etiquetasPDA="NA", etiquetasSIA="NA", etiquetasSDA="NA";
                int tIA=-1, tDA=-1;
                for(int i=0;i<listaFotosAux.size();i++){//Por cada elemento de la lista
                    if(!listaFotosAux.get(i).getEtiquetaP().contains(query)&& !listaFotosAux.get(i).getEtiquetaS().contains(query)){
                        if(!listaFotosAux.get(i).getEtiquetaPD().contains(query)&& !listaFotosAux.get(i).getEtiquetaSD().contains(query)) {
                            //Si ninguna de las dos tiene la palabra clave, ni Izq, ni Der
                        }
                        else{
                            //Si la izquierda no tiene la palabra clave pero la derecha sí
                            if(id==0){
                                idIA=listaFotosAux.get(i).getIdD();
                                pathIA=listaFotosAux.get(i).getPathD();
                                etiquetasPIA=listaFotosAux.get(i).getEtiquetaPD();
                                etiquetasSIA=listaFotosAux.get(i).getEtiquetaSD();
                                tIA=listaFotosAux.get(i).getTipoD();
                                id=1;
                                if(i==listaFotosAux.size()-1) {
                                    listaFotos.add(new FotoVideo(idIA,pathIA,etiquetasPIA,etiquetasSIA,tIA,"0","NULL"," "," ",0));
                                    tIA=-1;
                                }
                            }
                            else{
                                idDA=listaFotosAux.get(i).getIdD();
                                pathDA=listaFotosAux.get(i).getPathD();
                                etiquetasPDA=listaFotosAux.get(i).getEtiquetaPD();
                                etiquetasSDA=listaFotosAux.get(i).getEtiquetaSD();
                                tDA=listaFotosAux.get(i).getTipoD();
                                id=0;
                                listaFotos.add(new FotoVideo(idIA,pathIA,etiquetasPIA,etiquetasSIA,tIA,idDA,pathDA,etiquetasPDA,etiquetasSDA,tDA));
                                tIA=-1;
                            }
                        }
                    }
                    else{
                        if(!listaFotosAux.get(i).getEtiquetaPD().contains(query)&& !listaFotosAux.get(i).getEtiquetaSD().contains(query)) {
                            //Si la derecha no tiene la palabra clave pero la izquierda sí
                            if(id==0){
                                idIA=listaFotosAux.get(i).getId();
                                pathIA=listaFotosAux.get(i).getPath();
                                etiquetasPIA=listaFotosAux.get(i).getEtiquetaP();
                                etiquetasSIA=listaFotosAux.get(i).getEtiquetaS();
                                tIA=listaFotosAux.get(i).getTipo();
                                id=1;
                                if(i==listaFotosAux.size()-1) {
                                    listaFotos.add(new FotoVideo(idIA,pathIA,etiquetasPIA,etiquetasSIA,tIA,"0","NULL"," "," ",0));
                                    tIA=-1;
                                }
                            }
                            else{
                                idDA=listaFotosAux.get(i).getIdD();
                                pathDA=listaFotosAux.get(i).getPath();
                                etiquetasPDA=listaFotosAux.get(i).getEtiquetaP();
                                etiquetasSDA=listaFotosAux.get(i).getEtiquetaS();
                                tDA=listaFotosAux.get(i).getTipo();
                                id=0;
                                listaFotos.add(new FotoVideo(idIA,pathIA,etiquetasPIA,etiquetasSIA,tIA,idDA,pathDA,etiquetasPDA,etiquetasSDA,tDA));
                                tIA=-1;
                            }
                        }
                        else{
                            //Si ambas, izquierda y derecha, tienen la palabra clave
                            if(id==0){
                                pathIA=listaFotosAux.get(i).getPath();
                                etiquetasPIA=listaFotosAux.get(i).getEtiquetaP();
                                etiquetasSIA=listaFotosAux.get(i).getEtiquetaS();
                                tIA=listaFotosAux.get(i).getTipo();
                                pathDA=listaFotosAux.get(i).getPathD();
                                etiquetasPDA=listaFotosAux.get(i).getEtiquetaPD();
                                etiquetasSDA=listaFotosAux.get(i).getEtiquetaSD();
                                tDA=listaFotosAux.get(i).getTipoD();
                                listaFotos.add(new FotoVideo(idIA,pathIA,etiquetasPIA,etiquetasSIA,tIA,idDA,pathDA,etiquetasPDA,etiquetasSDA,tDA));
                                tIA=-1;
                            }
                            else{
                                pathDA=listaFotosAux.get(i).getPath();
                                etiquetasPDA=listaFotosAux.get(i).getEtiquetaP();
                                etiquetasSDA=listaFotosAux.get(i).getEtiquetaS();
                                tDA=listaFotosAux.get(i).getTipo();
                                listaFotos.add(new FotoVideo(idIA,pathIA,etiquetasPIA,etiquetasSIA,tIA,idDA,pathDA,etiquetasPDA,etiquetasSDA,tDA));
                                pathIA=listaFotosAux.get(i).getPathD();
                                etiquetasPIA=listaFotosAux.get(i).getEtiquetaPD();
                                etiquetasSIA=listaFotosAux.get(i).getEtiquetaSD();
                                tIA=listaFotosAux.get(i).getTipoD();
                            }
                        }
                    }
                }
                if(tIA!=-1) {
                    listaFotos.add(new FotoVideo(idIA,pathIA,etiquetasPIA,etiquetasSIA,tIA,"0","NULL"," "," ",0));
                }
                adapter = new FotoVideoAdapter(getBaseContext(), listaFotos);
                rvFotos.setAdapter(adapter);
                //rvFotos.getAdapter().notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    listaFotos.clear();
                    for(int fv=0;fv<listaFotosAux.size();fv++){
                        listaFotos.add(listaFotosAux.get(fv));
                    }
                    adapter = new FotoVideoAdapter(getBaseContext(), listaFotos);
                    rvFotos.setAdapter(adapter);
                }
                return false;
            }
        });
        Typeface tituloF = Typeface.createFromAsset(getAssets(), "century-expanded-bold-bt.ttf");

        TextView titulo = (TextView)findViewById(R.id.tituloGaleria_txt);
        titulo.setTypeface(tituloF);
    }
    @Override
    protected void onStart() {
        super.onStart();
        CostDbHelper dbCosturero;
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Read();
        Cursor lectura=dbCosturero.readFotoVideo();
        int l=0;
        while(lectura.moveToNext()){
            String idA=""+(lectura.getInt(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.encuentro)));
            String pathF = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.path));
            String etP = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetas));
            String etS = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetasS));
            int tipoFV=lectura.getInt(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.tipo));
            if(lectura.moveToNext()){
                String idB=""+(lectura.getInt(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.encuentro)));
                String pathFD = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.path));
                String etPD = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetas));
                String etSD = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetasS));
                int tipoFVD=lectura.getInt(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.tipo));
                listaFotos.add(new FotoVideo(idA,pathF,etP,etS,tipoFV,idB,pathFD,etPD,etSD,tipoFVD));
            }
            else{
                listaFotos.add(new FotoVideo(idA,pathF,etP,etS,tipoFV,"0","NULL"," "," ",0));
            }
            l++;
        }
        if(listaFotos.size()>0) {
            for(int fv=0;fv<listaFotos.size();fv++){
                listaFotosAux.add(listaFotos.get(fv));
            }
            rvFotos= (RecyclerView) findViewById(R.id.recyclerFotos_lst);
            adapter = new FotoVideoAdapter(getBaseContext(), listaFotos);
            rvFotos.setLayoutManager(new LinearLayoutManager(this));
            rvFotos.setAdapter(adapter);
        }
    }
    public void abrirMenu(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    public void abrirCostureros(View view){
        Intent intent = new Intent(this, CosturerosActivity.class);
        startActivity(intent);
    }
    public void abrirInfo(View view){
        Intent intent = new Intent(this, ProyectoActivity.class);
        startActivity(intent);
    }
}
