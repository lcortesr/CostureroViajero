package com.costurero.picolab.costureroviajero;

import android.provider.BaseColumns;

/**
 * Created by PiCoLab on 2016-10-07.
 */

public class ParticipanteContractClass {
    public static abstract class ParticipanteEntry implements BaseColumns {
        public static final String TABLE_NAME ="TBL_Participante";
        public static final String id = "PAR_id";
        public static final String path="PAR_path";
        public static final String nombre="PAR_nombre";
        public static final String historia="PAR_historia";
        public static final String costurero="COS_id";
        public static final String sincronizado="PAR_sincronizado";
    }
}
