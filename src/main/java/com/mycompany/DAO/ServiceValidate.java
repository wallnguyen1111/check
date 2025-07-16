/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguyenvandan
 */
public class ServiceValidate {

    static String CheckTonTaiEmail = "select * from Tai_khoan where Email = ?";
    static String CheckTonTaiUserName = "select * from Tai_khoan where Username = ?";

    public static String CheckValidateUserName(String UserName) {
        String eror = "";

        if (UserName == null || UserName.isBlank()) {
            eror = "Vui lòng nhập tên tài khoản";
        } else if (UserName.contains(" ")) {
            eror = "Tên tài khoản không được chứa khoảng trắng";
        } else if (UserName.length() < 3) {
            eror = "Tên tài khoản phải có ít nhất 3 ký tự";
        } else if (UserName.length() > 20) {
            eror = "Tên tài khoản không được quá 20 ký tự";
        } else {
            List<Object> paramsUserName = new ArrayList<>();
            paramsUserName.add(UserName);
            try {
                ResultSet rs = (ResultSet) Service1forAll.executeQuery(CheckTonTaiUserName, paramsUserName);
                if (rs.next()) {
                    eror = "Tên tài khoản đã được sử dụng";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return eror;
    }

    public static String CheckValidatePassword(String Password) {
        String eror = "";

        if (Password == null || Password.isBlank()) {
            eror = "Vui lòng nhập mật khẩu";
        } else if (Password.contains(" ")) {
            eror = "Mật khẩu không được chứa khoảng trắng";
        } else if (Password.length() < 8 || Password.length() > 16) {
            eror = "Mật khẩu phải từ 8 đến 16 ký tự";
        }

        return eror;
    }

    public static String CheckValidateHo_Va_Ten(String Ho_Va_Ten) {
        String eror = "";
        if (Ho_Va_Ten == null || Ho_Va_Ten.isBlank()) {
            eror = "Vui lòng nhập họ và tên";
        }

        return eror;
    }

    public static String CheckValidateSo_Dien_Thoai(String So_Dien_Thoai) {
        String eror = "";
        if (So_Dien_Thoai != null && !So_Dien_Thoai.isBlank() && !So_Dien_Thoai.matches("\\d{10,11}")) {
            eror = "Số điện thoại không hợp lệ";
        }

        return eror;
    }

    public static String CheckValidateEmail(String Email) {
        String eror = "";
        if (Email == null || Email.isBlank()) {
            eror = "Vui lòng nhập Email";
        } else {
            if (!Email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                eror = "Email không đúng định dạng";
            } else {
                List<Object> paramsEmail = new ArrayList<>();
                paramsEmail.add(Email);
                try {
                    ResultSet rs = (ResultSet) Service1forAll.executeQuery(CheckTonTaiEmail, paramsEmail);
                    if (rs.next()) {
                        eror = "Email đã được đăng ký tài khoản trước đó";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return eror;
    }

    public static String CheckValidateAvartar(String Avartar) {
        String eror = "";
        if (Avartar == null || Avartar.isBlank()) {
            eror = "Vui lòng chọn ảnh";
        }

        return eror;
    }

}
