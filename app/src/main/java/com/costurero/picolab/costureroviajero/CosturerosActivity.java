package com.costurero.picolab.costureroviajero;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class CosturerosActivity extends AppCompatActivity {//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costureros);
        Typeface tituloF = Typeface.createFromAsset(getAssets(), "century-expanded-bold-bt.ttf");
        TextView titulo = (TextView)findViewById(R.id.tituloCostureros_txt);
        titulo.setTypeface(tituloF);
    }

    @Override
    protected void onStart() {
        super.onStart();
        CostDbHelper dbCosturero;
        RecyclerView rvContacts;
        ArrayList<Costurero> listaCostureros=new ArrayList<Costurero>();
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Read();
        Cursor lectura=dbCosturero.readCosturero();
        Cursor lecturaLoc=dbCosturero.readLocalizacion();
        while(lectura.moveToNext()){
            String name = lectura.getString(lectura.getColumnIndex(CostureroContractClass.CostureroEntry.nombre));
            String idLoc = lectura.getString(lectura.getColumnIndex(CostureroContractClass.CostureroEntry.localizacion));
            String hist = lectura.getString(lectura.getColumnIndex(CostureroContractClass.CostureroEntry.historia));
            String pathC = lectura.getString(lectura.getColumnIndex(CostureroContractClass.CostureroEntry.path));
            lecturaLoc.moveToFirst();
            lecturaLoc.moveToPrevious();
            while(lecturaLoc.moveToNext()){
                String idA=lecturaLoc.getString(lecturaLoc.getColumnIndex(LocalizacionContractClass.LocalizacionEntry._ID));
                if(idA.equals(idLoc)) {
                    String locTown = lecturaLoc.getString(lecturaLoc.getColumnIndex(LocalizacionContractClass.LocalizacionEntry.municipio));
                    String locPlace = lecturaLoc.getString(lecturaLoc.getColumnIndex(LocalizacionContractClass.LocalizacionEntry.nombre));
                    float lat = lecturaLoc.getFloat(lecturaLoc.getColumnIndex(LocalizacionContractClass.LocalizacionEntry.latitud));
                    float lng = lecturaLoc.getFloat(lecturaLoc.getColumnIndex(LocalizacionContractClass.LocalizacionEntry.longitud));
                    listaCostureros.add(new Costurero(name, locTown, locPlace, pathC, lat, lng, hist));
                }
            }
        }
        if(listaCostureros.size()>0) {
            rvContacts = (RecyclerView) findViewById(R.id.rexyclerCosturero_lst);
            CosturerosAdapter adapter = new CosturerosAdapter(getBaseContext(), listaCostureros);
            rvContacts.setLayoutManager(new LinearLayoutManager(this));
            rvContacts.setAdapter(adapter);
        }
    }
    public void abrirMenu(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    public void abrirNuevoCosturero(View view){
        Intent intent = new Intent(this, AgregarCostureroActivity.class);
        startActivity(intent);
    }
    public void abrirGaleria(View view){
        Intent intent = new Intent(this, GaleriaActivity.class);
        startActivity(intent);
    }
    public void abrirInfo(View view){
        Intent intent = new Intent(this, ProyectoActivity.class);
        startActivity(intent);
    }
}