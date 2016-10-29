package com.costurero.picolab.costureroviajero;

import android.provider.BaseColumns;

/**
 * Created by PiCoLab on 2016-10-07.
 */

public class EncuentroContractClass {
    public static abstract class EncuentroEntry implements BaseColumns {
        public static final String TABLE_NAME ="TBL_Encuentro";
        public static final String _ID = "ENC_id";
        public static final String fecha="ENC_fecha";
        public static final String costurero="COS_id";
        public static final String bitacora="ENC_bitacora";
        public static final String sincronizado="ENC_sincronizado";
    }
}