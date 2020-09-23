package com.aidiaoemami.tahsinquran;

public class Tahsin {
    private int id;
    private String lafadz, transkripsi, transliterasi;

    public Tahsin(int id, String transliterasi, String lafadz, String transkripsi) {
        this.id = id;
        this.lafadz = lafadz;
        this.transkripsi = transkripsi;
        this.transliterasi = transliterasi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLafadz() {
        return lafadz;
    }

    public void setLafadz(String lafadz) {
        this.lafadz = lafadz;
    }

    public String getTranskripsi() {
        return transkripsi;
    }

    public void setTranskripsi(String transkripsi) {
        this.transkripsi = transkripsi;
    }

    public String getTransliterasi() {
        return transliterasi;
    }

    public void setTransliterasi(String transliterasi) {
        this.transliterasi = transliterasi;
    }
}
