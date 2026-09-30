package com.example.da_app.model;

public class DanhGiaCuaToi {

    private int maDiaDiem;
    private String tenDiaDiem;
    private String diaChi;
    private String hinhAnh;
    private int soSao;

    public DanhGiaCuaToi() {
    }

    public DanhGiaCuaToi(
            int maDiaDiem,
            String tenDiaDiem,
            String diaChi,
            String hinhAnh,
            int soSao
    ) {
        this.maDiaDiem = maDiaDiem;
        this.tenDiaDiem = tenDiaDiem;
        this.diaChi = diaChi;
        this.hinhAnh = hinhAnh;
        this.soSao = soSao;
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

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getSoSao() {
        return soSao;
    }

    public void setSoSao(int soSao) {
        this.soSao = soSao;
    }
}