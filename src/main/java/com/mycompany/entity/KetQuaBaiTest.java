/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entity;

/**
 *
 * @author Do Tuong Minh
 */
public class KetQuaBaiTest {

    private String id;
    private String idHocVien;
    private String diem;
    private String thoiGianLamBai;

    public KetQuaBaiTest() {
    }

    public KetQuaBaiTest(String id, String idHocVien, String diem, String thoiGianLamBai) {
        this.id = id;
        this.idHocVien = idHocVien;
        this.diem = diem;
        this.thoiGianLamBai = thoiGianLamBai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdHocVien() {
        return idHocVien;
    }

    public void setIdHocVien(String idHocVien) {
        this.idHocVien = idHocVien;
    }

    public String getDiem() {
        return diem;
    }

    public void setDiem(String diem) {
        this.diem = diem;
    }

    public String getThoiGianLamBai() {
        return thoiGianLamBai;
    }

    public void setThoiGianLamBai(String thoiGianLamBai) {
        this.thoiGianLamBai = thoiGianLamBai;
    }

}
