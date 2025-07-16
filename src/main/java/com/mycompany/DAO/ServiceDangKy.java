/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DAO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import javax.print.attribute.standard.SheetCollate;

/**
 *
 * @author nguyenvandan
 */
public class ServiceDangKy {

    static String CheckTonTaiEmail = "select * from Tai_khoan where Email = ?";
    static String CheckTonTaiSDT = "select * from Tai_khoan where So_Dien_Thoai = ?";
    static String CheckTonTaiUserName = "select * from Tai_khoan where Username = ?";
    static String DangKi = "insert into Tai_khoan(Username, Password, Ho_Va_Ten, So_Dien_Thoai, Email, Ngay_Dang_Ki, Role, Avatar, Trang_Thai) values (?,?,?,?,?,?,?,?,?)";
    static String NgayHienTai = "SELECT CAST(GETDATE() AS DATE) AS CurrentDate";

    public static Date LayNgayHienTai() {
        List<Object> params = new ArrayList<>();
        Date daynow;
        try {
            ResultSet rs = (ResultSet) Service1forAll.executeQuery(NgayHienTai, params);
            while (rs.next()) {
                daynow = rs.getDate("CurrentDate");
                return daynow;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean InsertTaiKhoan(String UserName, String Password, String Ho_Va_Ten, String So_Dien_Thoai, String Email, String Role, String Avartar) {
        List<Object> params = new ArrayList<>();
        params.add(UserName);
        params.add(Password);
        params.add(Ho_Va_Ten);
        params.add(So_Dien_Thoai);
        params.add(Email);
        Date Ngay_Dang_Ki = LayNgayHienTai();
        params.add(Ngay_Dang_Ki);
        params.add(Role);
        params.add(Avartar);
        params.add("Hoạt động");
        try {
            boolean rs = (boolean) Service1forAll.executeQuery(DangKi, params);
            if (rs == true) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String> Validate(String UserName, String Password, String Ho_Va_Ten, String So_Dien_Thoai, String Email, String Avartar) {
        List<String> errors = new ArrayList<>();

        List<String> missingFields = new ArrayList<>();
        if (UserName == null || UserName.isBlank()) {
            missingFields.add("nhập tên tài khoản");
        }
        if (Password == null || Password.isBlank()) {
            missingFields.add("nhập mật khẩu");
        }
        if (Ho_Va_Ten == null || Ho_Va_Ten.isBlank()) {
            missingFields.add("nhập họ và tên");
        }
        if (Email == null || Email.isBlank()) {
            missingFields.add("nhập email");
        }
        if (Avartar == null || Avartar.isBlank()) {
            missingFields.add("chọn ảnh đại diện");
        }

        if (!missingFields.isEmpty()) {
            if (missingFields.size() > 1) {
                errors.add("Vui lòng nhập đầy đủ thông tin");
                return errors;
            } else {
                errors.add("Vui lòng " + String.join(", ", missingFields));
                return errors;
            }
        }

        if (UserName.contains(" ")) {
            errors.add("Tên tài khoản không được chứa khoảng trắng");
        }

        if (Password.length() < 8 || Password.length() > 16) {
            errors.add("Mật khẩu phải từ 8 đến 16 ký tự");
        }

        if (So_Dien_Thoai != null && !So_Dien_Thoai.isBlank() && !So_Dien_Thoai.matches("\\d{10,11}")) {
            errors.add("Số điện thoại không hợp lệ");
        }

        if (!Email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            errors.add("Email không đúng định dạng");
        }

        List<Object> paramsEmail = new ArrayList<>();
        paramsEmail.add(Email);
        try {
            ResultSet rs = (ResultSet) Service1forAll.executeQuery(CheckTonTaiEmail, paramsEmail);
            if (rs.next()) {
                errors.add("Email đã được đăng ký tài khoản trước đó");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (So_Dien_Thoai != null && !So_Dien_Thoai.isBlank()) {
            List<Object> paramsSDT = new ArrayList<>();
            paramsSDT.add(So_Dien_Thoai);
            try {
                ResultSet rs = (ResultSet) Service1forAll.executeQuery(CheckTonTaiSDT, paramsSDT);
                if (rs.next()) {
                    errors.add("SDT đã được đăng ký tài khoản trước đó");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Object> paramsUserName = new ArrayList<>();
        paramsUserName.add(UserName);
        try {
            ResultSet rs = (ResultSet) Service1forAll.executeQuery(CheckTonTaiUserName, paramsUserName);
            if (rs.next()) {
                errors.add("Tên tài khoản đã được sử dụng");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return errors;
    }

}
