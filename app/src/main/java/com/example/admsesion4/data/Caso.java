package com.example.admsesion4.data;

import android.content.ContentValues;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Caso {

    private String code;
    private Date startdate;
    private Boolean fever;
    private Boolean cough;
    private Boolean shortness;
    private Boolean fatigue;
    private Boolean muscleBodyAches;
    private Boolean headachea;
    private Boolean loss_of_taste_or_smell;
    private Boolean soreThroat;
    private Boolean congestion;
    private Boolean nausea;
    private Boolean diarrhea;
    private Boolean closeContact;
    private String municipio;

    public Caso(String code, Date startdate, Boolean fever, Boolean cough, Boolean shortness, Boolean fatigue, Boolean muscleBodyAches, Boolean headachea, Boolean loss_of_taste_or_smell, Boolean soreThroat, Boolean congestion, Boolean nausea, Boolean diarrhea, Boolean closeContacte, String municipioid) {
        this.code = code;
        this.startdate = startdate;
        this.fever = fever;
        this.cough = cough;
        this.shortness = shortness;
        this.fatigue = fatigue;
        this.muscleBodyAches = muscleBodyAches;
        this.headachea = headachea;
        this.loss_of_taste_or_smell = loss_of_taste_or_smell;
        this.soreThroat = soreThroat;
        this.congestion = congestion;
        this.nausea = nausea;
        this.diarrhea = diarrhea;
        this.closeContact = closeContacte;
        this.municipio = municipioid;
    }

    public String getId() {
        return code;
    }

    public void setId(String code) {
        this.code = code;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Boolean getFever() {
        return fever;
    }

    public void setFever(Boolean fever) {
        this.fever = fever;
    }

    public Boolean getCough() {
        return cough;
    }

    public void setCough(Boolean cough) {
        this.cough = cough;
    }

    public Boolean getShortness() {
        return shortness;
    }

    public void setShortness(Boolean shortness) {
        this.shortness = shortness;
    }

    public Boolean getFatigue() {
        return fatigue;
    }

    public void setFatigue(Boolean fatigue) {
        this.fatigue = fatigue;
    }

    public Boolean getMuscleBodyAches() {
        return muscleBodyAches;
    }

    public void setMuscleBodyAches(Boolean muscleBodyAches) {
        this.muscleBodyAches = muscleBodyAches;
    }

    public Boolean getHeadachea() {
        return headachea;
    }

    public void setHeadachea(Boolean headachea) {
        this.headachea = headachea;
    }

    public Boolean getLoss_of_taste_or_smell() {
        return loss_of_taste_or_smell;
    }

    public void setLoss_of_taste_or_smell(Boolean loss_of_taste_or_smell) {
        this.loss_of_taste_or_smell = loss_of_taste_or_smell;
    }

    public Boolean getSoreThroat() {
        return soreThroat;
    }

    public void setSoreThroat(Boolean soreThroat) {
        this.soreThroat = soreThroat;
    }

    public Boolean getCongestion() {
        return congestion;
    }

    public void setCongestion(Boolean congestion) {
        this.congestion = congestion;
    }

    public Boolean getNausea() {
        return nausea;
    }

    public void setNausea(Boolean nausea) {
        this.nausea = nausea;
    }

    public Boolean getDiarrhea() {
        return diarrhea;
    }

    public void setDiarrhea(Boolean diarrhea) {
        this.diarrhea = diarrhea;
    }

    public Boolean getCloseContacte() {
        return closeContact;
    }

    public void setCloseContacte(Boolean closeContacte) {
        this.closeContact = closeContacte;
    }

    public Boolean getCloseContact() {
        return closeContact;
    }

    public void setCloseContact(Boolean closeContact) {
        this.closeContact = closeContact;
    }

    public String getMunicipioid() {
        return municipio;
    }

    public void setMunicipioid(String municipioid) {
        this.municipio = municipioid;
    }

    public ContentValues toContentValues() {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = startdate;
        String todayAsString = df.format(today);

        System.out.println("Today is: " + todayAsString);
        ContentValues values = new ContentValues();
        values.put(CasoContracts.CasoEntry.CODE, code);
        values.put(CasoContracts.CasoEntry.SDATE, todayAsString);
        values.put(CasoContracts.CasoEntry.FEVER, fever);
        values.put(CasoContracts.CasoEntry.COUGH, cough);
        values.put(CasoContracts.CasoEntry.SHORTNESS, shortness);
        values.put(CasoContracts.CasoEntry.FATIGUE, fatigue);
        values.put(CasoContracts.CasoEntry.MUSCLEBODYACHES, muscleBodyAches);
        values.put(CasoContracts.CasoEntry.HEADACHE, headachea);
        values.put(CasoContracts.CasoEntry.LOSSOFTASEORSMELL, loss_of_taste_or_smell);
        values.put(CasoContracts.CasoEntry.SORETHROAT, soreThroat);
        values.put(CasoContracts.CasoEntry.CONGESTION, congestion);
        values.put(CasoContracts.CasoEntry.NAUSEA, nausea);
        values.put(CasoContracts.CasoEntry.DIARRHEA, diarrhea);
        values.put(CasoContracts.CasoEntry.CLOSECONTACT, closeContact);
        values.put(CasoContracts.CasoEntry.MUNICIPIO, municipio);
        return values;
    }

}
