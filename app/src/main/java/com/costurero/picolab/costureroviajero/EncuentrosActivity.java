package com.costurero.picolab.costureroviajero;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EncuentrosActivity extends AppCompatActivity {
    private String message;
    private String tituloCosturero;
    private TextView textoTitulo;
    private String municipioCosturero;
    private String lugarCosturero;
    private String historiaCosturero;
    ArrayList<Encuentro> listaEncuentros=new ArrayList<Encuentro>();
    EncuentrosAdapter adapter;
    RecyclerView rvEncuentros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuentros);
        listaEncuentros.clear();
        Typeface tituloF = Typeface.createFromAsset(getAssets(), "century-expanded-bold-bt.ttf");
        Typeface cuerpoF = Typeface.createFromAsset(getAssets(), "century-expanded-regular.ttf");
        Typeface cursivaF = Typeface.createFromAsset(getAssets(), "century-expanded-italic.ttf");

        TextView titulo = (TextView)findViewById(R.id.tituloEncuentros_txt);
        titulo.setTypeface(tituloF);
        TextView municipio = (TextView)findViewById(R.id.titMunicipio_txt);
        municipio.setTypeface(cuerpoF);
        TextView municipioTxt = (TextView) findViewById(R.id.nombreMun_txt);
        municipioTxt.setTypeface(cuerpoF);
        TextView lugar = (TextView) findViewById(R.id.titLugar_txt);
        lugar.setTypeface(cuerpoF);
        TextView lugarTxt = (TextView) findViewById(R.id.nombreLug_txt);
        lugarTxt.setTypeface(cuerpoF);
        TextView historia = (TextView) findViewById(R.id.titHistoria_txt);
        historia.setTypeface(cuerpoF);
        TextView historiaTxt = (TextView) findViewById(R.id.historiaCol_txt);
        historiaTxt.setTypeface(cuerpoF);
        TextView enc = (TextView) findViewById(R.id.encuentros_txt);
        enc.setTypeface(tituloF);
        TextView par = (TextView) findViewById(R.id.participantes_txt);
        par.setTypeface(tituloF);
        TextView tipEnc = (TextView) findViewById(R.id.tipEncuentro_txt);
        tipEnc.setTypeface(cursivaF);
        TextView tipPar = (TextView) findViewById(R.id.tipParticipante_txt);
        tipPar.setTypeface(cursivaF);
    }
    @Override
    protected void onStart() {
        super.onStart();
        CostDbHelper dbCosturero;
        RecyclerView rvParticipantes;
        listaEncuentros.clear();
        ArrayList<Participante> listaParticipantes =new ArrayList<Participante>();
        listaParticipantes.clear();
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Read();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            message=""+ Integer.parseInt(bundle.getString("ID_COSTURERO"));
            municipioCosturero = bundle.getString("MUNICIPIO_COSTURERO");
            lugarCosturero = bundle.getString("LUGAR_COSTURERO");
            historiaCosturero = bundle.getString("HISTORIA_COSTURERO");
            TextView municipio=(TextView) findViewById(R.id.nombreMun_txt);
            municipio.setText(municipioCosturero);
            TextView lugar=(TextView) findViewById(R.id.nombreLug_txt);
            lugar.setText(lugarCosturero);
            TextView historia=(TextView) findViewById(R.id.historiaCol_txt);
            historia.setText(historiaCosturero);
            Cursor lectura=dbCosturero.readCosturero();
            while(lectura.moveToNext()){
                String idA=""+(lectura.getInt(lectura.getColumnIndex(CostureroContractClass.CostureroEntry._ID))-1);
                if(idA.equals(message)){
                    tituloCosturero=lectura.getString(lectura.getColumnIndex(CostureroContractClass.CostureroEntry.nombre));
                    textoTitulo=(TextView) findViewById(R.id.tituloEncuentros_txt);
                    textoTitulo.setText(tituloCosturero);
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
                listaEncuentros.add(new Encuentro(nameC, "Encuentro "+(listaEncuentros.size()+1)+": "+fechaC, idC));
            }
        }
        if(listaEncuentros.size()>0) {
            rvEncuentros = (RecyclerView) findViewById(R.id.recyclerEncuentros_lst);
            adapter = new EncuentrosAdapter(getBaseContext(), listaEncuentros);
            rvEncuentros.setLayoutManager(new LinearLayoutManager(this));
            rvEncuentros.setAdapter(adapter);
        }
        Cursor lecturaParticipante=dbCosturero.readParticipante();
        while(lecturaParticipante.moveToNext()) {
            if(lecturaParticipante.getString(lecturaParticipante.getColumnIndex(ParticipanteContractClass.ParticipanteEntry.costurero)).equals(message)) {
                String nombreParticipante = lecturaParticipante.getString(lecturaParticipante.getColumnIndex(ParticipanteContractClass.ParticipanteEntry.nombre));
                String historiaParticipante = lecturaParticipante.getString(lecturaParticipante.getColumnIndex(ParticipanteContractClass.ParticipanteEntry.historia));
                String pathParticipante = lecturaParticipante.getString(lecturaParticipante.getColumnIndex(ParticipanteContractClass.ParticipanteEntry.path));
                int idP=lecturaParticipante.getInt(lecturaParticipante.getColumnIndex(ParticipanteContractClass.ParticipanteEntry._ID));
                int idCos=lecturaParticipante.getInt(lecturaParticipante.getColumnIndex(ParticipanteContractClass.ParticipanteEntry.costurero));
                listaParticipantes.add(new Participante(nombreParticipante, historiaParticipante,pathParticipante,idP, idCos));
            }
        }
        if(listaParticipantes.size()>0) {
            rvParticipantes = (RecyclerView) findViewById(R.id.recyclerParticipantes_lst);
            ParticipantesAdapter adapterP = new ParticipantesAdapter(getBaseContext(), listaParticipantes);
            rvParticipantes.setLayoutManager(new LinearLayoutManager(this));
            rvParticipantes.setAdapter(adapterP);
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
        CostDbHelper dbCosturero;
        dbCosturero=new CostDbHelper(this.getBaseContext());//Revisar
        dbCosturero.Write();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String fechaEncuentro= year+"-"+month+"-"+day;
        if(listaEncuentros!=null){
            if(listaEncuentros.size()>0){
                boolean flag=true;
                for(int e=0;e<listaEncuentros.size();e++)
                    if(listaEncuentros.get(e).getFecha().contains(fechaEncuentro))
                        flag=false;
                if(flag){
                    long insersion=dbCosturero.insertEncuentro(fechaEncuentro,Long.parseLong(message));
                    if(insersion>=0) {
                        listaEncuentros.add(new Encuentro(tituloCosturero, "Encuentro "+(listaEncuentros.size()+1)+": "+fechaEncuentro, listaEncuentros.size()+1));
                        adapter = new EncuentrosAdapter(getBaseContext(), listaEncuentros);
                        rvEncuentros.setAdapter(adapter);
                    }
                }
            }
            else{
                long insersion=dbCosturero.insertEncuentro(fechaEncuentro,Long.parseLong(message));
                if(insersion>=0) {
                    listaEncuentros.add(new Encuentro(tituloCosturero, "Encuentro "+(listaEncuentros.size()+1)+": "+fechaEncuentro, listaEncuentros.size()+1));
                    rvEncuentros = (RecyclerView) findViewById(R.id.recyclerEncuentros_lst);
                    adapter = new EncuentrosAdapter(getBaseContext(), listaEncuentros);
                    rvEncuentros.setLayoutManager(new LinearLayoutManager(this));
                    rvEncuentros.setAdapter(adapter);
                }
            }
        }
        else{
            listaEncuentros=new ArrayList<Encuentro>();
            long insersion=dbCosturero.insertEncuentro(fechaEncuentro,Long.parseLong(message));
            if(insersion>=0) {
                listaEncuentros.add(new Encuentro(tituloCosturero, "Encuentro "+(listaEncuentros.size()+1)+": "+fechaEncuentro, listaEncuentros.size()+1));
                rvEncuentros = (RecyclerView) findViewById(R.id.recyclerEncuentros_lst);
                adapter = new EncuentrosAdapter(getBaseContext(), listaEncuentros);
                rvEncuentros.setLayoutManager(new LinearLayoutManager(this));
                rvEncuentros.setAdapter(adapter);
            }
        }
    }
    public void agregarNuevoParticipante(View view){
        Intent intent = new Intent(this, AgregarParticipanteActivity.class);
        intent.putExtra("ID_COSTURERO", message);
        intent.putExtra("MUNICIPIO_COSTURERO", ""+municipioCosturero);
        intent.putExtra("LUGAR_COSTURERO", ""+lugarCosturero);
        intent.putExtra("HISTORIA_COSTURERO", ""+historiaCosturero);
        startActivity(intent);
    }
    public void abrirInfo(View view){
        Intent intent = new Intent(this, ProyectoActivity.class);
        startActivity(intent);
    }
}
