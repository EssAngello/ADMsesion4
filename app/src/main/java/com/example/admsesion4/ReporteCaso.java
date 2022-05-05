package com.example.admsesion4;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.admsesion4.data.Caso;
import com.example.admsesion4.data.CasosDbHelper;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReporteCaso extends AppCompatActivity {

    TextView mensaje1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_caso);
        mensaje1 = (TextView) findViewById(R.id.mensaje_id);
        Intent intent = getIntent();

        Spinner spinnermunicipios = findViewById(R.id.spinnermunicipios);

        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Municipio> municipios = (ArrayList<Municipio>) args.getSerializable("ARRAYLIST");
        ArrayList<String> nombresmunicipios= new ArrayList<String>();

        for(Municipio municipio:municipios)
        {
            nombresmunicipios.add(municipio.getMunicipi());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, nombresmunicipios);
        spinnermunicipios.setAdapter(adapter);

        Spinner spinnerclosecontact = findViewById(R.id.spinnerclosecontact);

        String[] opciones = {"SI","NO"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, opciones);
        spinnerclosecontact.setAdapter(adapter2);

        Bundle extras = getIntent().getExtras();

        String nombremunicipio = extras.getString("nombreMunicipio");

        if(nombremunicipio!=null)
        {
            int spinnerPosition = adapter.getPosition(nombremunicipio);
            spinnermunicipios.setSelection(spinnerPosition);
        }

        //MenuItem deletecaso = optionsMenu.findItem(R.id.action_delte);
        //deletecaso.setVisible(false);
        //MenuItem deletecaso = menu_report_caso.findItem(R.id.action_delte);


        String code = extras.getString("code");

        if(code!=null)
        {
            //CasosDbHelper casosdbhelper = new CasosDbHelper(this);
            //Cursor cursor = casosdbhelper.FindReportByNumeroReporte(code);

            //String date = cursor.getString(cursor.getColumnIndexOrThrow(CasoContracts.CasoEntry.SDATE));
            String date = extras.getString("date");
            Boolean fever = extras.getBoolean("fever");
            Boolean cough = extras.getBoolean("cough");
            Boolean shortness = extras.getBoolean("shortness");
            Boolean muscle = extras.getBoolean("muscle");
            Boolean headache = extras.getBoolean("headache");
            Boolean diarrhea = extras.getBoolean("diarrhea");
            Boolean lose = extras.getBoolean("lossoftaste");
            Boolean congestion = extras.getBoolean("congestion");
            Boolean fatigue = extras.getBoolean("fatigue");
            Boolean nausea = extras.getBoolean("nausea");
            Boolean sore = extras.getBoolean("sorethroat");
            Boolean closecontact = extras.getBoolean("closecontact");


            EditText etcode = findViewById(R.id.et_code);
            etcode.setText(code);
            etcode.setEnabled(false);

            EditText etdate = findViewById(R.id.date);
            etdate.setText(date);

            CheckBox cdfever = findViewById(R.id.checkBoxFever);
            cdfever.setChecked(fever);

            CheckBox cdcough = findViewById(R.id.checkBoxCough);
            cdcough.setChecked(cough);

            CheckBox cdshortness = findViewById(R.id.checkBoxShortness);
            cdshortness.setChecked(shortness);

            CheckBox cdmuscle = findViewById(R.id.checkBoxMuscle);
            cdmuscle.setChecked(muscle);

            CheckBox cdheadache = findViewById(R.id.checkBoxHeadache);
            cdheadache.setChecked(headache);

            CheckBox cddiarrhea = findViewById(R.id.checkBoxDiarrhea);
            cddiarrhea.setChecked(diarrhea);

            CheckBox cdlose = findViewById(R.id.checkBoxLose);
            cdlose.setChecked(lose);

            CheckBox cdcongestion = findViewById(R.id.checkBoxCongestion);
            cdcongestion.setChecked(congestion);

            CheckBox cdfatigue = findViewById(R.id.checkBoxFatigue);
            cdfatigue.setChecked(fatigue);

            CheckBox cdnausea = findViewById(R.id.checkBoxNausea);
            cdnausea.setChecked(nausea);

            CheckBox cdsore = findViewById(R.id.checkBoxSore);
            cdsore.setChecked(sore);

            int spinnerPosition2;
            if(closecontact)
                spinnerPosition2 = adapter2.getPosition("SI");
            else
                spinnerPosition2 = adapter2.getPosition("NO");

            spinnerclosecontact.setSelection(spinnerPosition2);

        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        this.setTitle("Reporte Caso");

    }

    @Override
    public void onBackPressed() {
        Bundle extras = getIntent().getExtras();

        String code = extras.getString("code");

        if(code != null)
        {
            Intent intent = new Intent(getApplicationContext(), MunicipioPulsado.class);

            Intent intentt = getIntent();

            Bundle args = intentt.getBundleExtra("BUNDLE");
            ArrayList<Municipio> municipios = (ArrayList<Municipio>) args.getSerializable("ARRAYLIST");

            String nombremunicipio = extras.getString("nombreMunicipio");

            for(Municipio municipio: municipios)
            {
                if(municipio.getMunicipi().equals(nombremunicipio))
                {
                    intent.putExtra("codMunicipio", municipio.getCodMunicipio());
                    intent.putExtra("casos", municipio.getCasos());
                    intent.putExtra("incidenciaacumulada", municipio.getIncidenciaacumulada());
                    intent.putExtra("casos14dias", municipio.getCasospcr14dias());
                    intent.putExtra("incidenciaacumulada14dias", municipio.getIncidenciaacumuladapcr14dias());
                    intent.putExtra("defuncions", municipio.getDefuncions());
                    intent.putExtra("tasadefuncio", municipio.getTasadefuncio());
                    break;
                }
            }

            intent.putExtra("nombreMunicipio", nombremunicipio);


            ArrayList<Municipio> object = municipios;
            Bundle argss = new Bundle();
            argss.putSerializable("ARRAYLIST",(Serializable)object);
            intent.putExtra("BUNDLE",argss);

            startActivity(intent);
            finish();
        }
        else
            finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report_caso, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        Bundle extras = getIntent().getExtras();
        String code = extras.getString("code");
        if(code==null)
        {
            MenuItem deletecaso = menu.findItem(R.id.action_delte);
            deletecaso.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_filtrar_municipio) {
            SearchView searchView = (SearchView) item.getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        }*/

        if (id == R.id.action_check){

            Bundle extras = getIntent().getExtras();
            String codee = extras.getString("code");

            EditText etcode = findViewById(R.id.et_code);
            String code = etcode.getText().toString();

            EditText etdate = findViewById(R.id.date);
            String date = etdate.getText().toString();

            CheckBox cdfever = findViewById(R.id.checkBoxFever);
            Boolean fever = false;
            if(cdfever.isChecked())
                fever = true;

            CheckBox cdcough = findViewById(R.id.checkBoxCough);
            Boolean cough = false;
            if(cdcough.isChecked())
                cough = true;

            CheckBox cdshortness = findViewById(R.id.checkBoxShortness);
            Boolean shortness = false;
            if(cdshortness.isChecked())
                shortness = true;

            CheckBox cdmuscle = findViewById(R.id.checkBoxMuscle);
            Boolean muscle = false;
            if(cdmuscle.isChecked())
                muscle = true;

            CheckBox cdheadache = findViewById(R.id.checkBoxHeadache);
            Boolean headache = false;
            if(cdheadache.isChecked())
                headache = true;

            CheckBox cddiarrhea = findViewById(R.id.checkBoxDiarrhea);
            Boolean diarrhea = false;
            if(cddiarrhea.isChecked())
                diarrhea = true;

            CheckBox cdlose = findViewById(R.id.checkBoxLose);
            Boolean lose = false;
            if(cdlose.isChecked())
                lose = true;

            CheckBox cdcongestion = findViewById(R.id.checkBoxCongestion);
            Boolean congestion = false;
            if(cdcongestion.isChecked())
                congestion = true;

            CheckBox cdfatigue = findViewById(R.id.checkBoxFatigue);
            Boolean fatigue = false;
            if(cdfatigue.isChecked())
                fatigue = true;

            CheckBox cdnausea = findViewById(R.id.checkBoxNausea);
            Boolean nausea = false;
            if(cdnausea.isChecked())
                nausea = true;

            CheckBox cdsore = findViewById(R.id.checkBoxSore);
            Boolean sore = false;
            if(cdsore.isChecked())
                sore = true;
            Spinner spinnerclosecontact = findViewById(R.id.spinnerclosecontact);
            String closecontact = spinnerclosecontact.getSelectedItem().toString();
            Boolean bCloseContact = true;
            if(closecontact.equals("NO"))
                bCloseContact = false;

            Spinner spinnermunicipios = findViewById(R.id.spinnermunicipios);
            String municipio = spinnermunicipios.getSelectedItem().toString();

            Date date1 = null;

            if(codee==null){
                try {
                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    Caso caso = new Caso(code, date1, fever, cough, shortness, fatigue, muscle, headache, lose, sore, congestion, nausea, diarrhea, bCloseContact, municipio);
                    CasosDbHelper casosdbhelper = new CasosDbHelper(this);
                    casosdbhelper.saveCaso(caso);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    Caso caso = new Caso(code, date1, fever, cough, shortness, fatigue, muscle, headache, lose, sore, congestion, nausea, diarrhea, bCloseContact, municipio);
                    CasosDbHelper casosdbhelper = new CasosDbHelper(this);
                    Boolean b = casosdbhelper.UpdateReport(caso);
                    if(b)
                        Toast.makeText(this, "Caso actualizado correctamente" , Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "No se ha podido actualizar el caso" , Toast.LENGTH_SHORT).show();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }

        if (id == R.id.action_delte) {
            Bundle extras = getIntent().getExtras();
            String codee = extras.getString("code");

            /*EditText etcode = findViewById(R.id.et_code);
            String code = etcode.getText().toString();

            EditText etdate = findViewById(R.id.date);
            String date = etdate.getText().toString();

            CheckBox cdfever = findViewById(R.id.checkBoxFever);
            Boolean fever = false;
            if(cdfever.isChecked())
                fever = true;

            CheckBox cdcough = findViewById(R.id.checkBoxCough);
            Boolean cough = false;
            if(cdcough.isChecked())
                cough = true;

            CheckBox cdshortness = findViewById(R.id.checkBoxShortness);
            Boolean shortness = false;
            if(cdshortness.isChecked())
                shortness = true;

            CheckBox cdmuscle = findViewById(R.id.checkBoxMuscle);
            Boolean muscle = false;
            if(cdmuscle.isChecked())
                muscle = true;

            CheckBox cdheadache = findViewById(R.id.checkBoxHeadache);
            Boolean headache = false;
            if(cdheadache.isChecked())
                headache = true;

            CheckBox cddiarrhea = findViewById(R.id.checkBoxDiarrhea);
            Boolean diarrhea = false;
            if(cddiarrhea.isChecked())
                diarrhea = true;

            CheckBox cdlose = findViewById(R.id.checkBoxLose);
            Boolean lose = false;
            if(cdlose.isChecked())
                lose = true;

            CheckBox cdcongestion = findViewById(R.id.checkBoxCongestion);
            Boolean congestion = false;
            if(cdcongestion.isChecked())
                congestion = true;

            CheckBox cdfatigue = findViewById(R.id.checkBoxFatigue);
            Boolean fatigue = false;
            if(cdfatigue.isChecked())
                fatigue = true;

            CheckBox cdnausea = findViewById(R.id.checkBoxNausea);
            Boolean nausea = false;
            if(cdnausea.isChecked())
                nausea = true;

            CheckBox cdsore = findViewById(R.id.checkBoxSore);
            Boolean sore = false;
            if(cdsore.isChecked())
                sore = true;
            Spinner spinnerclosecontact = findViewById(R.id.spinnerclosecontact);
            String closecontact = spinnerclosecontact.getSelectedItem().toString();
            Boolean bCloseContact = true;
            if(closecontact.equals("NO"))
                bCloseContact = false;

            Spinner spinnermunicipios = findViewById(R.id.spinnermunicipios);
            String municipio = spinnermunicipios.getSelectedItem().toString();

            Date date1 = null;*/
            /*date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            Caso caso = new Caso(code, date1, fever, cough, shortness, fatigue, muscle, headache, lose, sore, congestion, nausea, diarrhea, bCloseContact, municipio);*/
            CasosDbHelper casosdbhelper = new CasosDbHelper(this);
            Boolean b = casosdbhelper.DeleteReport(codee);
            if(b)
                Toast.makeText(this, "Caso Eliminado correctamente" , Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "No se ha podido eliminar el caso" , Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        mensaje1.setText("Localización agregada");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    mensaje1.setText("Mi direccion es: \n"
                            + DirCalle.getLocality());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class Localizacion implements LocationListener {
        ReporteCaso mainActivity;
        public ReporteCaso getMainActivity() {
            return mainActivity;
        }
        public void setMainActivity(ReporteCaso mainActivity) {
            this.mainActivity = mainActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            String Text = "Mi Municipio actual segun el gps es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            mensaje1.setText(Text);
            this.mainActivity.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            mensaje1.setText("GPS Desactivado");
        }
        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            mensaje1.setText("GPS Activado");
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }

}