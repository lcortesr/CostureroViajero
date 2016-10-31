package com.costurero.picolab.costureroviajero;


import android.provider.BaseColumns;
/**
 * Created by PiCoLab on 2016-10-07.
 */

public final class CostureroContractClass {
    public static abstract class CostureroEntry implements BaseColumns{
        public static final String TABLE_NAME ="TBL_Costurero";
        public static final String _ID = "COS_id";
        public static final String localizacion="LOC_id";
        public static final String nombre="COS_nombre";
        public static final String path="COS_path";
        public static final String historia="COS_historia";
        public static final String sincronizado="COS_sincronizado";
    }
}