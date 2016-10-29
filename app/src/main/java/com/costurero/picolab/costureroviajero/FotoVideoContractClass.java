package com.costurero.picolab.costureroviajero;

import android.provider.BaseColumns;

/**
 * Created by PiCoLab on 2016-10-07.
 */

public class FotoVideoContractClass {
    public static abstract class FotoVideoEntry implements BaseColumns {
        public static final String TABLE_NAME ="TBL_FotoVideo";
        public static final String _ID = "MUL_id";
        public static final String path="MUL_path";
        public static final String etiquetas="MUL_etPrimarias";
        public static final String etiquetasS="MUL_etPSecundarias";
        public static final String encuentro="ENC_id";
        public static final String tipo="MUL_tipo";
        public static final String sincronizado="MUL_sincronizado";
    }
}
