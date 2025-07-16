/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entity;
import java.sql.Date;
/**
 *
 * @author hungb
 */
public class BaiViet {

    private int id;
    private int idAdmin;
    private String tieuDe;
    private String noiDung;
    private Date ngayDang;
    private String trangThai;
    private String hinhAnh;

    public BaiViet() {
    }

    public BaiViet(int id, int idAdmin, String tieuDe, String noiDung, Date ngayDang, String trangThai, String hinhAnh) {
        this.id = id;
        this.idAdmin = idAdmin;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ngayDang = ngayDang;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
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

    public Date  getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(Date  ngayDang) {
        this.ngayDang = ngayDang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

}