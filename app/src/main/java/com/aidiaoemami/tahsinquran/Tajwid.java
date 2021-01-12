package com.aidiaoemami.tahsinquran;

public class Tajwid {
    private int id, hukum;
    private String huruf, pola;

    public Tajwid(int id, String huruf, String pola, int hukum) {
        this.id = id;
        this.huruf = huruf;
        this.pola = pola;
        this.hukum = hukum;
    }

    public int getId() {
        return id;
    }

    public int getHukum() {
        return hukum;
    }

    public String getHuruf() {
        return huruf;
    }

    public String getPola() {
        return pola;
    }
}
