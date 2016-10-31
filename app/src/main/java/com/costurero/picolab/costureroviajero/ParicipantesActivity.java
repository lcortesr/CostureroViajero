package com.costurero.picolab.costureroviajero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ParicipantesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paricipantes);
        Typeface tituloF = Typeface.createFromAsset(getAssets(), "century-expanded-bold-bt.ttf");
        Typeface cursiva = Typeface.createFromAsset(getAssets(), "century-expanded-italic.ttf");
        TextView titulo = (TextView)findViewById(R.id.tituloParticipantes_txt);
        titulo.setTypeface(tituloF);
        TextView historia = (TextView)findViewById(R.id.histParticipante_txt);
        historia.setTypeface(cursiva);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            String idP="" + Integer.parseInt(bundle.getString("ID_PARTICIPANTE"));
            String nomP=bundle.getString("NOMBRE_PARTICIPANTE");
            String pathP=bundle.getString("PATH_PARTICIPANTE");
            String histP=bundle.getString("HISTORIA_PARTICIPANTE");
            TextView titulo=(TextView) findViewById(R.id.tituloParticipantes_txt);
            TextView historia=(TextView) findViewById(R.id.histParticipante_txt);
            titulo.setText(nomP);
            historia.setText(histP);
            ImageView imagen = (ImageView) findViewById(R.id.imgParticipante_img);
            if(!(pathP.equals("") || pathP.equals("null"))) {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(pathP, bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;
                // Determine how much to scale down the ima/*ge
                int scaleFactor = Math.min(photoW / 800, photoH / 400);
                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;
                // Set item views based on the data model
                Bitmap bitmap = BitmapFactory.decodeFile(pathP, bmOptions);
                imagen.setImageBitmap(bitmap);
            }
            else {
                imagen.setImageResource(R.drawable.foto_ejemplo);
            }
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
}
