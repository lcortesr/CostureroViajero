package com.costurero.picolab.costureroviajero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Environment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgregarCostureroActivity extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private Uri fileUri;
    private String pathCost="http://costureroviajero.org/media/files/fotovideo/5/Img_5.jpg";
    CostDbHelper dbCosturero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Write();
        setContentView(R.layout.activity_agregar_costurero);
        Typeface tituloF = Typeface.createFromAsset(getAssets(), "century-expanded-bold-bt.ttf");
        Typeface cuerpoF = Typeface.createFromAsset(getAssets(), "century-expanded-regular.ttf");
        TextView titulo = (TextView)findViewById(R.id.tituloCostureros_txt);
        titulo.setTypeface(tituloF);
        TextView nombreCosturero = (TextView)findViewById(R.id.nombreCosturero_inp);
        nombreCosturero.setTypeface(cuerpoF);
        TextView municipioCosturero = (TextView)findViewById(R.id.municipioCosturero_inp);
        municipioCosturero.setTypeface(cuerpoF  );
        TextView lugarCosturero = (TextView)findViewById(R.id.lugarCosturero);
        lugarCosturero.setTypeface(cuerpoF);
        TextView historiaCosturero = (TextView)findViewById(R.id.historiaCosturero_inp);
        historiaCosturero.setTypeface(cuerpoF);
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
    public void agregarFotoCosturero(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CostureroApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("CostureroApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Insertar foto en la base de datos
                // Image captured and saved to fileUri specified in the Intent
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                //esto hace que la imagen se ve en la galería
                Uri contentUri = fileUri; // Uri.fromFile(f);
                /*Toast path= Toast.makeText(context,"Path: "+contentUri.getPath(),Toast.LENGTH_LONG);
                path.show();*/
                //intent.putExtra("URI_FOTO",contentUri.toString());
                pathCost = contentUri.getPath();
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                setPic();
                //Toast.makeText(this, "Imagen guadada exitosamente", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, "Cancelación" +
                        "la foto", Toast.LENGTH_LONG).show();
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }
    public void setPic() {
        ImageView imagenP=(ImageView) findViewById(R.id.ver_foto_costurero);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathCost, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determine how much to scale down the ima/*ge
        int scaleFactor = Math.min(photoW / 1600, photoH / 800);
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        // Set item views based on the data model
        Bitmap bitmap = BitmapFactory.decodeFile(pathCost, bmOptions);
        imagenP.setImageBitmap(bitmap);
        imagenP.setVisibility(View.VISIBLE);
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
                long insersionCosturero=dbCosturero.insertCosturero(nombre.getText().toString(),insersion,historia.getText().toString(), pathCost);
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
