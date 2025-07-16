/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author vipgl
 */
public class NhiemVu {

    private int idNhiemVu;
    private int idTaiKhoan;
    private int idMonHoc;
    private String tieuDe;
    private String noiDung;
    private String trangThai;
    private String ghiChu;
    private Date thoiGianBatDau;
    private Date thoiGianKetThuc;

    // Constructors
    public NhiemVu() {
    }

    public NhiemVu(int idNhiemVu, int idTaiKhoan, int idMonHoc, String tieuDe,
            String noiDung, String trangThai, String ghiChu,
            Date thoiGianBatDau, Date thoiGianKetThuc) {
        this.idNhiemVu = idNhiemVu;
        this.idTaiKhoan = idTaiKhoan;
        this.idMonHoc = idMonHoc;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    // Getter & Setter
    public int getIdNhiemVu() {
        return idNhiemVu;
    }

    public void setIdNhiemVu(int idNhiemVu) {
        this.idNhiemVu = idNhiemVu;
    }

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public int getIdMonHoc() {
        return idMonHoc;
    }

    public void setIdMonHoc(int idMonHoc) {
        this.idMonHoc = idMonHoc;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Date getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(Date thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public Date getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Date thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }
}

