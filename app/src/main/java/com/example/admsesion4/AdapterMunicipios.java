package com.example.admsesion4;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdapterMunicipios extends RecyclerView.Adapter<AdapterMunicipios.ViewHolder> implements Filterable {
    private ArrayList<Municipio> municipios, municipioslleno;

    Context context;
    BufferedReader reader = null;
    private ItemClickListener mClickListener;

    public void ordenarpornombre(){
        Collections.sort(municipios, new Comparator<Municipio>(){

            public int compare(Municipio o1, Municipio o2)
            {
                return o1.getMunicipi().compareTo(o2.getMunicipi());
            }
        });
        notifyDataSetChanged();
    }

    public void ordenarporcasos(){
        Collections.sort(municipios, new Comparator<Municipio>(){

            public int compare(Municipio o1, Municipio o2)
            {
                return o1.getCasos() - o2.getCasos();
            }
        });
        notifyDataSetChanged();
    }

    public void ordenarportasadefuncion(){
        Collections.sort(municipios, new Comparator<Municipio>(){

            public int compare(Municipio o1, Municipio o2)
            {
                return o1.getTasadefuncio().compareTo(o2.getTasadefuncio());
            }
        });
        notifyDataSetChanged();
    }

    public void ordenarporincidenciaacumulada(){
        Collections.sort(municipios, new Comparator<Municipio>(){

            public int compare(Municipio o1, Municipio o2)
            {
                return o1.getIncidenciaacumulada().compareTo(o2.getIncidenciaacumulada());
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return MunicipioFilter;
    }

    private Filter MunicipioFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Municipio> listaFiltrada = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                listaFiltrada.addAll(municipioslleno);
            } else {
                String patronFiltrado = constraint.toString().toLowerCase().trim();

                for (Municipio municipio : municipioslleno) {
                    if (municipio.getMunicipi().toLowerCase().contains(patronFiltrado)) {
                        listaFiltrada.add(municipio);
                    }

                }
            }

            FilterResults results = new FilterResults();
            results.values = listaFiltrada;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            municipios.clear();
            municipios.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public interface ItemClickListener {
        void onRVItemClick(View view, int position);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public AdapterMunicipios(Context c, ArrayList municipios) throws IOException {
        context=c;
        Init(municipios);
    }

    public void Init(ArrayList<Municipio> municipioss) throws IOException {
        // We read the JSON file and fill the “municipios” ArrayList
        municipios = municipioss;
        municipioslleno = new ArrayList<>(municipios);

        /*InputStream is=context.getAssets().open("municipios.json");
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonString = readAll(rd);
        is.close();
        try{
            JSONObject jsonOobject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonOobject.getJSONArray("records");
            int num = jsonArray.length();
            // número de elementos en el array
            for(int i=0 ; i< num; i++)
            {
                JSONObject municipioData = jsonArray.getJSONObject(i);

                if(municipioData.has("Municipi")){
                    Municipio municipio = new Municipio();
                    municipio.setId(municipioData.getInt("_id"));
                    municipio.setCodMunicipio(municipioData.getInt("CodMunicipio"));
                    municipio.setMunicipi(municipioData.getString("Municipi"));
                    municipio.setCasos(municipioData.getInt("Casos PCR+"));
                    municipio.setIncidenciaacumulada(municipioData.getString("Incidència acumulada PCR+"));
                    municipio.setCasospcr14dias(municipioData.getInt("Casos PCR+ 14 dies"));
                    municipio.setIncidenciaacumuladapcr14dias(municipioData.getString("Incidència acumulada PCR+14"));
                    municipio.setDefuncions(municipioData.getInt("Defuncions"));
                    municipio.setTasadefuncio(municipioData.getString("Taxa de defunció"));
                    Log.d("codmunicipio", String.valueOf(municipio.getCodMunicipio()));
                    municipios.add(municipio);
                }
                else
                    continue;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        municipioslleno = new ArrayList<>(municipios);*/
    }
    @Override
    public int getItemCount() {
        return municipios.size();
    }

    public Municipio getItemAtPosition(int position){
        return municipios.get(position);
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textView;
        private final TextView textViewdos;
        private final TextView textViewtres;
        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView1);
            textViewdos = (TextView) view.findViewById(R.id.textView2);
            textViewtres = (TextView) view.findViewById(R.id.textView3);
            view.setOnClickListener(this);
        }
        public TextView getTextView() {
            return textView;
        }
        public TextView getTextView2() {
            return textViewdos;
        }
        public TextView getTextView3() {
            return textViewtres;
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onRVItemClick(view, getAdapterPosition());
        }

    }
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ejemploitemmunicipio, viewGroup, false);
        return new ViewHolder(view);
    }



    public ArrayList<Municipio> getMunicipios(){
        return municipioslleno;
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.getTextView().setText(String.valueOf(municipios.get(position).getMunicipi()));
        holder.getTextView2().setText((String.valueOf(municipios.get(position).getId())));
        holder.getTextView3().setText((String.valueOf(municipios.get(position).getCasos())));
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}