package com.costurero.picolab.costureroviajero;

import android.provider.BaseColumns;
/**
 * Created by PiCoLab on 2016-10-07.
 */

public final class LocalizacionContractClass {
    public static abstract class LocalizacionEntry implements BaseColumns{
        public static final String TABLE_NAME ="TBL_localizacion";
        public static final String _ID = "LOC_id";
        public static final String latitud="LOC_latitud";
        public static final String longitud="LOC_longitud";
        public static final String municipio="LOC_municipio";
        public static final String nombre="LOC_nombre";
        public static final String sincronizado="LOC_sincronizado";
    }
}
