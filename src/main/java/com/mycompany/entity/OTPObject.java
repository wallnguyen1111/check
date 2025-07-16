/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entity;

/**
 *
 * @author Do Tuong Minh
 */
public class OTPObject {

    // moi toanh de luu otp va ma gui den
    private String otp;
    private boolean isSent;

    public OTPObject(String otp, boolean isSent) {
        this.otp = otp;
        this.isSent = isSent;
    }

    public String getOtp() {
        return otp;
    }

    public boolean isSent() {
        return isSent;
    }
}
