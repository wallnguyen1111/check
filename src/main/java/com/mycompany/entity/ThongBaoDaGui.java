/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entity;

/**
 *
 * @author Do Tuong Minh
 */
public class ThongBaoDaGui {

    private String id;
    private String tieuDe;
    private String noiDung;
    private String idNguoiGui;
    private String thoiGianGui;

    public ThongBaoDaGui() {
    }

    public ThongBaoDaGui(String id, String tieuDe, String noiDung, String idNguoiGui, String thoiGianGui) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.idNguoiGui = idNguoiGui;
        this.thoiGianGui = thoiGianGui;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIdNguoiGui() {
        return idNguoiGui;
    }

    public void setIdNguoiGui(String idNguoiGui) {
        this.idNguoiGui = idNguoiGui;
    }

    public String getThoiGianGui() {
        return thoiGianGui;
    }

    public void setThoiGianGui(String thoiGianGui) {
        this.thoiGianGui = thoiGianGui;
    }

}
