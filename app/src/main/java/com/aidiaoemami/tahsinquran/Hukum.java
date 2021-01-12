package com.aidiaoemami.tahsinquran;

public class Hukum {
    int id;
    String hukum;
    String cara_baca;

    public Hukum(int id, String hukum, String cara_baca) {
        this.id = id;
        this.hukum = hukum;
        this.cara_baca = cara_baca;
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

    public String getCara_baca() {
        return cara_baca;
    }

    public void setCara_baca(String cara_baca) {
        this.cara_baca = cara_baca;
    }
}
