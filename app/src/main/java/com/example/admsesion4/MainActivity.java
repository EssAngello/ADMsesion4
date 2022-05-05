package com.example.admsesion4;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admsesion4.data.CasosDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterMunicipios.ItemClickListener {

    private AdapterMunicipios adapter = null;
    HTTPConnector httpConnector = new HTTPConnector();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CasosDbHelper dbHelper = new CasosDbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //RecyclerView recyclerView = findViewById(R.id.recycler1);

        //String[] opciones = {"incidencia acumulada", "casos", "nombre municipio", "tasa de defunción"};

        //ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);

        /*recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            adapter = new AdapterMunicipios(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(MainActivity.this);*/

        httpConnector.execute("https://dadesobertes.gva.es/api/3/action/package_show?id=5403e057-5b64-4347-ae44-06fa7a65e1b8");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                if(db != null){
                    Intent intent = new Intent(getApplicationContext(), ReporteCaso.class);
                    ArrayList<Municipio> object = adapter.getMunicipios();
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST",(Serializable)object);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                }
                else{
                    Snackbar.make(view, "Error al crear base de datos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }

    class HTTPConnector extends AsyncTask<String, Void, ArrayList> {
        @Override
        protected ArrayList doInBackground(String... params) {
            ArrayList municipios=new ArrayList<Municipio>();

            String url = params[0];
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            String id_paquete = null;
            Writer writer2 = new StringWriter();
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                //add request header
                con.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                con.setRequestProperty("accept", "application/json;");
                con.setRequestProperty("accept-language", "es");
                con.connect();
                int responseCode = con.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF8"));
                int n;
                while ((n = in.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
                in.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObject = new JSONObject(writer.toString());
                JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                Log.d("POLLO", jsonObject2.toString());
                JSONArray records = jsonObject2.getJSONArray("resources");
                Log.d("POLLO", records.toString());
                JSONObject jsonObject3 = records.getJSONObject(0);
                Log.d("POLLO", jsonObject3.toString());
                id_paquete = jsonObject3.getString("id");
                Log.d("POLLO", id_paquete);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            url="https://dadesobertes.gva.es/es/api/3/action/datastore_search?resource_id="+id_paquete+"&limit=1000";

            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                //add request header
                con.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                con.setRequestProperty("accept", "application/json;");
                con.setRequestProperty("accept-language", "es");
                con.connect();
                int responseCode = con.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF8"));
                int n;
                while ((n = in.read(buffer)) != -1) {
                    writer2.write(buffer, 0, n);
                }
                in.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObject = new JSONObject(writer2.toString());
                JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                Log.d("POLLO", jsonObject2.toString());
                JSONArray records = jsonObject2.getJSONArray("records");
                Log.d("POLLO", records.toString());

                for(int i=0 ; i< records.length(); i++)
                {
                    JSONObject jsonObject3 = records.getJSONObject(i);
                    Municipio municipio = new Municipio();
                    municipio.setId(records.getJSONObject(i).getInt("_id"));
                    municipio.setCodMunicipio(records.getJSONObject(i).getInt("CodMunicipio"));
                    municipio.setMunicipi(records.getJSONObject(i).getString("Municipi"));
                    municipio.setCasos(records.getJSONObject(i).getInt("Casos PCR+"));
                    municipio.setIncidenciaacumulada(records.getJSONObject(i).getString("Incidència acumulada PCR+"));
                    municipio.setCasospcr14dias(records.getJSONObject(i).getInt("Casos PCR+ 14 dies"));
                    municipio.setIncidenciaacumuladapcr14dias(records.getJSONObject(i).getString("Incidència acumulada PCR+14"));
                    municipio.setDefuncions(records.getJSONObject(i).getInt("Defuncions"));
                    municipio.setTasadefuncio(records.getJSONObject(i).getString("Taxa de defunció"));
                    municipios.add(municipio);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return municipios;
        }
        @Override
        protected void onPostExecute(ArrayList municipios) {
            // Create the RecyclerView
            RecyclerView recyclerView = findViewById(R.id.recycler1);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            try {
                adapter = new AdapterMunicipios(getApplicationContext(), municipios);
                recyclerView.setAdapter(adapter);
                adapter.setClickListener(MainActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        if (id == R.id.ordenar_municipio){
            adapter.ordenarpornombre();
        }
        if (id == R.id.ordenar_casos){
            adapter.ordenarporcasos();
        }
        if(id==R.id.ordenar_tasadefuncion){
            adapter.ordenarportasadefuncion();
        }

        if(id==R.id.ordenar_incidenciaacumulada){
            adapter.ordenarporincidenciaacumulada();
        }

        if(id==R.id.refresh){
            httpConnector.cancel(true);
            httpConnector = new HTTPConnector();
            httpConnector.execute("https://dadesobertes.gva.es/api/3/action/package_show?id=5403e057-5b64-4347-ae44-06fa7a65e1b8");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRVItemClick(View view, int position) {
        /*Toast.makeText(this, "Has pulsado en " +
                        adapter.getItemAtPosition(position).getMunicipi() + " que es el item número " + position,
                Toast.LENGTH_SHORT).show();*/
        Intent intent = new Intent(getApplicationContext(), MunicipioPulsado.class);
        intent.putExtra("nombreMunicipio", adapter.getItemAtPosition(position).getMunicipi());
        intent.putExtra("codMunicipio", adapter.getItemAtPosition(position).getCodMunicipio());
        intent.putExtra("casos", adapter.getItemAtPosition(position).getCasos());
        intent.putExtra("incidenciaacumulada", adapter.getItemAtPosition(position).getIncidenciaacumulada());
        intent.putExtra("casos14dias", adapter.getItemAtPosition(position).getCasospcr14dias());
        intent.putExtra("incidenciaacumulada14dias", adapter.getItemAtPosition(position).getIncidenciaacumuladapcr14dias());
        intent.putExtra("defuncions", adapter.getItemAtPosition(position).getDefuncions());
        intent.putExtra("tasadefuncio", adapter.getItemAtPosition(position).getTasadefuncio());

        ArrayList<Municipio> object = adapter.getMunicipios();
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable)object);
        intent.putExtra("BUNDLE",args);

        startActivity(intent);
    }


}