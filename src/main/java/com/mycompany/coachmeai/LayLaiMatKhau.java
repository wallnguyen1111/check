/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.coachmeai;

/**
 *
 * @author Do Tuong Minh
 */
import com.mycompany.DAO.DatabaseConnection;
import com.mycompany.DAO.GuiOTP;
import com.mycompany.entity.OTPObject;
import static com.mycompany.coachmeai.LayLaiMatKhau.DatabaseHelper.getConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

public class LayLaiMatKhau extends javax.swing.JFrame {

    private JTextField txtEmail, txtOTP;
    private JButton btnGui, btnXacNhan;
    private OTPObject otpObject; // Lưu OTP đã gửi để kiểm tra

    // noi lay ra otp de xac nhan
    public static boolean verifyOTP(OTPObject otpObject, String otpNhap) {
        return otpObject != null && otpObject.getOtp().equals(otpNhap);
    }

    public class DatabaseHelper {

        private static final String URL = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";

        // Phương thức lấy kết nối CSDL
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL);
        }

        // kiem tra email c o trong db hay ko
        public static boolean emailExists(String email) {
            String query = "SELECT 1 FROM Tai_Khoan WHERE Email = ?";
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                return rs.next();  // Nếu có dữ liệu thì email tồn tại
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;  // Nếu có lỗi hoặc không tìm thấy email
        }
    }

    public LayLaiMatKhau() {

        setTitle("Khôi Phục Mật Khẩu");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(10, 40, 80));

        ImageIcon robotIcon = new ImageIcon("C:\\Users\\Do Tuong Minh\\Desktop\\hihi\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\anhnendn.jpg");
        JLabel lblRobot = new JLabel(robotIcon);
        lblRobot.setBounds(30, 50, 180, 180);
        panel.add(lblRobot);

        JLabel lblTitle = new JLabel("KHÔI PHỤC", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(280, 20, 250, 30);
        panel.add(lblTitle);

        JLabel lblEmail = new JLabel("* Email");
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setBounds(280, 70, 150, 25);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(280, 90, 250, 30);
        panel.add(txtEmail);

        btnGui = new JButton("GỬI");
        btnGui.setBounds(280, 130, 250, 35);
        panel.add(btnGui);

        JLabel lblotp = new JLabel("* Nhập Mã OTP");
        lblotp.setForeground(Color.WHITE);
        lblotp.setBounds(280, 130, 150, 25);
        lblotp.setVisible(false);
        panel.add(lblotp);

        txtOTP = new JTextField();
        txtOTP.setBounds(280, 150, 250, 30);
        txtOTP.setVisible(false);
        panel.add(txtOTP);

        btnXacNhan = new JButton("XÁC NHẬN");
        btnXacNhan.setBounds(280, 220, 250, 35);
        btnXacNhan.setVisible(false);
        panel.add(btnXacNhan);

        btnGui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();

                // Kiểm tra email hợp lệ
                if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập email hợp lệ!");
                    return;
                }

                // Kiểm tra xem email có tồn tại trong hệ thống không
                if (!DatabaseHelper.emailExists(email)) {
                    JOptionPane.showMessageDialog(null, "Email không tồn tại trong hệ thống!");
                    return;
                }

                // Tạo kết nối CỤC BỘ trong sự kiện
                try (Connection conn = DatabaseConnection.getConnection()) {
                    // Kiểm tra số lần gửi OTP (dùng email thay vì userId)
                    if (!GuiOTP.canSendOTP(email, conn)) {
                        JOptionPane.showMessageDialog(null, "Bạn đã gửi quá 5 lần trong 24 giờ qua. Không thể gửi thêm OTP.");
                        return;
                    }

                    // Gửi OTP và nhận đối tượng chứa OTP
                    otpObject = GuiOTP.sendOTP(email, conn); // Lưu OTP

                    if (otpObject.isSent()) {
                        // Cập nhật giao diện
                        txtOTP.setVisible(true);
                        lblotp.setVisible(true);
                        btnXacNhan.setVisible(true);
                        btnGui.setVisible(false);  // Ẩn nút gửi
                    } else {
                        JOptionPane.showMessageDialog(null, "Gửi OTP thất bại. Hãy thử lại!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi hệ thống! Vui lòng thử lại sau.");
                }
            }
        });

        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String otpNhapVao = txtOTP.getText();

                if (otpObject != null && verifyOTP(otpObject, otpNhapVao)) {  // Kiểm tra OTP
                    JOptionPane.showMessageDialog(null, "Xác nhận thành công! Chuyển sang trang mới.");
                    new LayLaiMK(txtEmail.getText()).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "OTP không chính xác!");
                }
            }
        });

        add(panel);

        setVisible(
                true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LayLaiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LayLaiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LayLaiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LayLaiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LayLaiMatKhau().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
