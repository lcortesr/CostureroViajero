package com.costurero.picolab.costureroviajero;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by gapiedrahita on 12/04/2016.
 */
public class Contact {
    private String mName;
    private boolean mOnline;
    private float mLat;
    private float mLong;


    public Contact(String name, boolean online, float lat, float Lon) {
        mName = name;
        mOnline = online;
        mLat = lat;
        mLong = Lon;
    }

    public String getName() {
        return mName;
    }

    public float getmLat() {
        return mLat;
    }

    public float getmLong() {
        return mLong;
    }

    public boolean isOnline() {
        return mOnline;
    }

    private static int lastContactId = 0;

    public static ArrayList<Contact> createContactsList(int numContacts) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

//        SQLiteDatabase mydatabase = SQLiteDatabase.openOrCreateDatabase("Contactos", Context.MODE_PRIVATE,null);

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Contact("Ubicación " + i, i <= numContacts / 2, (float)4.636892+i,(float)-74.055462-i));
        }

        return contacts;
    }

}
