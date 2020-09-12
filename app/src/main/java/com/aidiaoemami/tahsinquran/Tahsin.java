package com.aidiaoemami.tahsinquran;

public class Tahsin {
    private int id, hukum;
    private String lafadz, transkripsi;

    public Tahsin(int id, int hukum, String lafadz, String transkripsi) {
        this.id = id;
        this.hukum = hukum;
        this.lafadz = lafadz;
        this.transkripsi = transkripsi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHukum() {
        return hukum;
    }

    public void setHukum(int hukum) {
        this.hukum = hukum;
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
}
