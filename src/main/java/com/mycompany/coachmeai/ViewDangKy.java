/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.coachmeai;

import com.mycompany.DAO.DatabaseConnection;
import com.mycompany.DAO.GuiOTP;
import com.mycompany.DAO.ServiceDangKy;
import com.mycompany.DAO.ServiceValidate;
import com.mycompany.entity.OTPObject;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewDangKy extends javax.swing.JFrame {

    public JTextField txtHoTen, txtTaiKhoan, txtEmail, txtSoDienThoai;
    public JPasswordField txtMatKhau;
    public JRadioButton rdoHocVien, rdoPhuHuynh;
    public JButton btnDangKy, btnChonAnh;
    public JPanel panelAnh, panelLogo;
    public String Anh;
    private OTPObject otpObject;

    /**
     * Creates new form ViewDangKy
     */
    public ViewDangKy() {
        initComponents();
        DangKy();
    }

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

    }

    public void DangKy() {
        setTitle("Đăng Ký");
        setSize(770, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(3, 39, 87));

        panelLogo = new JPanel();
        panelLogo.setBounds(20, 20, 80, 80);
        add(panelLogo);

        ImageIcon logoIcon = new ImageIcon("D:\\coachmeai\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\COACH ME.png");
        JLabel lblLogo = new JLabel(new ImageIcon(logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        panelLogo.setLayout(new BorderLayout());
        panelLogo.add(lblLogo, BorderLayout.CENTER);

        JLabel lblTitle = new JLabel("ĐĂNG KÝ");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(320, 60, 200, 30);
        add(lblTitle);

        txtHoTen = new JTextField();
        txtTaiKhoan = new JTextField();
        txtMatKhau = new JPasswordField();
        txtEmail = new JTextField();
        txtSoDienThoai = new JTextField();

        JLabel lblHoTen = new JLabel("* Họ và tên");
        JLabel lblTaiKhoan = new JLabel("* Tên tài khoản");
        JLabel lblMatKhau = new JLabel("* Mật khẩu");
        JLabel lblEmail = new JLabel("* Email");
        JLabel lblSoDienThoai = new JLabel("  Số điện thoại");
        JLabel lblRole = new JLabel("* Role");

        JLabel lblHoTenEror = new JLabel("Eror");
        JLabel lblTaiKhoanEror = new JLabel("Eror");
        JLabel lblMatKhauEror = new JLabel("Eror");
        JLabel lblEmailEror = new JLabel("Eror");
        JLabel lblSoDienThoaiEror = new JLabel("Eror");
        JLabel lblRoleEror = new JLabel("Eror");
        JLabel lblAnhEror = new JLabel("Eror");

        lblHoTen.setForeground(Color.WHITE);
        lblHoTen.setBounds(50, 120, 150, 25);
        add(lblHoTen);
        txtHoTen.setBounds(50, 150, 350, 30);
        add(txtHoTen);
        lblHoTenEror.setForeground(Color.red);
        lblHoTenEror.setBounds(50, 175, 350, 30);
        add(lblHoTenEror);

        lblTaiKhoan.setForeground(Color.WHITE);
        lblTaiKhoan.setBounds(50, 200, 150, 25);
        add(lblTaiKhoan);
        txtTaiKhoan.setBounds(50, 230, 350, 30);
        add(txtTaiKhoan);
        lblTaiKhoanEror.setForeground(Color.red);
        lblTaiKhoanEror.setBounds(50, 255, 350, 30);
        add(lblTaiKhoanEror);

        lblMatKhau.setForeground(Color.WHITE);
        lblMatKhau.setBounds(50, 280, 150, 25);
        add(lblMatKhau);
        txtMatKhau.setBounds(50, 310, 350, 30);
        add(txtMatKhau);
        lblMatKhauEror.setForeground(Color.red);
        lblMatKhauEror.setBounds(50, 335, 350, 30);
        add(lblMatKhauEror);

        lblEmail.setForeground(Color.WHITE);
        lblEmail.setBounds(50, 360, 150, 25);
        add(lblEmail);
        txtEmail.setBounds(50, 390, 350, 30);
        add(txtEmail);
        lblEmailEror.setForeground(Color.red);
        lblEmailEror.setBounds(50, 415, 350, 30);
        add(lblEmailEror);

        lblSoDienThoai.setForeground(Color.WHITE);
        lblSoDienThoai.setBounds(50, 440, 150, 25);
        add(lblSoDienThoai);
        txtSoDienThoai.setBounds(50, 470, 350, 30);
        add(txtSoDienThoai);
        lblSoDienThoaiEror.setForeground(Color.red);
        lblSoDienThoaiEror.setBounds(50, 495, 350, 30);
        add(lblSoDienThoaiEror);

        lblRole.setForeground(Color.WHITE);
        lblRole.setBounds(50, 520, 150, 25);
        add(lblRole);

        rdoHocVien = new JRadioButton("Học Viên");
        rdoPhuHuynh = new JRadioButton("Phụ Huynh");
        ButtonGroup group = new ButtonGroup();
        group.add(rdoHocVien);
        group.add(rdoPhuHuynh);

        rdoHocVien.setBounds(50, 550, 100, 25);
        rdoPhuHuynh.setBounds(200, 550, 100, 25);
        rdoHocVien.setForeground(Color.WHITE);
        rdoPhuHuynh.setForeground(Color.WHITE);
        add(rdoHocVien);
        add(rdoPhuHuynh);
        lblRoleEror.setForeground(Color.red);
        lblRoleEror.setBounds(50, 575, 350, 30);
        add(lblRoleEror);

        panelAnh = new JPanel();
        panelAnh.setBounds(500, 140, 200, 250);
        add(panelAnh);
        lblAnhEror.setForeground(Color.red);
        lblAnhEror.setBounds(500, 385, 200, 30);
        add(lblAnhEror);

        btnChonAnh = new JButton("CHỌN ẢNH");
        btnChonAnh.setBounds(540, 420, 120, 30);
        add(btnChonAnh);

        lblHoTenEror.setVisible(false);
        lblTaiKhoanEror.setVisible(false);
        lblMatKhauEror.setVisible(false);
        lblEmailEror.setVisible(false);
        lblSoDienThoaiEror.setVisible(false);
        lblRoleEror.setVisible(false);
        lblAnhEror.setVisible(false);

        btnChonAnh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblAnhEror.setVisible(false);
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("CHON ANH", "jpg", "png", "gif"));
                int Chon = chooser.showOpenDialog(null);
                if (Chon == JFileChooser.APPROVE_OPTION) {
                    File FileOpen = chooser.getSelectedFile();
                    Anh = FileOpen.getPath();
                    ImageIcon Icon = new ImageIcon(FileOpen.getAbsolutePath());
                    int panelWidth = panelAnh.getWidth();
                    int panelHeight = panelAnh.getHeight();
                    ImageIcon Ima = new ImageIcon(Icon.getImage().getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH));
                    JLabel label = new JLabel(Ima);
                    panelAnh.removeAll();
                    panelAnh.setLayout(new BorderLayout());
                    panelAnh.add(label, BorderLayout.CENTER);
                    panelAnh.revalidate();
                    panelAnh.repaint();
                }
            }
        });

        btnDangKy = new JButton("ĐĂNG KÝ");
        btnDangKy.setBounds(300, 600, 150, 40);
        add(btnDangKy);

        btnDangKy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Ten = txtHoTen.getText();
                String UserName = txtTaiKhoan.getText();
                String Pass = String.valueOf(txtMatKhau.getPassword());
                String email = txtEmail.getText();
                String SDT = txtSoDienThoai.getText();
                String Role = "";

                boolean Check = true;
                String CheckTentaikhoan = ServiceValidate.CheckValidateUserName(UserName);
                if (!CheckTentaikhoan.equals("")) {
                    lblTaiKhoanEror.setText(CheckTentaikhoan);
                    lblTaiKhoanEror.setVisible(true);
                    Check = false;
                }

                String CheckPass = ServiceValidate.CheckValidatePassword(Pass);
                if (!CheckPass.equals("")) {
                    lblMatKhauEror.setText(CheckPass);
                    lblMatKhauEror.setVisible(true);
                    Check = false;
                }

                String CheckHoTen = ServiceValidate.CheckValidateHo_Va_Ten(Ten);
                if (!CheckHoTen.equals("")) {
                    lblHoTenEror.setText(CheckHoTen);
                    lblHoTenEror.setVisible(true);
                    Check = false;
                }

                String CheckEmail = ServiceValidate.CheckValidateEmail(email);
                if (!CheckEmail.equals("")) {
                    lblEmailEror.setText(CheckEmail);
                    lblEmailEror.setVisible(true);
                    Check = false;
                }

                String CheckSDT = ServiceValidate.CheckValidateSo_Dien_Thoai(SDT);
                if (!CheckSDT.equals("")) {
                    lblSoDienThoaiEror.setText(CheckSDT);
                    lblSoDienThoaiEror.setVisible(true);
                    Check = false;
                }

                String CheckAvartar = ServiceValidate.CheckValidateAvartar(Anh);
                if (!CheckAvartar.equals("")) {
                    lblAnhEror.setText(CheckAvartar);
                    lblAnhEror.setVisible(true);
                    Check = false;
                }

                if (!rdoHocVien.isSelected() && !rdoPhuHuynh.isSelected()) {
                    lblRoleEror.setText("Vui lòng chọn role của bạn");
                    lblRoleEror.setVisible(true);
                    Check = false;
                } else {
                    if (rdoHocVien.isSelected()) {
                        Role = "Học viên";
                    } else {
                        Role = "Phụ huynh";
                    }
                }

                if (Check == true) {
                    try (Connection conn = DatabaseConnection.getConnection()) {
                        // Kiểm tra số lần gửi OTP (dùng email thay vì userId)
                        if (!GuiOTP.canSendOTP(email, conn)) {
                            
                            JOptionPane.showMessageDialog(null, "Bạn đã gửi quá 5 lần trong 24 giờ qua. Không thể gửi thêm OTP.");
                            return;
                        }

                        // Gửi OTP và nhận đối tượng chứa OTP
                        otpObject = GuiOTP.sendOTP(email, conn); // Lưu OTP

                        if (otpObject.isSent()) {
                            FromXacMinhEmail(UserName, Pass, Ten, SDT, email, Role);
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Gửi mã xác minh email thất bại",
                                    "Thông báo gửi mã xác minh email",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi hệ thống! Vui lòng thử lại sau.");
                    }

                }
            }
        });

        txtSoDienThoai.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lblSoDienThoaiEror.setVisible(false);

            }

            @Override
            public void focusLost(FocusEvent e) {
                // Không cần làm gì khi mất focus
            }
        });

        txtEmail.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lblEmailEror.setVisible(false);

            }

            @Override
            public void focusLost(FocusEvent e) {
                // Không cần làm gì khi mất focus
            }
        });

        txtMatKhau.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lblMatKhauEror.setVisible(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Không cần làm gì khi mất focus
            }
        });

        txtHoTen.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lblHoTenEror.setVisible(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Không cần làm gì khi mất focus
            }
        });

        txtTaiKhoan.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lblTaiKhoanEror.setVisible(false); // Ẩn lỗi khi ô nhập tài khoản được bấm vào
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Không cần làm gì khi mất focus
            }
        });

        rdoHocVien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblRoleEror.setVisible(false);
            }
        });

        rdoPhuHuynh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblRoleEror.setVisible(false);
            }
        });

    }

    public void FromXacMinhEmail(String UserName, String Pass, String Ten, String SDT, String email, String Role) {
        JFrame FromXacMinh = new JFrame();
        FromXacMinh.setTitle("Xác minh tài khoản email");
        FromXacMinh.setSize(600, 350);
        FromXacMinh.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FromXacMinh.setLocationRelativeTo(null);
        FromXacMinh.setVisible(true);
        FromXacMinh.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(3, 39, 87));
        FromXacMinh.add(panel);

        ImageIcon robotIcon = new ImageIcon("D:\\coachmeai\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\COACH ME.png");
        Image img = robotIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        JLabel lblRobot = new JLabel(new ImageIcon(img));
        lblRobot.setBounds(30, 50, 180, 180);
        panel.add(lblRobot);

        JLabel lblTitle = new JLabel("Xác minh tài khoản email", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(280, 60, 270, 30);
        panel.add(lblTitle);

        JLabel lbl_GT = new JLabel("Mã xác minh đã được gửi đến email đăng ký của bạn");
        lbl_GT.setForeground(Color.WHITE);
        lbl_GT.setBounds(280, 85, 280, 25);
        panel.add(lbl_GT);

        JLabel lbl_MaOTP = new JLabel("Nhập mã");
        lbl_MaOTP.setForeground(Color.WHITE);
        lbl_MaOTP.setBounds(280, 120, 150, 25);
        panel.add(lbl_MaOTP);

        JTextField txt_MaOTP = new JPasswordField();
        txt_MaOTP.setBounds(280, 140, 280, 30);
        panel.add(txt_MaOTP);

        // nut dang nhap
        JButton btn_Check = new JButton("XÁC NHẬN");
        btn_Check.setBounds(280, 190, 280, 35);
        panel.add(btn_Check);

        btn_Check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String MaXacMinh = txt_MaOTP.getText();
                if (otpObject != null && verifyOTP(otpObject, MaXacMinh)) {
                    boolean check = ServiceDangKy.InsertTaiKhoan(UserName, Pass, Ten, SDT, email, Role, Anh);
                    if (check == true) {
                        JOptionPane.showMessageDialog(rootPane, "Dang ky thanh cong");
                        FromXacMinh.dispose();
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "OTP không chính xác!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

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
            java.util.logging.Logger.getLogger(ViewDangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewDangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewDangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewDangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewDangKy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
