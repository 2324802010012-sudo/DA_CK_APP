package com.example.da_app.model;

public class DiaDiem {

    private int maDiaDiem;
    private String tenDiaDiem;
    private String diaChi;
    private String khuVuc;
    private String danhMuc;
    private double danhGia;
    private String moTa;
    private String thoiDiemThichHop;
    private String hinhAnh;

    // Constructor rỗng
    public DiaDiem() {
    }

    // Constructor đầy đủ
    public DiaDiem(
            int maDiaDiem,
            String tenDiaDiem,
            String diaChi,
            String khuVuc,
            String danhMuc,
            double danhGia,
            String moTa,
            String thoiDiemThichHop,
            String hinhAnh
    ) {
        this.maDiaDiem = maDiaDiem;
        this.tenDiaDiem = tenDiaDiem;
        this.diaChi = diaChi;
        this.khuVuc = khuVuc;
        this.danhMuc = danhMuc;
        this.danhGia = danhGia;
        this.moTa = moTa;
        this.thoiDiemThichHop = thoiDiemThichHop;
        this.hinhAnh = hinhAnh;
    }

    // Constructor dùng khi thêm mới
    // Không cần maDiaDiem vì SQLite tự tăng
    public DiaDiem(
            String tenDiaDiem,
            String diaChi,
            String khuVuc,
            String danhMuc,
            double danhGia,
            String moTa,
            String thoiDiemThichHop,
            String hinhAnh
    ) {
        this.tenDiaDiem = tenDiaDiem;
        this.diaChi = diaChi;
        this.khuVuc = khuVuc;
        this.danhMuc = danhMuc;
        this.danhGia = danhGia;
        this.moTa = moTa;
        this.thoiDiemThichHop = thoiDiemThichHop;
        this.hinhAnh = hinhAnh;
    }

    public int getMaDiaDiem() {
        return maDiaDiem;
    }

    public void setMaDiaDiem(int maDiaDiem) {
        this.maDiaDiem = maDiaDiem;
    }

    public String getTenDiaDiem() {
        return tenDiaDiem;
    }

    public void setTenDiaDiem(String tenDiaDiem) {
        this.tenDiaDiem = tenDiaDiem;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(String khuVuc) {
        this.khuVuc = khuVuc;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public double getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(double danhGia) {
        this.danhGia = danhGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getThoiDiemThichHop() {
        return thoiDiemThichHop;
    }

    public void setThoiDiemThichHop(String thoiDiemThichHop) {
        this.thoiDiemThichHop = thoiDiemThichHop;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}