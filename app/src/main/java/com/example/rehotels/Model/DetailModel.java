package com.example.rehotels.Model;

import java.io.Serializable;

public class DetailModel implements Serializable {

    int id, hotelId;
    String urlImage, jenisRuang, deskripsi, namaHotel;

    public String getNamaHotel() {
        return namaHotel;
    }

    public void setNamaHotel(String namaHotel) {
        this.namaHotel = namaHotel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getJenisRuang() {
        return jenisRuang;
    }

    public void setJenisRuang(String jenisRuang) {
        this.jenisRuang = jenisRuang;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
