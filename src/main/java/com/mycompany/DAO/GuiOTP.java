/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DAO;

/**
 *
 * @author Do Tuong Minh
 */
import com.mycompany.entity.OTPObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;

public class GuiOTP {

    private static final String EMAIL_GUI = "cma.coachmeai@gmail.com"; // Gmail gửi OTP
    private static final String PASSWORD = "dbik psbd isur eigb"; // Mật khẩu ứng dụng
    private static final int MAX_ATTEMPTS = 5; // toi da 5 lan gui ve gamil

    public static String generateOTP() {
        Random rand = new Random();
        return String.format("%06d", rand.nextInt(1000000));
    }

    //Kiêm tra số lần trong 24h qua
    public static boolean canSendOTP(String email, Connection conn) throws Exception {
        String query = "SELECT COUNT(*) FROM Lich_Su_Truy_Cap WHERE Email = ? AND Loai_Hanh_Dong = 'Gui_Ma' "
                + "AND Thoi_Gian_Truy_Cap >= DATEADD(DAY, -1, GETDATE())";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email); // Gán Email vào câu lệnh SQL
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) >= MAX_ATTEMPTS) {
                return false; // Nếu số lần gửi OTP >= 5, chặn gửi tiếp
            }
        }
        return true; // Nếu chưa gửi quá 5 lần, cho phép gửi tiếp
    }

    //Khi gửi OTP thành công, ghi nhận thêm một lần gửi vào bảng Lich_Su_Truy_Cap.
    //luu tyhong tin gui vào db
    public static void logOTPAttempt(String email, Connection conn) throws Exception {
        String insertQuery = "INSERT INTO Lich_Su_Truy_Cap (Email, Thoi_Gian_Truy_Cap, Loai_Hanh_Dong) VALUES (?, GETDATE(), 'Gui_Ma')";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, email); // Gán Email của người gửi OTP
            pstmt.executeUpdate(); // Thực thi câu lệnh INSERT vào database
        }
    }

    public static OTPObject sendOTP(String emailNhan, Connection conn) {
        try {
            if (!canSendOTP(emailNhan, conn)) {
                System.out.println("Bạn đã gửi quá 5 lần trong 24 giờ qua. Không thể gửi thêm mã xác minh.");
                return new OTPObject(null, false);
                
            }

            String otp = generateOTP();
            String subject = "Ma Xac Nhan Tai Khoan";
            String message = "Mã của bạn là: " + otp + "\nVui lòng không chia sẻ với ai nhé 😡😡.";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_GUI, PASSWORD);
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(EMAIL_GUI));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailNhan));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);

            logOTPAttempt(emailNhan, conn);
            //System.out.println("Gửi thành công OTP: " + otp);

            return new OTPObject(otp, true);  // Trả về đối tượng OTPObject

        } catch (Exception e) {
            e.printStackTrace();
            return new OTPObject(null, false);
        }
    }

}
