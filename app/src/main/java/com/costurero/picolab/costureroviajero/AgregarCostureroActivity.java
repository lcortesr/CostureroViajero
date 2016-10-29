package com.costurero.picolab.costureroviajero;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarCostureroActivity extends AppCompatActivity {
    CostDbHelper dbCosturero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Write();
        setContentView(R.layout.activity_agregar_costurero);
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

    public void crearCosturero(View view){
        EditText nombre=(EditText) findViewById(R.id.nombreCosturero_inp);
        EditText municipio=(EditText) findViewById(R.id.municipioCosturero_inp);
        EditText lugar=(EditText) findViewById(R.id.lugarCosturero);
        EditText historia=(EditText) findViewById(R.id.historiaCosturero_inp);
        float latitud=-74.47f;
        float longitud=4.59f;
        if(municipio.getText().toString()!=null && nombre.getText()!=null){
            long insersion=dbCosturero.insertLocalizacion(lugar.getText().toString(),municipio.getText().toString(),""+latitud,""+longitud);
            if(insersion>=0){//Insersión de localización correcta
                long insersionCosturero=dbCosturero.insertCosturero(nombre.getText().toString(),insersion,historia.getText().toString());
                if(insersionCosturero>=0){
                    Intent intent = new Intent(this, CosturerosActivity.class);
                    startActivity(intent);
                }
                else
                    muestraError("No se pudo agregar el costurero");
            }
            else
                muestraError("No se pudo agregar la localización");
        }
        else
            muestraError("No se pudo agregar el costurero");
    }

    public void muestraError(String text){
        Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
