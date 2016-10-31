package com.costurero.picolab.costureroviajero;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

//Servicios Web

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Typeface tituloF = Typeface.createFromAsset(getAssets(), "century-expanded-bold-bt.ttf");
        TextView galeria = (TextView)findViewById(R.id.galeria_text);
        galeria.setTypeface(tituloF);
        TextView costureros = (TextView)findViewById(R.id.costureros_text);
        costureros.setTypeface(tituloF);
        TextView proyecto = (TextView)findViewById(R.id.proyecto_text);
        proyecto.setTypeface(tituloF);
        /*Typeface face= Typeface.createFromAsset(getAssets(),"fonts/crosssew.ttf");
        TextView titulo=(TextView) findViewById(R.id.tituloMenu_txt);
        titulo.setTypeface(face);*/
        /*contacts = Contact.createContactsList(4);
        adapter = new ContactsAdapter(contacts);
        adapter.notifyDataSetChanged();
        rvContacts = (RecyclerView) findViewById(R.id.rexyclerCosturero_lst);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));*/
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
    public void sincronizar(View view){

        //Subir un costurero
        try {
            // WebServer Request URL for costureros - Sólo cambia la URL y los parámetros
            String serverURLLoc = "http://costureroviajero.org/api/costureros/";
            CostDbHelper dbCosturero;
            dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
            dbCosturero.Read();
            Cursor lectura=dbCosturero.readCosturero();
            while(lectura.moveToNext()){
                int idC=lectura.getInt(lectura.getColumnIndex(CostureroContractClass.CostureroEntry._ID));
                String name = lectura.getString(lectura.getColumnIndex(CostureroContractClass.CostureroEntry.nombre));
                String idLoc = lectura.getString(lectura.getColumnIndex(CostureroContractClass.CostureroEntry.localizacion));
                String hist = lectura.getString(lectura.getColumnIndex(CostureroContractClass.CostureroEntry.historia));
                String pathC = lectura.getString(lectura.getColumnIndex(CostureroContractClass.CostureroEntry.path));

                JSONObject parametros = new JSONObject();
                parametros.put("id_costurero", 3);
                parametros.put("nombre", "Test Android");
                parametros.put("historia", "Historia Test Android");
                //Archivo

                parametros.put("path", "http://www.centrodememoriahistorica.gov.co/media/k2/items/cache/2ee80c33d67e0ebffed64653bf9b6503_XL.jpg");
                parametros.put("localizacion", 1);
                new PostAsync().execute(serverURLLoc,"root", "admin2016", parametros.toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GetAsync extends AsyncTask<String, String, JSONArray> {

        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MenuActivity.this);
            pDialog.setMessage("Sincronizando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                Log.d("request", "starting");

                JSONArray json = jsonParser.makeHttpRequest(args[0], "GET", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONArray json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    }
    class PostAsync extends AsyncTask<String, String, JSONArray> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MenuActivity.this);
            pDialog.setMessage("Sincronizando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                JSONObject parametros=new JSONObject(args[2]);
                Log.d("request", "starting");
                JSONArray json = jsonParser.makeHttpRequest(args[0], "POST", parametros);
                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(JSONArray json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
                Toast.makeText(MenuActivity.this, json.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
