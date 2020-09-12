package com.aidiaoemami.tahsinquran;

public class Quran {
//    private int id;
//    private String surat;
//    private int ayat;
//    private String lafadz;
//    private String transkripsi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getHukum() {
        return hukum;
    }

    public void setHukum(String hukum) {
        this.hukum = hukum;
    }

    public String getLafadz() {
        return lafadz;
    }

    public void setLafadz(String lafadz) {
        this.lafadz = lafadz;
    }

    private int id;
    private String huruf;
    private String transliterasi;
    private String transkripsi;
    private String hukum;
    private String lafadz;

    public Quran(int id, String huruf, String transliterasi, String transkripsi, String hukum, String lafadz) {
        this.id = id;
        this.huruf = huruf;
        this.transliterasi = transliterasi;
        this.transkripsi = transkripsi;
        this.hukum = hukum;
        this.lafadz = lafadz;
    }

//    public Quran(int id, String surat, int ayat, String lafadz, String transkripsi) {
//        this.id = id;
//        this.surat = surat;
//        this.ayat = ayat;
//        this.lafadz = lafadz;
//        this.transkripsi = transkripsi;
//    }
//
//    public int getId() {
//
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getSurat() {
//        return surat;
//    }
//
//    public void setSurat(String surat) {
//        this.surat = surat;
//    }
//
//    public int getAyat() {
//        return ayat;
//    }
//
//    public void setAyat(int ayat) {
//        this.ayat = ayat;
//    }
//
//    public String getLafadz() {
//        return lafadz;
//    }
//
//    public void setLafadz(String lafadz) {
//        this.lafadz = lafadz;
//    }
//
//    public String getTranskripsi() {
//        return transkripsi;
//    }
//
//    public void setTranskripsi(String transkripsi) {
//        this.transkripsi = transkripsi;
//    }
}
