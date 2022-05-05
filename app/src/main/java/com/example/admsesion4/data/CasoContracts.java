package com.example.admsesion4.data;

import android.provider.BaseColumns;

public class CasoContracts {


    public static abstract class CasoEntry implements BaseColumns {
        public static final String TABLE_NAME ="caso";

        public static final String CODE = "code";
        public static final String SDATE = "startdate";
        public static final String FEVER = "fever";
        public static final String COUGH = "cough";
        public static final String SHORTNESS = "shortness";
        public static final String FATIGUE = "fatigue";
        public static final String MUSCLEBODYACHES = "musclebodyaches";
        public static final String HEADACHE = "headache";
        public static final String LOSSOFTASEORSMELL = "lossoftasteorsmell";
        public static final String SORETHROAT = "sorethroat";
        public static final String CONGESTION = "congestion";
        public static final String NAUSEA = "nausea";
        public static final String DIARRHEA = "diarrhea";
        public static final String CLOSECONTACT = "closecontact";
        public static final String MUNICIPIO = "municipio";
    }

}
