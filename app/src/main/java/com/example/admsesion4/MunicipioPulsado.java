package com.example.admsesion4;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admsesion4.data.CasoContracts;
import com.example.admsesion4.data.CasosDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class MunicipioPulsado extends AppCompatActivity {
    CasosDbHelper casosdbhelperr = new CasosDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipio_pulsado);
        TextView nombremunicipioTw = findViewById(R.id.nombremunicipio);
        String nombremunicipio = "No seleccionado";

        TextView codigomunicipioTw = findViewById(R.id.codmunicipio);
        int codigomunicipio = 0;

        TextView casospcrTw = findViewById(R.id.casospcr);
        int casospcr = 0;

        TextView incidenciaacumuladaTw = findViewById(R.id.incidenciaacumulada);
        String incidenciaacumulada = "No seleccionado";

        TextView casospcr14Tw = findViewById(R.id.casospcr14);
        int casospcr14 = 0;

        TextView incidenciaacumulada14Tw = findViewById(R.id.incidenciaacumulada14);
        String incidenciaacumulada14 = "No seleccionado";

        TextView defuncionsTw = findViewById(R.id.defuncions);
        int defuncions = 0;

        TextView tasadefuncionsTw = findViewById(R.id.tasadefuncions);
        String tasadefuncions = "No seleccionado";

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            nombremunicipio = extras.getString("nombreMunicipio");
            codigomunicipio = extras.getInt("codMunicipio");
            casospcr = extras.getInt("casos");
            incidenciaacumulada = extras.getString("incidenciaacumulada");
            casospcr14 = extras.getInt("casos14dias");
            incidenciaacumulada14 = extras.getString("incidenciaacumulada14dias");
            defuncions = extras.getInt("defuncions");
            tasadefuncions = extras.getString("tasadefuncio");

        }

        ListView casoslist = findViewById(R.id.casos_list);
        CasosDbHelper casosdbhelper = new CasosDbHelper(this);
        CasoCursorAdapter adapter = new CasoCursorAdapter(this, casosdbhelper.FindReportsByMunicipality(nombremunicipio));
        casoslist.setAdapter(adapter);

        nombremunicipioTw.setText(nombremunicipio);
        codigomunicipioTw.setText(String.valueOf(codigomunicipio));
        casospcrTw.setText(String.valueOf(casospcr));
        incidenciaacumuladaTw.setText(incidenciaacumulada);
        casospcr14Tw.setText(String.valueOf(casospcr14));
        incidenciaacumulada14Tw.setText(incidenciaacumulada14);
        defuncionsTw.setText(String.valueOf(defuncions));
        tasadefuncionsTw.setText(tasadefuncions);

        this.setTitle(nombremunicipio);

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        String finalNombremunicipio = nombremunicipio;
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombremunicipioo = extras.getString("nombreMunicipio");
                Bundle args = getIntent().getBundleExtra("BUNDLE");
                ArrayList<Municipio> municipios = (ArrayList<Municipio>) args.getSerializable("ARRAYLIST");

                Intent intent = new Intent(getApplicationContext(), ReporteCaso.class);
                ArrayList<Municipio> object = municipios;

                Bundle args2 = new Bundle();
                args2.putSerializable("ARRAYLIST",(Serializable)object);
                intent.putExtra("BUNDLE",args2);
                intent.putExtra("nombreMunicipio", nombremunicipioo);
                startActivity(intent);
            }
        });

        casoslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nombremunicipioo = extras.getString("nombreMunicipio");
                Cursor cursor = (Cursor) casoslist.getItemAtPosition(i);
                //cursor.moveToFirst();
                String code = cursor.getString(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.CODE));

                //cursor.close();

                Cursor cursord = casosdbhelperr.FindReportByNumeroReporte(code);
                cursord.moveToFirst();

                //Log.d("cursordata", String.valueOf(cursor.moveToFirst()));

                String date = cursord.getString(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.SDATE));

                int fever = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.FEVER));
                Boolean bfever = false;
                if(fever>0)
                {
                    bfever = true;
                }

                int cough = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.COUGH));
                Boolean bcough = false;
                if(cough>0)
                {
                    bcough = true;
                }

                int shortness = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.SHORTNESS));
                Boolean bshortness = false;
                if(shortness>0)
                {
                    bshortness = true;
                }

                int muscle = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.MUSCLEBODYACHES));
                Boolean bmuscle = false;
                if(muscle>0)
                {
                    bmuscle = true;
                }

                int headache = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.HEADACHE));
                Boolean bheadache = false;
                if(headache>0)
                {
                    bheadache = true;
                }

                int diarrhea = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.DIARRHEA));
                Boolean bdiarrhea = false;
                if(diarrhea>0)
                {
                    bdiarrhea = true;
                }

                int lossoftaste = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.LOSSOFTASEORSMELL));
                Boolean blossoftaste = false;
                if(lossoftaste>0)
                {
                    blossoftaste = true;
                }

                int congestion = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.CONGESTION));
                Boolean bcongestion = false;
                if(congestion>0)
                {
                    bcongestion = true;
                }

                int fatigue = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.FATIGUE));
                Boolean bfatigue = false;
                if(fatigue>0)
                {
                    bfatigue = true;
                }

                int nausea = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.NAUSEA));
                Boolean bnausea = false;
                if(nausea>0)
                {
                    bnausea = true;
                }

                int sorethroat = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.SORETHROAT));
                Boolean bsorethroat = false;
                if(sorethroat>0)
                {
                    bsorethroat = true;
                }

                int closecontact = cursord.getInt(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.CLOSECONTACT));
                Boolean bclosecontact = false;
                if(closecontact>0)
                {
                    bclosecontact = true;
                }


                Bundle args = getIntent().getBundleExtra("BUNDLE");
                ArrayList<Municipio> municipios = (ArrayList<Municipio>) args.getSerializable("ARRAYLIST");

                Intent intent = new Intent(getApplicationContext(), ReporteCaso.class);
                ArrayList<Municipio> object = municipios;

                Bundle args2 = new Bundle();
                args2.putSerializable("ARRAYLIST",(Serializable)object);
                intent.putExtra("BUNDLE",args2);
                intent.putExtra("nombreMunicipio", nombremunicipioo);
                intent.putExtra("code", code);
                intent.putExtra("date", date);
                intent.putExtra("fever", bfever);
                intent.putExtra("cough", bcough);
                intent.putExtra("shortness", bshortness);
                intent.putExtra("muscle", bmuscle);
                intent.putExtra("headache", bheadache);
                intent.putExtra("diarrhea", bdiarrhea);
                intent.putExtra("lossoftaste", blossoftaste);
                intent.putExtra("congestion", bcongestion);
                intent.putExtra("fatigue", bfatigue);
                intent.putExtra("nausea", bnausea);
                intent.putExtra("sorethroat", bsorethroat);
                intent.putExtra("closecontact", bclosecontact);
                startActivity(intent);
                finish();
            }
        });
    }
}