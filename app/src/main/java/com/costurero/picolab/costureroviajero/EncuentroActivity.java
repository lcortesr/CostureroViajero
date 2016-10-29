package com.costurero.picolab.costureroviajero;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class EncuentroActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private Uri fileUri;
    private String nombreCosturero;
    private String idEncuentroP;
    private String fechaEncuentro;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuentro);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            nombreCosturero = bundle.getString("NOMBRE_COSTURERO");
            idEncuentroP = "" + (Integer.parseInt(bundle.getString("ID_ENCUENTRO")));
            context= getApplicationContext();
            fechaEncuentro = bundle.getString("FECHA_ENCUENTRO");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView titulo=(TextView) findViewById(R.id.tituloEncuentro_txt);
        titulo.setText("Encuentro - "+nombreCosturero+" - el "+fechaEncuentro);
        CostDbHelper dbCosturero;
        RecyclerView rvFotos;
        ArrayList<FotoVideo> listaFotos=new ArrayList<FotoVideo>();
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Read();
        Cursor lectura=dbCosturero.readFotoVideo();
        int l=0;
        while(lectura.moveToNext()){
            String idA=""+(lectura.getInt(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.encuentro)));
            /*Toast toast=Toast.makeText(context,"Id foto: "+idA, Toast.LENGTH_LONG);
            toast.show();*/
            idEncuentroP = "" + Integer.parseInt(idEncuentroP);
            if(idA.equals(idEncuentroP)){
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
    public void abrirGaleria(View view){
        Intent intent = new Intent(this, GaleriaActivity.class);
        startActivity(intent);
    }
    public void abrirInfoP(View view){
        Intent intent = new Intent(this, ProyectoActivity.class);
        startActivity(intent);
    }
    public void toma_foto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }

    public void toma_video(View view){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);

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
                Intent intent = new Intent(this, AgregarFotoActivity.class);
                /*Toast path= Toast.makeText(context,"Path: "+contentUri.getPath(),Toast.LENGTH_LONG);
                path.show();*/
                //intent.putExtra("URI_FOTO",contentUri.toString());
                intent.putExtra("URI_FOTO",contentUri.getPath());
                intent.putExtra("ID_ENCUENTRO",idEncuentroP);
                intent.putExtra("NOMBRE_COSTURERO",nombreCosturero);
                intent.putExtra("FECHA_ENCUENTRO",fechaEncuentro);
                startActivity(intent);

                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                //setPic();

                //Toast.makeText(this, "Imagen guadada exitosamente", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, "Cancelación" +
                        "la foto", Toast.LENGTH_LONG).show();
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Insertar video en la base de datos


                // Video captured and saved to fileUri specified in the Intent
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//esto hace que la imagen se ve en la galería
                Uri contentUri = fileUri; // Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                Intent intentV = new Intent(this, AgregarVideoActivity.class);
                /*Toast path= Toast.makeText(context,"Path: "+contentUri.getPath(),Toast.LENGTH_LONG);
                path.show();*/
                //intent.putExtra("URI_FOTO",contentUri.toString());
                intentV.putExtra("URI_VIDEO",contentUri.getPath());
                intentV.putExtra("ID_ENCUENTRO",idEncuentroP);
                intentV.putExtra("NOMBRE_COSTURERO",nombreCosturero);
                intentV.putExtra("FECHA_ENCUENTRO",fechaEncuentro);
                startActivity(intentV);
                //setPic();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
            } else {
                // Video capture failed, advise user
            }
        }
    }

    /*private void setPic() {
        // Get the dimensions of the View
        String filePath = fileUri.getPath();
        ImageView mImageView = (ImageView) findViewById(R.id.mimageView);
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }*/

}