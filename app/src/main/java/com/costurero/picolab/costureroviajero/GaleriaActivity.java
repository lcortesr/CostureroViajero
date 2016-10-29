package com.costurero.picolab.costureroviajero;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class GaleriaActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        context= getApplicationContext();
    }
    @Override
    protected void onStart() {
        super.onStart();
        CostDbHelper dbCosturero;
        RecyclerView rvFotos;
        ArrayList<FotoVideo> listaFotos=new ArrayList<FotoVideo>();
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Read();
        Cursor lectura=dbCosturero.readFotoVideo();
        int l=0;
        while(lectura.moveToNext()){
            //String idA=""+(lectura.getInt(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.encuentro)));
            String pathF = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.path));
            String etP = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetas));
            String etS = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetasS));
            int tipoFV=lectura.getInt(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.tipo));
            if(lectura.moveToNext()){
                String pathFD = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.path));
                String etPD = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetas));
                String etSD = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetasS));
                int tipoFVD=lectura.getInt(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.tipo));
                listaFotos.add(new FotoVideo(pathF,etP,etS,tipoFV,pathFD,etPD,etSD,tipoFVD));
            }
            else{
                listaFotos.add(new FotoVideo(pathF,etP,etS,tipoFV,"NULL"," "," ",0));
            }
            l++;
        }
        if(listaFotos.size()>0) {
            rvFotos= (RecyclerView) findViewById(R.id.recyclerFotos_lst);
            FotoVideoAdapter adapter = new FotoVideoAdapter(getBaseContext(), listaFotos);
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
