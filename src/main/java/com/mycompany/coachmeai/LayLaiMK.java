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
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class LayLaiMK extends javax.swing.JFrame {

    private JTextField txtPassword, txtConfirmPassword;
    private JButton btnXacNhan;
    private static String email = "";
    static String Url = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";

    public LayLaiMK(String email) {
        this.email = email;

        setTitle("Đặt Mật Khẩu Mới");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(10, 40, 80));

        JLabel lblTitle = new JLabel("ĐẶT MẬT KHẨU MỚI", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(170, 20, 250, 30);
        panel.add(lblTitle);

        JLabel lblPassword = new JLabel("* Mật khẩu mới");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(140, 70, 150, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(140, 90, 300, 30);
        panel.add(txtPassword);

        JLabel lblConfirmPassword = new JLabel("* Xác nhận mật khẩu");
        lblConfirmPassword.setForeground(Color.WHITE);
        lblConfirmPassword.setBounds(140, 130, 150, 25);
        panel.add(lblConfirmPassword);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(140, 150, 300, 30);
        panel.add(txtConfirmPassword);

        btnXacNhan = new JButton("XÁC NHẬN");
        btnXacNhan.setBounds(140, 190, 300, 35);
        panel.add(btnXacNhan);

        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = txtPassword.getText();
                String confirmPassword = txtConfirmPassword.getText();

                if (password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu xác nhận không khớp!");
                } else {
                    if (updatePassword(email, password)) {
                        JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!");
                        new DangNhap();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Lỗi! Không thể đổi mật khẩu.");
                    }
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    private boolean updatePassword(String email, String newPassword) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE Tai_Khoan SET [Password]= ? WHERE Email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            int rows = stmt.executeUpdate();
            conn.close();
            return rows > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
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
            java.util.logging.Logger.getLogger(LayLaiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LayLaiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LayLaiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LayLaiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LayLaiMK(email).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
