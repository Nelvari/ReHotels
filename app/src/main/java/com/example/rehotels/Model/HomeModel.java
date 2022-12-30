package com.example.rehotels.Model;


import java.io.Serializable;

public class HomeModel implements Serializable {

    private int id;
    private String namaHotel;
    private String hargaHotel;
    private String urlImage;
    private String rating;
    private String alamat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaHotel() {
        return namaHotel;
    }

    public void setNamaHotel(String namaHotel) {
        this.namaHotel = namaHotel;
    }

    public String getHargaHotel() {
        return hargaHotel;
    }

    public void setHargaHotel(String hargaHotel) {
        this.hargaHotel = hargaHotel;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
