/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.coachmeai;

import com.mycompany.DAO.Dao;
import com.mycompany.entity.TaiKhoan;
import com.mycompany.utils.Auth;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Do Tuong Minh
 */
public class DangNhap extends javax.swing.JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnPassword, btnDangKy;
    static String Url = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";
    private Dao taiKhoanDAO = new Dao();

    private boolean validateInput(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (username.length() < 5 || password.length() < 5) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập phải từ 5 ký tự, mật khẩu từ 6 ký tự trở lên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (username.contains(" ") || password.contains(" ")) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập và mật khẩu không được chứa khoảng trắng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void handleLogin() {
        String input = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (input.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            return;
        }

        TaiKhoan user = taiKhoanDAO.findByUsernameOrEmail(input);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại!");
        } else if (!user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(this, "Sai mật khẩu!");
        } else if ("Khóa".equalsIgnoreCase(user.getTrang_Thai())) {
            JOptionPane.showMessageDialog(this, "Tài khoản của bạn đã bị khóa!");
        } else {
            Auth.login(user);
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            dispose();

            if (Auth.isAdmin()) {
                new trangchu("quantrivien").setVisible(true);
            } else if (Auth.isHocVien()) {
                new trangchu("hocvien").setVisible(true);
            } else if (Auth.isPhuHuynh()) {
                new trangchu("phuhuynh").setVisible(true);
            }
        }
    }

    public DangNhap() {
        setTitle("Đăng Nhập");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // bia
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(10, 40, 80));

        //anh
        ImageIcon robotIcon = new ImageIcon("D:\\coachmeai\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\anhnendn.jpg");
        JLabel lblRobot = new JLabel(robotIcon);
        lblRobot.setBounds(30, 50, 180, 180);
        panel.add(lblRobot);

        // tieu de
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(280, 20, 250, 30);
        panel.add(lblTitle);

        // ten tk
        JLabel lblUsername = new JLabel("* Tên tài khoản");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setBounds(280, 70, 150, 25);
        panel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(280, 90, 250, 30);
        panel.add(txtUsername);

        // MK
        JLabel lblPassword = new JLabel("* Mật khẩu");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(280, 130, 150, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(280, 150, 250, 30);
        panel.add(txtPassword);

        // nut dang nhap
        btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setBounds(280, 190, 250, 35);
        panel.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            if (validateInput(username, password)) {
                handleLogin();
            }
        });

        // Lay lai mk
        btnPassword = new JButton("Tại đây");

        btnPassword.setBounds(
                370, 240, 70, 20);
        btnPassword.setBorderPainted(
                false);
        btnPassword.setContentAreaFilled(
                false);
        btnPassword.setForeground(Color.CYAN);

        panel.add(btnPassword);

        JLabel lblForgot = new JLabel("Lấy lại mật khẩu?");

        lblForgot.setForeground(Color.WHITE);

        lblForgot.setBounds(
                280, 240, 150, 20);
        panel.add(lblForgot);

        // nut dang ky
        btnDangKy = new JButton("Đăng ký");

        btnDangKy.setBounds(
                385, 260, 80, 20);
        btnDangKy.setBorderPainted(
                false);
        btnDangKy.setContentAreaFilled(
                false);
        btnDangKy.setForeground(Color.GREEN);

        btnDangKy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewDangKy().setVisible(true);
            }
        });

        panel.add(btnDangKy);

        btnPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LayLaiMatKhau khau = new LayLaiMatKhau();
                khau.setVisible(true);
                dispose();
            }
        });

        JLabel lblHaveAccount = new JLabel("Bạn chưa có tài khoản?");

        lblHaveAccount.setForeground(Color.WHITE);

        lblHaveAccount.setBounds(
                280, 260, 150, 20);
        panel.add(lblHaveAccount);

        // them panel vao frame
        add(panel);

        setVisible(
                true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DangNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
