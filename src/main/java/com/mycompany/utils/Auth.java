/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.utils;

import com.mycompany.entity.TaiKhoan;

/**
 *
 * @author Do Tuong Minh
 */
public class Auth {

    private static TaiKhoan user = null;

    public static void login(TaiKhoan taiKhoan) {
        user = taiKhoan;
    }

    public static void logout() {
        user = null;
    }

    public static boolean isLogin() {
        return user != null;
    }

    public static boolean isAdmin() {
        return isLogin() && "Quản trị viên".equals(user.getRole());
    }

    public static boolean isHocVien() {
        return isLogin() && "Học viên".equals(user.getRole());
    }

    public static boolean isPhuHuynh() {
        return isLogin() && "Phụ huynh".equals(user.getRole());
    }

    public static TaiKhoan getUser() {
        return user;
    }

}
