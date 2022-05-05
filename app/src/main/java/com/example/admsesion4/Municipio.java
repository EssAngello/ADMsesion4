package com.example.admsesion4;

public class Municipio implements java.io.Serializable{
    private int id;
    private int CodMunicipio;
    private String Municipi;
    private int casos;
    private String incidenciaacumulada;
    private int casospcr14dias;
    private String incidenciaacumuladapcr14dias;
    private int defuncions;
    private String tasadefuncio;

    public void setId(int id) {
        this.id = id;
    }

    public void setCodMunicipio(int codMunicipio) {
        CodMunicipio = codMunicipio;
    }

    public void setMunicipi(String municipi) {
        Municipi = municipi;
    }

    public void setCasos(int casos) {
        this.casos = casos;
    }

    public void setIncidenciaacumulada(String incidenciaacumulada) {
        this.incidenciaacumulada = incidenciaacumulada;
    }

    public void setCasospcr14dias(int casospcr14dias) {
        this.casospcr14dias = casospcr14dias;
    }

    public void setIncidenciaacumuladapcr14dias(String incidenciaacumuladapcr14dias) {
        this.incidenciaacumuladapcr14dias = incidenciaacumuladapcr14dias;
    }

    public void setDefuncions(int defuncions) {
        this.defuncions = defuncions;
    }

    public void setTasadefuncio(String tasadefuncio) {
        this.tasadefuncio = tasadefuncio;
    }

    public int getId() {
        return id;
    }

    public int getCodMunicipio() {
        return CodMunicipio;
    }

    public String getMunicipi() {
        return Municipi;
    }

    public int getCasos() {
        return casos;
    }

    public String getIncidenciaacumulada() {
        return incidenciaacumulada;
    }

    public int getCasospcr14dias() {
        return casospcr14dias;
    }

    public String getIncidenciaacumuladapcr14dias() {
        return incidenciaacumuladapcr14dias;
    }

    public int getDefuncions() {
        return defuncions;
    }

    public String getTasadefuncio() {
        return tasadefuncio;
    }
}
