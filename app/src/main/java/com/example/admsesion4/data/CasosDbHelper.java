package com.example.admsesion4.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CasosDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Casos.db";

    public CasosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Comandos SQL
        sqLiteDatabase.execSQL("CREATE TABLE " + CasoContracts.CasoEntry.TABLE_NAME + " ("
                + CasoContracts.CasoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CasoContracts.CasoEntry.CODE + " TEXT NOT NULL,"
                + CasoContracts.CasoEntry.SDATE + " TEXT NOT NULL,"
                + CasoContracts.CasoEntry.FEVER + " INTEGER,"
                + CasoContracts.CasoEntry.COUGH + " INTEGER,"
                + CasoContracts.CasoEntry.SHORTNESS + " INTEGER,"
                + CasoContracts.CasoEntry.FATIGUE + " INTEGER,"
                + CasoContracts.CasoEntry.MUSCLEBODYACHES + " INTEGER,"
                + CasoContracts.CasoEntry.HEADACHE + " INTEGER,"
                + CasoContracts.CasoEntry.LOSSOFTASEORSMELL + " INTEGER,"
                + CasoContracts.CasoEntry.SORETHROAT + " INTEGER,"
                + CasoContracts.CasoEntry.CONGESTION + " INTEGER,"
                + CasoContracts.CasoEntry.NAUSEA + " INTEGER,"
                + CasoContracts.CasoEntry.DIARRHEA + " INTEGER,"
                + CasoContracts.CasoEntry.CLOSECONTACT + " INTEGER,"
                + CasoContracts.CasoEntry.MUNICIPIO + " TEXT NOT NULL,"
                + "UNIQUE (" + CasoContracts.CasoEntry.CODE + "))");


        // Contenedor de valores
        ContentValues values = new ContentValues();

        // Pares clave-valor
        values.put(CasoContracts.CasoEntry.CODE, "L-001");
        values.put(CasoContracts.CasoEntry.SDATE, "20/10/1997");
        values.put(CasoContracts.CasoEntry.FEVER, 1);
        values.put(CasoContracts.CasoEntry.MUNICIPIO, "Ademuz");

        // Insertar...
        sqLiteDatabase.insert(CasoContracts.CasoEntry.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //No hay operaciones
        sqLiteDatabase.execSQL("DROP TABLE " +  CasoContracts.CasoEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long saveCaso(Caso caso) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                CasoContracts.CasoEntry.TABLE_NAME,
                null,
                caso.toContentValues());
    }

    public void InsertReport(Caso caso){
        saveCaso(caso);
    }

    public Boolean UpdateReport(Caso caso){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("select * from " + CasoContracts.CasoEntry.TABLE_NAME + " WHERE " + CasoContracts.CasoEntry.CODE + " = " + "'" + caso.getId() + "'" , null);;

        if(cursor.getCount() > 0){
            long result = DB.update(CasoContracts.CasoEntry.TABLE_NAME, caso.toContentValues(), "code=?", new String[]{caso.getId()});
            if(result==-1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }

    }

    public Boolean DeleteReport(String codee){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("select * from " + CasoContracts.CasoEntry.TABLE_NAME + " WHERE " + CasoContracts.CasoEntry.CODE + " = " + "'" + codee + "'" , null);;

        if(cursor.getCount() > 0){
            long result = DB.delete(CasoContracts.CasoEntry.TABLE_NAME, "code=?", new String[]{codee});
            if(result==-1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }
    }

    public Cursor FindReportsByMunicipality(String municipio){
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("select * from " + CasoContracts.CasoEntry.TABLE_NAME + " WHERE " + CasoContracts.CasoEntry.MUNICIPIO + " = " + "'" + municipio + "'" , null);

        return cursor;
    }

    public Cursor FindReportByNumeroReporte(String numeroReporte) {
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("select * from " + CasoContracts.CasoEntry.TABLE_NAME + " WHERE " + CasoContracts.CasoEntry.CODE + " = " + "'" + numeroReporte + "'" , null);

        return cursor;
    }

}
