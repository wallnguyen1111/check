/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entity;

/**
 *
 * @author Do Tuong Minh
 */
public class Feedback {

    private String id;
    private String idNguoiGui;
    private String noiDung;
    private String ngayGui;

    public Feedback() {
    }

    public Feedback(String id, String idNguoiGui, String noiDung, String ngayGui) {
        this.id = id;
        this.idNguoiGui = idNguoiGui;
        this.noiDung = noiDung;
        this.ngayGui = ngayGui;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdNguoiGui() {
        return idNguoiGui;
    }

    public void setIdNguoiGui(String idNguoiGui) {
        this.idNguoiGui = idNguoiGui;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayGui() {
        return ngayGui;
    }

    public void setNgayGui(String ngayGui) {
        this.ngayGui = ngayGui;
    }

}
