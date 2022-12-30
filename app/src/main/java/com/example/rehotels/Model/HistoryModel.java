package com.example.rehotels.Model;

import java.io.Serializable;

public class HistoryModel implements Serializable {

    int id;
    String namaHotel, jenisRuang, tanggalCheckIn, tanggalCheckOut, status;

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

    public String getJenisRuang() {
        return jenisRuang;
    }

    public void setJenisRuang(String jenisRuang) {
        this.jenisRuang = jenisRuang;
    }

    public String getTanggalCheckIn() {
        return tanggalCheckIn;
    }

    public void setTanggalCheckIn(String tanggalCheckIn) {
        this.tanggalCheckIn = tanggalCheckIn;
    }

    public String getTanggalCheckOut() {
        return tanggalCheckOut;
    }

    public void setTanggalCheckOut(String tanggalCheckOut) {
        this.tanggalCheckOut = tanggalCheckOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
