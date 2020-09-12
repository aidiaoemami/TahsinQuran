package com.aidiaoemami.tahsinquran;

public class Tajwid {
    private int id, hukum;
    private String huruf, transliterasi, transkripsi;

    public Tajwid(int id, int hukum, String huruf, String transliterasi, String transkripsi) {
        this.id = id;
        this.hukum = hukum;
        this.huruf = huruf;
        this.transliterasi = transliterasi;
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

    public String getHuruf() {
        return huruf;
    }

    public void setHuruf(String huruf) {
        this.huruf = huruf;
    }

    public String getTransliterasi() {
        return transliterasi;
    }

    public void setTransliterasi(String transliterasi) {
        this.transliterasi = transliterasi;
    }

    public String getTranskripsi() {
        return transkripsi;
    }

    public void setTranskripsi(String transkripsi) {
        this.transkripsi = transkripsi;
    }
}
