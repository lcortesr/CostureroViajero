package com.costurero.picolab.costureroviajero;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EncuentroActivity extends AppCompatActivity{

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private Uri fileUri;
    private String nombreCosturero;
    private String idEncuentroP;
    private String fechaEncuentro;

    RecyclerView rvFotos;
    FotoVideoAdapter adapter;
    ArrayList<FotoVideo> listaFotos=new ArrayList<FotoVideo>();
    ArrayList<FotoVideo> listaFotosAux=new ArrayList<FotoVideo>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuentro);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        listaFotos.clear();
        if(bundle!=null) {
            nombreCosturero = bundle.getString("NOMBRE_COSTURERO");
            idEncuentroP = "" + (Integer.parseInt(bundle.getString("ID_ENCUENTRO")));
            context= getApplicationContext();
            fechaEncuentro = bundle.getString("FECHA_ENCUENTRO");
        }
        SearchView buscador=(SearchView) findViewById(R.id.buscadorEtiquetas_srch);

        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast toast=Toast.makeText(context,"Entra a buscar", Toast.LENGTH_LONG);
                //toast.show();
                listaFotos.clear();
                int id=0;
                String idAI="NA", pathIA="NA", pathDA="NA", etiquetasPIA="NA", etiquetasPDA="NA", etiquetasSIA="NA", etiquetasSDA="NA", idAD="NA";
                int tIA=-1, tDA=-1;
                for(int i=0;i<listaFotosAux.size();i++){//Por cada elemento de la lista
                    if(!listaFotosAux.get(i).getEtiquetaP().contains(query)&& !listaFotosAux.get(i).getEtiquetaS().contains(query)){
                        if(!listaFotosAux.get(i).getEtiquetaPD().contains(query)&& !listaFotosAux.get(i).getEtiquetaSD().contains(query)) {
                            //Si ninguna de las dos tiene la palabra clave, ni Izq, ni Der
                        }
                        else{
                            //Si la izquierda no tiene la palabra clave pero la derecha sí
                            if(id==0){
                                idAI=listaFotosAux.get(i).getId();
                                pathIA=listaFotosAux.get(i).getPathD();
                                etiquetasPIA=listaFotosAux.get(i).getEtiquetaPD();
                                etiquetasSIA=listaFotosAux.get(i).getEtiquetaSD();
                                tIA=listaFotosAux.get(i).getTipoD();
                                id=1;
                                if(i==listaFotosAux.size()-1) {
                                    listaFotos.add(new FotoVideo(idAI,pathIA,etiquetasPIA, etiquetasSIA,tIA,"0","NULL"," "," ",0));
                                    tIA=-1;
                                }
                            }
                            else{
                                idAD=listaFotosAux.get(i).getIdD();
                                pathDA=listaFotosAux.get(i).getPathD();
                                etiquetasPDA=listaFotosAux.get(i).getEtiquetaPD();
                                etiquetasSDA=listaFotosAux.get(i).getEtiquetaSD();
                                tDA=listaFotosAux.get(i).getTipoD();
                                id=0;
                                listaFotos.add(new FotoVideo(idAI,pathIA,etiquetasPIA,etiquetasSIA,tIA,idAD,pathDA,etiquetasPDA,etiquetasSDA,tDA));
                                tIA=-1;
                            }
                        }
                    }
                    else{
                        if(!listaFotosAux.get(i).getEtiquetaPD().contains(query)&& !listaFotosAux.get(i).getEtiquetaSD().contains(query)) {
                            //Si la derecha no tiene la palabra clave pero la izquierda sí
                            if(id==0){
                                idAI=listaFotosAux.get(i).getId();
                                pathIA=listaFotosAux.get(i).getPath();
                                etiquetasPIA=listaFotosAux.get(i).getEtiquetaP();
                                etiquetasSIA=listaFotosAux.get(i).getEtiquetaS();
                                tIA=listaFotosAux.get(i).getTipo();
                                id=1;
                                if(i==listaFotosAux.size()-1) {
                                    listaFotos.add(new FotoVideo(idAI,pathIA,etiquetasPIA,etiquetasSIA,tIA,"0","NULL"," "," ",0));
                                    tIA=-1;
                                }
                            }
                            else{
                                idAD=listaFotosAux.get(i).getId();
                                pathDA=listaFotosAux.get(i).getPath();
                                etiquetasPDA=listaFotosAux.get(i).getEtiquetaP();
                                etiquetasSDA=listaFotosAux.get(i).getEtiquetaS();
                                tDA=listaFotosAux.get(i).getTipo();
                                id=0;
                                listaFotos.add(new FotoVideo(idAI,pathIA,etiquetasPIA,etiquetasSIA,tIA,idAD,pathDA,etiquetasPDA,etiquetasSDA,tDA));
                                tIA=-1;
                            }
                        }
                        else{
                            //Si ambas, izquierda y derecha, tienen la palabra clave
                            if(id==0){
                                idAI=listaFotosAux.get(i).getId();
                                pathIA=listaFotosAux.get(i).getPath();
                                etiquetasPIA=listaFotosAux.get(i).getEtiquetaP();
                                etiquetasSIA=listaFotosAux.get(i).getEtiquetaS();
                                tIA=listaFotosAux.get(i).getTipo();
                                idAD=listaFotosAux.get(i).getIdD();
                                pathDA=listaFotosAux.get(i).getPathD();
                                etiquetasPDA=listaFotosAux.get(i).getEtiquetaPD();
                                etiquetasSDA=listaFotosAux.get(i).getEtiquetaSD();
                                tDA=listaFotosAux.get(i).getTipoD();
                                listaFotos.add(new FotoVideo(idAI,pathIA,etiquetasPIA,etiquetasSIA,tIA,idAD,pathDA,etiquetasPDA,etiquetasSDA,tDA));
                                tIA=-1;
                            }
                            else{
                                idAD=listaFotosAux.get(i).getIdD();
                                pathDA=listaFotosAux.get(i).getPath();
                                etiquetasPDA=listaFotosAux.get(i).getEtiquetaP();
                                etiquetasSDA=listaFotosAux.get(i).getEtiquetaS();
                                tDA=listaFotosAux.get(i).getTipo();
                                listaFotos.add(new FotoVideo(idAI,pathIA,etiquetasPIA,etiquetasSIA,tIA,idAD,pathDA,etiquetasPDA,etiquetasSDA,tDA));
                                pathIA=listaFotosAux.get(i).getPathD();
                                etiquetasPIA=listaFotosAux.get(i).getEtiquetaPD();
                                etiquetasSIA=listaFotosAux.get(i).getEtiquetaSD();
                                tIA=listaFotosAux.get(i).getTipoD();
                            }
                        }
                    }
                }
                if(tIA!=-1) {
                    listaFotos.add(new FotoVideo(idAI,pathIA,etiquetasPIA,etiquetasSIA,tIA,"0","NULL"," "," ",0));
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
        TextView titulo = (TextView)findViewById(R.id.tituloEncuentro_txt);
        titulo.setTypeface(tituloF);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listaFotos.clear();
        TextView titulo=(TextView) findViewById(R.id.tituloEncuentro_txt);
        titulo.setText(fechaEncuentro+" - "+nombreCosturero);
        CostDbHelper dbCosturero;
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Read();
        Cursor lectura=dbCosturero.readFotoVideo();
        int l=0;
        String idFot="-1",pathF="NULL",etP="0",etS="0", idFotD, pathFD,etPD,etSD;
        int tipoFV=0,tipoFVD ;
        lectura.moveToLast();
        lectura.moveToNext();
        while(lectura.moveToPrevious()){
            String idA=""+(lectura.getInt(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.encuentro)));
            if(l==0) {
                if(idA.equals(idEncuentroP)) {
                    idFot = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry._ID));
                    pathF = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.path));
                    etP = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetas));
                    etS = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetasS));
                    tipoFV = lectura.getInt(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.tipo));
                    l = 1;
                    if(!lectura.moveToPrevious()) {
                        listaFotos.add(new FotoVideo(idFot,pathF,etP,etS,tipoFV,"NULL","NULL"," "," ",0));
                    }
                    lectura.moveToNext();
                }
            }
            else{
                if (idA.equals(idEncuentroP)){
                    idFotD=lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry._ID));
                    pathFD = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.path));
                    etPD = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetas));
                    etSD = lectura.getString(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.etiquetasS));
                    tipoFVD = lectura.getInt(lectura.getColumnIndex(FotoVideoContractClass.FotoVideoEntry.tipo));
                    listaFotos.add(new FotoVideo(idFot,pathF, etP, etS, tipoFV, idFotD,pathFD, etPD, etSD, tipoFVD));
                    l=0;
                }
            }
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CosturerosActivity.class);
        startActivity(intent);
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
                listaFotos.clear();
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
                listaFotos.clear();
            } else {
                // Video capture failed, advise user
            }
        }
    }



}