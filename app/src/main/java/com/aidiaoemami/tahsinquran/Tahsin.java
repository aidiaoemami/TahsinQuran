package com.aidiaoemami.tahsinquran;

public class Tahsin {
    private int id;
    private String lafadz, transkripsi, transliterasi;

    public Tahsin(int id, String lafadz, String transkripsi) {
        this.id = id;
        this.lafadz = lafadz;
        this.transkripsi = transkripsi;
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

    public String getTranskripsi() {
        return transkripsi;
    }


}
