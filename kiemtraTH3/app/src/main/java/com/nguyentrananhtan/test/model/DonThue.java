package com.nguyentrananhtan.test.model;

public class DonThue {
    int maDon;
    String hangXe;
    double giaThue;
    String hinhAnh;
    String diaDiem;
    String loaiXe;

    //constructor

    public DonThue(int maDon, String hangXe, double giaThue, String hinhAnh, String diaDiem, String loaiXe) {
        this.maDon = maDon;
        this.hangXe = hangXe;
        this.giaThue = giaThue;
        this.hinhAnh = hinhAnh;
        this.diaDiem = diaDiem;
        this.loaiXe = loaiXe;
    }


    //getter and setter


    public int getMaDon() {
        return maDon;
    }

    public void setMaDon(int maDon) {
        this.maDon = maDon;
    }

    public String getHangXe() {
        return hangXe;
    }

    public void setHangXe(String hangXe) {
        this.hangXe = hangXe;
    }

    public double getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(double giaThue) {
        this.giaThue = giaThue;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }
}
