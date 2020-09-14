package com.aidiaoemami.tahsinquran;

public class Hukum {
    int id;
    String hukum;

    public Hukum(int id, String hukum) {
        this.id = id;
        this.hukum = hukum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHukum() {
        return hukum;
    }

    public void setHukum(String hukum) {
        this.hukum = hukum;
    }
}
