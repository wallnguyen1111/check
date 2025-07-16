/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entity;

import java.sql.Date;

/**
 *
 * @author nguyenvandan
 */
public class TaiKhoan {

    private int ID_Tai_Khoan;
    private String Username, Password, Ho_Va_Ten, So_Dien_Thoai, Email, Role, Trang_Thai, Avatar;
    private Date Ngay_Dang_Ki;

    public TaiKhoan() {
    }

    public TaiKhoan(int ID_Tai_Khoan, String Username, String Password, String Ho_Va_Ten, String So_Dien_Thoai, String Email, String Role, String Trang_Thai, String Avatar, Date Ngay_Dang_Ki) {
        this.ID_Tai_Khoan = ID_Tai_Khoan;
        this.Username = Username;
        this.Password = Password;
        this.Ho_Va_Ten = Ho_Va_Ten;
        this.So_Dien_Thoai = So_Dien_Thoai;
        this.Email = Email;
        this.Role = Role;
        this.Trang_Thai = Trang_Thai;
        this.Avatar = Avatar;
        this.Ngay_Dang_Ki = Ngay_Dang_Ki;
    }

    public int getID_Tai_Khoan() {
        return ID_Tai_Khoan;
    }

    public void setID_Tai_Khoan(int ID_Tai_Khoan) {
        this.ID_Tai_Khoan = ID_Tai_Khoan;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getHo_Va_Ten() {
        return Ho_Va_Ten;
    }

    public void setHo_Va_Ten(String Ho_Va_Ten) {
        this.Ho_Va_Ten = Ho_Va_Ten;
    }

    public String getSo_Dien_Thoai() {
        return So_Dien_Thoai;
    }

    public void setSo_Dien_Thoai(String So_Dien_Thoai) {
        this.So_Dien_Thoai = So_Dien_Thoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getTrang_Thai() {
        return Trang_Thai;
    }

    public void setTrang_Thai(String Trang_Thai) {
        this.Trang_Thai = Trang_Thai;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public Date getNgay_Dang_Ki() {
        return Ngay_Dang_Ki;
    }

    public void setNgay_Dang_Ki(Date Ngay_Dang_Ki) {
        this.Ngay_Dang_Ki = Ngay_Dang_Ki;
    }

}
