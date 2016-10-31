package com.costurero.picolab.costureroviajero;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

import static android.R.drawable.ic_media_play;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by gapiedrahita on 12/04/2016.
 */
// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class FotoVideoAdapter extends RecyclerView.Adapter<FotoVideoAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<FotoVideo> mFotoVideo;
    private Context contexto;
    private View generalView;

    public FotoVideoAdapter(Context context, List<FotoVideo> foto) {
        mFotoVideo = foto;
        inflater = LayoutInflater.from(context);
        contexto=context;
    }

    @Override
    public FotoVideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.println(Log.INFO,"Info", "Pos2");
        // Inflate the custom layout
        generalView = inflater.inflate(R.layout.container_galeriafotos, parent, false);
        ViewHolder viewHolder = new ViewHolder(generalView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(FotoVideoAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        FotoVideo fotoV = mFotoVideo.get(position);
        ImageView fotoI = viewHolder.imagenIzquierda;
        VideoView videoI = viewHolder.videoIzquierdo;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fotoV.getPath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the ima/*ge
        int scaleFactor = Math.min(photoW / 800, photoH / 400);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        // Set item views based on the data model
        if(fotoV.getTipo()==0) {
            Bitmap bitmap = BitmapFactory.decodeFile(fotoV.getPath(), bmOptions);
            videoI.setVisibility(View.INVISIBLE);
            fotoI.setVisibility(View.VISIBLE);
            fotoI.setImageBitmap(bitmap);
            viewHolder.playIzquierdo.setVisibility(View.INVISIBLE);
        }
        else {
            Bitmap bitmapV = ThumbnailUtils.createVideoThumbnail(fotoV.getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
            BitmapDrawable bitmapD=new BitmapDrawable(bitmapV);
            videoI.setBackground(bitmapD);
            videoI.setVideoPath(fotoV.getPath());
            videoI.setVisibility(View.VISIBLE);
            fotoI.setVisibility(View.INVISIBLE);
            viewHolder.playIzquierdo.setImageResource(R.drawable.play_ok);
            viewHolder.playIzquierdo.setVisibility(View.VISIBLE);
        }
        TextView ppalI = viewHolder.ppalIzquierda;
        TextView secI = viewHolder.secIzquierda;
        ppalI.setText(fotoV.getEtiquetaP());
        secI.setText(fotoV.getEtiquetaS());
        Typeface cuerpoF = Typeface.createFromAsset(contexto.getAssets(), "century-expanded-regular.ttf");
        ppalI.setTypeface(cuerpoF);
        secI.setTypeface(cuerpoF);
        ImageView fotoD = viewHolder.imagenDerecha;
        VideoView videoD = viewHolder.videoDerecho;
        if (!fotoV.getPathD().equals("NULL")) {
            BitmapFactory.Options bmOptionsD = new BitmapFactory.Options();
            bmOptionsD.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fotoV.getPathD(), bmOptions);
            int photoWD = bmOptionsD.outWidth;
            int photoHD = bmOptionsD.outHeight;

            // Determine how much to scale down the ima/*ge
            int scaleFactorD = Math.min(photoWD / 800, photoHD / 400);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptionsD.inJustDecodeBounds = false;
            bmOptionsD.inSampleSize = scaleFactor;
            bmOptionsD.inPurgeable = true;
            bmOptionsD = new BitmapFactory.Options();
            bmOptionsD.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fotoV.getPathD(), bmOptions);

            if(fotoV.getTipoD()==0) {
                Bitmap bitmapD = BitmapFactory.decodeFile(fotoV.getPathD(), bmOptions);
                videoD.setVisibility(View.INVISIBLE);
                fotoD.setVisibility(View.VISIBLE);
                viewHolder.playDerecho.setVisibility(View.INVISIBLE);
                fotoD.setImageBitmap(bitmapD);
            }
            else {
                Bitmap bitmapVD = ThumbnailUtils.createVideoThumbnail(fotoV.getPathD(), MediaStore.Images.Thumbnails.MINI_KIND);
                BitmapDrawable bitmapDD=new BitmapDrawable(bitmapVD);
                videoD.setBackground(bitmapDD);
                videoD.setVideoPath(fotoV.getPathD());
                videoD.setVisibility(View.VISIBLE);
                fotoD.setVisibility(View.INVISIBLE);
                viewHolder.playDerecho.setImageResource(R.drawable.play_ok);
                viewHolder.playDerecho.setVisibility(View.VISIBLE);
            }
            TextView ppalD = viewHolder.ppalDerecha;
            TextView secD = viewHolder.secDerecha;
            ppalD.setText(fotoV.getEtiquetaPD());
            secD.setText(fotoV.getEtiquetaSD());
            ppalD.setTypeface(cuerpoF);
            secD.setTypeface(cuerpoF);
        }
        else{
            fotoD.setVisibility(View.INVISIBLE);
            videoD.setVisibility(View.INVISIBLE);
            TextView ppalD = viewHolder.ppalDerecha;
            ppalD.setVisibility(View.INVISIBLE);
            TextView secD = viewHolder.secDerecha;
            secD.setVisibility(View.INVISIBLE);

        }
    }
    @Override
    public int getItemCount() {
        return mFotoVideo.size();
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        /*Izquierda*/
        public ImageView imagenIzquierda;
        public VideoView videoIzquierdo;
        public ImageView playIzquierdo;
        public TextView ppalIzquierda;
        public TextView secIzquierda;

        /*Derecha*/
        public ImageView imagenDerecha;
        public VideoView videoDerecho;
        public ImageView playDerecho;
        public TextView ppalDerecha;
        public TextView secDerecha;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            imagenIzquierda =(ImageView) itemView.findViewById(R.id.imagenIzquierda_img);
            videoIzquierdo = (VideoView) itemView.findViewById(R.id.video_vidL);
            playIzquierdo = (ImageView) itemView.findViewById(R.id.pIzquierdo_img);
            ppalIzquierda=(TextView) itemView.findViewById(R.id.principalIzquierda_txt);
            secIzquierda=(TextView) itemView.findViewById(R.id.secundariaIzquierda_txt);
            // video finish listener
            videoIzquierdo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // not playVideo
                    // playVideo();
                    Toast finish= Toast.makeText(contexto,"Termino izquierdo",Toast.LENGTH_SHORT);
                    finish.show();
                    playIzquierdo.setVisibility(View.VISIBLE);
                }
            });

            imagenDerecha=(ImageView) itemView.findViewById(R.id.imagenDerecha_img);
            videoDerecho =(VideoView) itemView.findViewById(R.id.video_vidR);
            playDerecho =(ImageView) itemView.findViewById(R.id.pDerecho_img);
            ppalDerecha=(TextView) itemView.findViewById(R.id.principalDerecha_txt);
            secDerecha=(TextView) itemView.findViewById(R.id.secundariaDerecha_txt);
            videoDerecho.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // not playVideo
                    // playVideo();
                    Toast finish= Toast.makeText(contexto,"Termino derecho",Toast.LENGTH_SHORT);
                    finish.show();
                    playDerecho.setVisibility(View.VISIBLE);
                }
            });
            // Attach a click listener to the entire row view
            playIzquierdo.setOnClickListener(this);
            playDerecho.setOnClickListener(this);
            //itemView.setOnClickListener(this);
        }
        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            FotoVideo fotoVideo= mFotoVideo.get(position);
            if(view == playIzquierdo && fotoVideo.getTipo()==1) {//Si es video y se dio clic al play izquierdo
                videoIzquierdo.setBackground(null);
                videoIzquierdo.start();
                playIzquierdo.setVisibility(View.INVISIBLE);
            }
            if(view == playDerecho && fotoVideo.getTipoD()==1) {//Si es video y se dio clic al play derecho
                videoDerecho.setBackground(null);
                videoDerecho.start();
                playDerecho.setVisibility(View.INVISIBLE);
            }
            /*FotoVideo fotoVideo= mFotoVideo.get(position);
            Intent intent = new Intent(contexto,EncuentrosActivity.class);
            intent.putExtra("ID_COSTURERO", ""+position);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexto.startActivity(intent);*/
        }

    }
}