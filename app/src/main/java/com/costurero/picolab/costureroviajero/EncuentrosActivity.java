package com.costurero.picolab.costureroviajero;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class EncuentrosActivity extends AppCompatActivity {
    private String message;
    private String tituloCosturero;
    private TextView textoTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuentros);
    }

    @Override
    protected void onStart() {
        super.onStart();
        CostDbHelper dbCosturero;
        RecyclerView rvEncuentros;
        ArrayList<Encuentro> listaEncuentros=new ArrayList<Encuentro>();
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Read();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            message=""+ Integer.parseInt(bundle.getString("ID_COSTURERO"));
            Cursor lectura=dbCosturero.readCosturero();
            while(lectura.moveToNext()){
                String idA=""+(lectura.getInt(lectura.getColumnIndex(CostureroContractClass.CostureroEntry._ID))-1);
                if(idA.equals(message)){
                    tituloCosturero=lectura.getString(lectura.getColumnIndex(CostureroContractClass.CostureroEntry.nombre));
                    textoTitulo=(TextView) findViewById(R.id.tituloEncuentros_txt);
                    textoTitulo.setText("Encuentros - "+tituloCosturero);
                    lectura.moveToLast();
                }
            }
        }
        Cursor lecturaEncuentro=dbCosturero.readEncuentro();
        while(lecturaEncuentro.moveToNext()) {
            if(lecturaEncuentro.getString(lecturaEncuentro.getColumnIndex(EncuentroContractClass.EncuentroEntry.costurero)).equals(message)) {
                //String nameC = lecturaEncuentro.getString(lecturaEncuentro.getColumnIndex(EncuentroContractClass.EncuentroEntry.costurero));
                String nameC = tituloCosturero;
                String fechaC = lecturaEncuentro.getString(lecturaEncuentro.getColumnIndex(EncuentroContractClass.EncuentroEntry.fecha));
                int idC=lecturaEncuentro.getInt(lecturaEncuentro.getColumnIndex(EncuentroContractClass.EncuentroEntry._ID));
                listaEncuentros.add(new Encuentro(nameC, fechaC, idC));
            }
        }
        if(listaEncuentros.size()>0) {
            rvEncuentros = (RecyclerView) findViewById(R.id.recyclerEncuentros_lst);
            EncuentrosAdapter adapter = new EncuentrosAdapter(getBaseContext(), listaEncuentros);
            rvEncuentros.setLayoutManager(new LinearLayoutManager(this));
            rvEncuentros.setAdapter(adapter);
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
    public void abrirGaleria(View view){
        Intent intent = new Intent(this, GaleriaActivity.class);
        startActivity(intent);
    }
    public void agregarNuevoEncuentro(View view){
        Intent intent = new Intent(this, AgregarEncuentroActivity.class);
        intent.putExtra("ID_COSTURERO", message);
        startActivity(intent);
    }
    public void abrirInfo(View view){
        Intent intent = new Intent(this, ProyectoActivity.class);
        startActivity(intent);
    }
}
