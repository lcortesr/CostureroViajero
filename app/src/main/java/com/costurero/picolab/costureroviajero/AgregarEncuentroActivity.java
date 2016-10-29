package com.costurero.picolab.costureroviajero;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class AgregarEncuentroActivity extends AppCompatActivity {
    CostDbHelper dbCosturero;
    private String idCosturero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_encuentro);
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Write();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            idCosturero=bundle.getString("ID_COSTURERO");
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
    public void abrirInfo(View view){
        Intent intent = new Intent(this, ProyectoActivity.class);
        startActivity(intent);
    }
    public void crearEncuentro(View view) {
        DatePicker datePicker=(DatePicker) findViewById(R.id.fechaEncuentro_dat);
        TimePicker timePicker=(TimePicker) findViewById(R.id.horaEncuentro_hou);
        String fechaEncuentro = ""+datePicker.getYear() + "-"+datePicker.getMonth() +"-"+datePicker.getDayOfMonth() + " a las "+timePicker.getCurrentHour()+"h"+timePicker.getCurrentMinute();
        long insersion=dbCosturero.insertEncuentro(fechaEncuentro,Long.parseLong(idCosturero));
        if(insersion>=0) {
            Intent intent = new Intent(this, EncuentrosActivity.class);
            intent.putExtra("ID_COSTURERO", ""+Long.parseLong(idCosturero));
            startActivity(intent);
        }
    }
}
