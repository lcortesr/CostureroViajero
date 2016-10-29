package com.costurero.picolab.costureroviajero;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.costurero.picolab.costureroviajero.CosturerosActivity;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    /*ArrayList<Contact> contacts;
    ContactsAdapter adapter;
    RecyclerView rvContacts;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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
}
