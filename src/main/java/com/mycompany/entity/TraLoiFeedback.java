/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entity;

/**
 *
 * @author Do Tuong Minh
 */
public class TraLoiFeedback {

    private String id;
    private String idFeedback;
    private String noiDungTraLoi;
    private String thoiGianTraLoi;
    private String idAdmin;

    public TraLoiFeedback() {
    }

    public TraLoiFeedback(String id, String idFeedback, String noiDungTraLoi, String thoiGianTraLoi, String idAdmin) {
        this.id = id;
        this.idFeedback = idFeedback;
        this.noiDungTraLoi = noiDungTraLoi;
        this.thoiGianTraLoi = thoiGianTraLoi;
        this.idAdmin = idAdmin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(String idFeedback) {
        this.idFeedback = idFeedback;
    }

    public String getNoiDungTraLoi() {
        return noiDungTraLoi;
    }

    public void setNoiDungTraLoi(String noiDungTraLoi) {
        this.noiDungTraLoi = noiDungTraLoi;
    }

    public String getThoiGianTraLoi() {
        return thoiGianTraLoi;
    }

    public void setThoiGianTraLoi(String thoiGianTraLoi) {
        this.thoiGianTraLoi = thoiGianTraLoi;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }
    
    
    
    
}

