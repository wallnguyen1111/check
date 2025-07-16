/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entity;

public class CauHoiBaiTest {

    private String id;
    private String idBaiTest;
    private String dapAnDung;
    private String dapAnCauSai;
    private String trangThaiDapAn;

    public CauHoiBaiTest() {
    }

    public CauHoiBaiTest(String id, String idBaiTest, String dapAnDung, String dapAnCauSai, String trangThaiDapAn) {
        this.id = id;
        this.idBaiTest = idBaiTest;
        this.dapAnDung = dapAnDung;
        this.dapAnCauSai = dapAnCauSai;
        this.trangThaiDapAn = trangThaiDapAn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBaiTest() {
        return idBaiTest;
    }

    public void setIdBaiTest(String idBaiTest) {
        this.idBaiTest = idBaiTest;
    }

    public String getDapAnDung() {
        return dapAnDung;
    }

    public void setDapAnDung(String dapAnDung) {
        this.dapAnDung = dapAnDung;
    }

    public String getDapAnCauSai() {
        return dapAnCauSai;
    }

    public void setDapAnCauSai(String dapAnCauSai) {
        this.dapAnCauSai = dapAnCauSai;
    }

    public String getTrangThaiDapAn() {
        return trangThaiDapAn;
    }

    public void setTrangThaiDapAn(String trangThaiDapAn) {
        this.trangThaiDapAn = trangThaiDapAn;
    }

}
