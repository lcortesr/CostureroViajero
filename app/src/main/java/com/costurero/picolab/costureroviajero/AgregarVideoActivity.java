package com.costurero.picolab.costureroviajero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.media.session.MediaController;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

import static com.costurero.picolab.costureroviajero.R.string.video;

public class AgregarVideoActivity extends AppCompatActivity {
    private String pathVideo;
    private String idEnc;
    CostDbHelper dbCosturero;
    private String nombreCosturero;
    private String fechaEncuentro;
    VideoView videoAgregado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_video);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Write();
        if(bundle!=null) {
            pathVideo= bundle.getString("URI_VIDEO");
            idEnc= bundle.getString("ID_ENCUENTRO");
            nombreCosturero=bundle.getString("NOMBRE_COSTURERO");
            fechaEncuentro=bundle.getString("FECHA_ENCUENTRO");
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        videoAgregado=(VideoView) findViewById(R.id.video_vid);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathVideo, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/800, photoH/350);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(pathVideo, MediaStore.Images.Thumbnails.MINI_KIND);
        BitmapDrawable bitmapD=new BitmapDrawable(bitmap);
        videoAgregado.setBackground(bitmapD);
        videoAgregado.setVideoPath(pathVideo);
    }
    public void playVideo(View view){
        videoAgregado.setBackground(null);
        videoAgregado.start();
    }

    public void guardarVideo(View view){
        RadioGroup radioGroup=(RadioGroup) findViewById(R.id.etiquetasPrincipales_rg);
        int RB1_ID = 0;//first radio button id
        int RB2_ID = 1;//second radio button id
        int RB3_ID = 2;//third radio button id
        int RB4_ID = 3;//first radio button id
        RadioButton rb1=(RadioButton) findViewById(R.id.etiquetaFija1_tog);
        RadioButton rb2=(RadioButton) findViewById(R.id.etiquetaFija2_tog);
        RadioButton rb3=(RadioButton) findViewById(R.id.etiquetaFija3_tog);
        RadioButton rb4=(RadioButton) findViewById(R.id.etiquetaFija4_tog);
        int s=0;
        if(rb1.isChecked())
            s=0;
        if(rb2.isChecked())
            s=1;
        if(rb3.isChecked())
            s=2;
        if(rb4.isChecked())
            s=3;
        TextView etS1=(TextView) findViewById(R.id.historiaVideo_inp);

        String[] etiquetasPrincipales=new String[4];
        etiquetasPrincipales[0]=getString(R.string.etiqueta1);
        etiquetasPrincipales[1]=getString(R.string.etiqueta2);
        etiquetasPrincipales[2]=getString(R.string.etiqueta3);
        etiquetasPrincipales[3]=getString(R.string.etiqueta4);
        String secundarias=etS1.getText().toString();
        long enc=Long.parseLong(idEnc);
        long insersion=dbCosturero.insertFotoVideo(enc,etiquetasPrincipales[s],secundarias,pathVideo,1,0);
        if(insersion>=0){
            Intent intent = new Intent(this, EncuentroActivity.class);
            intent.putExtra("ID_ENCUENTRO",idEnc);
            intent.putExtra("NOMBRE_COSTURERO",nombreCosturero);
            intent.putExtra("FECHA_ENCUENTRO",fechaEncuentro);
            startActivity(intent);
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
