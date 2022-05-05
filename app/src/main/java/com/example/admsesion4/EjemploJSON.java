package com.example.admsesion4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;


public class EjemploJSON
{
    //private static final String CADENAJSON = "";

    public static void json() throws IOException {
        InputStream is = new URL("https://dadesobertes.gva.es/es/api/3/action/datastore_search?resource_id=7fd9a2bf-ffee-4604-907e-643a8009b04e&limit=1000").openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonString = readAll(rd);

        try{
            JSONArray jsonArray = new JSONArray(jsonString);
            int num = jsonArray.length();
            // número de elementos en el array
            for(int i=0 ; i< num; i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println("jsonObject " + i + ": color " + jsonObject.getString("color")+ ", valor " + jsonObject.getString("valor"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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