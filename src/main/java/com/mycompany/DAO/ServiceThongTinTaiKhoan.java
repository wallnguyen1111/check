/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DAO;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.entity.TaiKhoan;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author nguyenvandan
 */
public class ServiceThongTinTaiKhoan {

    static String FillThongTin = "select * from Tai_khoan where ID_Tai_Khoan = ?";
    static String CheckKetNoiSinhVien = "select * from Phu_Huynh_Hoc_Sinh where ID_Tai_Khoan_Hoc_Sinh = ? and Tinh_Trang_Ket_Noi = N'Đã kết nối'";
    static String CheckKetNoiPhuHuynh = "select * from Phu_Huynh_Hoc_Sinh where ID_Tai_Khoan_Phu_Huynh = ? and Tinh_Trang_Ket_Noi = N'Đã kết nối'";
//    static String CheckMatKhau = "select * from Tai_khoan where ID_Tai_Khoan = ? and Password = ?";
    static String DoiMatKhau = "update Tai_khoan set Password = ? where ID_Tai_Khoan = ?";
    static String UpdateAcc = "update Tai_Khoan set Username = ?, Ho_Va_Ten = ?, Avatar = ?, So_Dien_Thoai = ? where ID_Tai_Khoan = ?";
    static String LayThongTinLienKetChoPhuHuynh = " Select * from Phu_Huynh_Hoc_Sinh where ID_Tai_Khoan_Phu_Huynh = ? order by Tinh_Trang_Ket_Noi desc";
    static String XoaLienKet = "delete from Phu_Huynh_Hoc_Sinh where ID_Tai_Khoan_Phu_Huynh = ? and ID_Tai_Khoan_Hoc_Sinh = ?";
    static String UpdateEmail = "update Tai_Khoan set Email = ? where ID_Tai_Khoan = ?";

    public static boolean Upemail(String Email, int ID) {
        List<Object> params = new ArrayList<>();
        params.add(Email);
        params.add(ID);
        try {
            boolean rs = (boolean) Service1forAll.executeQuery(UpdateEmail, params);
            if (rs == true) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean XoaLienKet(int idPhuHuynh, int idHocVien) {
        List<Object> params = new ArrayList<>();
        params.add(idPhuHuynh);
        params.add(idHocVien);
        try {
            boolean rs = (boolean) Service1forAll.executeQuery(XoaLienKet, params);
            if (rs == true) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Object[]> layThongTinLienKetPHHV(int ID) {
        List<Object[]> danhSachLienKet = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        params.add(ID);
        try {
            ResultSet rs = (ResultSet) Service1forAll.executeQuery(LayThongTinLienKetChoPhuHuynh, params);
            while (rs != null && rs.next()) {
                Object[] row = {
                    rs.getInt("ID_Tai_Khoan_Phu_Huynh"),
                    rs.getInt("ID_Tai_Khoan_Hoc_Sinh"),
                    rs.getString("Tinh_Trang_Ket_Noi")
                };
                danhSachLienKet.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSachLienKet;
    }

    public static boolean DoiThongTin(String User, String HoTen, String Avatar, String Sdt, int ID) {
        List<Object> params = new ArrayList<>();
        params.add(User);
        params.add(HoTen);
        params.add(Avatar);
        params.add(Sdt);
        params.add(ID);
        try {
            boolean rs = (boolean) Service1forAll.executeQuery(UpdateAcc, params);
            if (rs == true) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean DoiPass(String Pass, int ID) {
        List<Object> params = new ArrayList<>();
        params.add(Pass);
        params.add(ID);
        try {
            boolean rs = (boolean) Service1forAll.executeQuery(DoiMatKhau, params);
            if (rs == true) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static boolean CheckMatKhau(int ID, String Pass){
//        ModelTaiKhoan taiKhoan = new ModelTaiKhoan();
//        List<Object> params = new ArrayList<>();
//        params.add(ID);
//        params.add(Pass);
//        try {
//            ResultSet rs = (ResultSet) Service1forAll.executeQuery(CheckMatKhau, params);
//            while (rs.next()) {                
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    public static boolean CheckKetNoiHocVien(int ID) {
        List<Object> params = new ArrayList<>();
        params.add(ID);
        try {
            ResultSet rs = (ResultSet) Service1forAll.executeQuery(CheckKetNoiSinhVien, params);
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean CheckKetNoiPhuHuynh(int ID) {
        List<Object> params = new ArrayList<>();
        params.add(ID);
        try {
            ResultSet rs = (ResultSet) Service1forAll.executeQuery(CheckKetNoiPhuHuynh, params);
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static TaiKhoan ThongTinTaiKhoan(int ID) {
        TaiKhoan taiKhoan = new TaiKhoan();
        List<Object> params = new ArrayList<>();
        params.add(ID);
        try {
            ResultSet rs = (ResultSet) Service1forAll.executeQuery(FillThongTin, params);
            while (rs.next()) {
                taiKhoan.setID_Tai_Khoan(ID);
                taiKhoan.setUsername(rs.getString("Username"));
                taiKhoan.setPassword(rs.getString("Password"));
                taiKhoan.setHo_Va_Ten(rs.getString("Ho_Va_Ten"));
                taiKhoan.setAvatar(rs.getString("Avatar"));
                taiKhoan.setSo_Dien_Thoai(rs.getString("So_Dien_Thoai"));
                taiKhoan.setEmail(rs.getString("Email"));
                taiKhoan.setNgay_Dang_Ki(rs.getDate("Ngay_Dang_Ki"));
                taiKhoan.setRole(rs.getString("Role"));
                taiKhoan.setTrang_Thai(rs.getString("Trang_Thai"));
            }
            return taiKhoan;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taiKhoan;
    }

}
