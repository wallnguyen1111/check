/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.DAO.Dao;
import com.mycompany.entity.TaiKhoan;
import com.mycompany.utils.Auth;
import com.mycompany.DAO.LienKetTaiKhoanDAO;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.sql.*;

import java.util.Map;

/**
 * @author Admin
 */
public class ThongKeHocTap extends JPanel {

    private JPanel blueCardPanel; // JPanel chứa blue_2_Panel và blue_3_Panel
    private CardLayout cardLayout;
    private JComboBox<String> cbo_hv;
    TaiKhoan user = Auth.getUser();

    public ThongKeHocTap() {
        giaoDien_ThongKeHocTap();
    }

    public void giaoDien_ThongKeHocTap() {
        this.setLayout(null);

        JPanel whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setBounds(0, 0, 710, 650);
        whitePanel.setLayout(null);

        JLabel txt_tkht = new JLabel("THỐNG KÊ HỌC TẬP");
        txt_tkht.setBounds(30, 30, 600, 40);
        txt_tkht.setFont(new Font("Arial", Font.BOLD, 35));
        whitePanel.add(txt_tkht);

        JLabel lbl_MHV = new JLabel("Mã học viên");
        lbl_MHV.setBounds(30, 90, 150, 30);
        lbl_MHV.setFont(new Font("Arial", Font.PLAIN, 15));
        whitePanel.add(lbl_MHV);

        cbo_hv = new JComboBox<>();
        loadStudents(); // Gọi hàm lấy dữ liệu từ database
        add(cbo_hv);

        cbo_hv.setBounds(120, 90, 120, 30);
        cbo_hv.setBackground(Color.decode("#064469"));
        cbo_hv.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        cbo_hv.setBorder(null);
        cbo_hv.setForeground(Color.white);
        whitePanel.add(cbo_hv);

        JLabel lbl_themHocVien = new JLabel("Thêm học viên");
        lbl_themHocVien.setBounds(300, 90, 150, 30);
        lbl_themHocVien.setFont(new Font("Arial", Font.PLAIN, 15));
        whitePanel.add(lbl_themHocVien);

        JTextField txt_themHocVien = new JTextField("Mời tìm kiếm");
        txt_themHocVien.setBounds(410, 90, 190, 30);
        txt_themHocVien.setBackground(Color.decode("#BFD7E3"));
        txt_themHocVien.setForeground(Color.GRAY); // Đặt màu chữ nhạt ban đầu

        ImageIcon originalIcon = new ImageIcon("D:\\CODE\\DuAnTotNghiep\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Images\\chuong.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon searchIcon = new ImageIcon(scaledImage);

        JButton btn_themHocVien = new JButton(searchIcon);
        btn_themHocVien.setBounds(600, 90, 54, 30);
        btn_themHocVien.setBackground(Color.decode("#064469"));
        btn_themHocVien.setBorderPainted(false);
        whitePanel.add(btn_themHocVien);

        txt_themHocVien.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt_themHocVien.getText().equals("Mời tìm kiếm")) {
                    txt_themHocVien.setText("");
                    txt_themHocVien.setForeground(Color.BLACK); // Chuyển màu chữ thành đen khi nhập
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt_themHocVien.getText().isEmpty()) {
                    txt_themHocVien.setText("Mời tìm kiếm");
                    txt_themHocVien.setForeground(Color.GRAY); // Trả về màu chữ nhạt nếu bỏ trống
                }
            }
        });

        whitePanel.add(txt_themHocVien);

        btn_themHocVien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputId = txt_themHocVien.getText().trim();

                if (inputId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập ID học viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int idHocSinh = Integer.parseInt(inputId);
                    int idPhuHuynh = user.getID_Tai_Khoan(); // Lấy ID của phụ huynh hiện tại

                    LienKetTaiKhoanDAO lkDAO = new LienKetTaiKhoanDAO();

                    // Kiểm tra học viên có tồn tại không & có phải học viên không
                    String tenHocSinh = lkDAO.getHoTenHocVien(idHocSinh);
                    if (tenHocSinh == null) {
                        JOptionPane.showMessageDialog(null, "ID học viên không tồn tại hoặc không phải học viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra xem đã liên kết chưa
                    if (lkDAO.isLinked(idPhuHuynh, idHocSinh)) {
                        JOptionPane.showMessageDialog(null, "Tài khoản học viên đã nhận được thông tin kết nối với tài khoản của bạn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    // Gửi yêu cầu liên kết
                    boolean success = lkDAO.sendLinkRequest(idPhuHuynh, idHocSinh);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Yêu cầu liên kết đã được gửi đến " + tenHocSinh + "!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi gửi yêu cầu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID học viên phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Khởi tạo CardLayout để quản lý các giao diện trong blueCardPanel
        cardLayout = new CardLayout();
        blueCardPanel = new JPanel(cardLayout);
        blueCardPanel.setBounds(20, 150, 635, 450);
        whitePanel.add(blueCardPanel);

        JPanel blue_tkht_Panel = giaoDien_0_1_ThongKeHocTap();
        blueCardPanel.add(blue_tkht_Panel, "BLUE_tkht");

        JPanel blue_tkbd_Panel = giaoDien_0_2_ThongKeBangDiem();
        blueCardPanel.add(blue_tkbd_Panel, "BLUE_tkbd");

        this.add(whitePanel);
        this.setVisible(true);
    }

    private int getPhuHuynhId(int userId) {
        int idPhuHuynh = -1;
        String sql = "SELECT ID_Tai_Khoan_Phu_Huynh FROM Phu_Huynh_Hoc_Sinh WHERE ID_Tai_Khoan_Phu_Huynh = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idPhuHuynh = rs.getInt("ID_Tai_Khoan_Phu_Huynh");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idPhuHuynh;
    }

    public static boolean kiemTraHocSinh(int idHocSinh) {
        String url = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";
        String sql = "SELECT * FROM Tai_Khoan WHERE ID_Tai_Khoan = ? AND Role = N'Học viên'";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idHocSinh);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; // ID tồn tại và role là Học viên
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Không tìm thấy hoặc không phải Học viên
    }

    public JLabel createMenuItem(String text, String iconPath) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image img = icon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH); // Chỉnh ảnh nhỏ lại
        icon = new ImageIcon(img);

        JLabel label = new JLabel(text, icon, JLabel.LEFT);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    public JPanel giaoDien_0_1_ThongKeHocTap() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#D0EBF8"));
        panel.setBounds(20, 150, 635, 450);
        panel.setLayout(null); // Bỏ layout để tự set vị trí

        JLabel lbl_tkht = new JLabel("THỐNG KÊ HỌC TẬP");
        lbl_tkht.setBounds(20, 20, 250, 30);
        lbl_tkht.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lbl_tkht);

        JComboBox<String> cbo_hv_thang = new JComboBox<>(new String[]{"Tháng", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"});
        cbo_hv_thang.setBounds(307, 20, 150, 30);
        cbo_hv_thang.setBackground(Color.decode("#064469"));
        cbo_hv_thang.setFont(new Font("Arial", Font.BOLD, 12));
        cbo_hv_thang.setBorder(null);
        cbo_hv_thang.setForeground(Color.white);
        panel.add(cbo_hv_thang);

        JComboBox<String> cbo_hv_nam = new JComboBox<>(new String[]{"Năm", "Năm 2024", "Năm 2025", "Năm 2026", "Năm 2027"});
        cbo_hv_nam.setBounds(467, 20, 150, 30);
        cbo_hv_nam.setBackground(Color.decode("#064469"));
        cbo_hv_nam.setFont(new Font("Arial", Font.BOLD, 12));
        cbo_hv_nam.setBorder(null);
        cbo_hv_nam.setForeground(Color.white);
        panel.add(cbo_hv_nam);

        JPanel panel_soGioHoc = new JPanel();
        panel_soGioHoc.setBackground(Color.WHITE);// Nền trắng
        panel_soGioHoc.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Viền đen dày 2px
        panel_soGioHoc.setBounds(20, 80, 190, 100);
        panel_soGioHoc.setLayout(null);
        panel.add(panel_soGioHoc);

        JPanel panel_nhiemVuDaHoanThanh = new JPanel();
        panel_nhiemVuDaHoanThanh.setBackground(Color.WHITE); // Nền trắng
        panel_nhiemVuDaHoanThanh.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Viền đen dày 2px
        panel_nhiemVuDaHoanThanh.setBounds(225, 80, 190, 100);
        panel_nhiemVuDaHoanThanh.setLayout(null);
        panel.add(panel_nhiemVuDaHoanThanh);

        JPanel panel_nhiemVuChuaHoanThanh = new JPanel();
        panel_nhiemVuChuaHoanThanh.setBackground(Color.WHITE); // Nền trắng
        panel_nhiemVuChuaHoanThanh.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Viền đen dày 2px
        panel_nhiemVuChuaHoanThanh.setBounds(430, 80, 190, 100);
        panel_nhiemVuChuaHoanThanh.setLayout(null);
        panel.add(panel_nhiemVuChuaHoanThanh);

        JLabel lbl_sgh_1 = new JLabel("Số giờ học");
        lbl_sgh_1.setBounds(10, 10, 150, 20);
        lbl_sgh_1.setFont(new Font("Arial", Font.BOLD, 12));
        panel_soGioHoc.add(lbl_sgh_1);

        JLabel lbl_sgh_2 = new JLabel("...");
        lbl_sgh_2.setBounds(10, 35, 100, 30);
        lbl_sgh_2.setFont(new Font("Arial", Font.BOLD, 27));
        panel_soGioHoc.add(lbl_sgh_2);

        JLabel lbl_sgh_3 = new JLabel("Trong 1 tháng");
        lbl_sgh_3.setBounds(10, 70, 200, 20);
        lbl_sgh_3.setForeground(Color.GRAY);
        panel_soGioHoc.add(lbl_sgh_3);

        JLabel lbl_nvdht_1 = new JLabel("Nhiệm vụ đã hoàn thành");
        lbl_nvdht_1.setBounds(10, 10, 150, 20);
        lbl_nvdht_1.setFont(new Font("Arial", Font.BOLD, 12));
        panel_nhiemVuDaHoanThanh.add(lbl_nvdht_1);

        JLabel lbl_nvdht_2 = new JLabel("...");
        lbl_nvdht_2.setBounds(10, 35, 100, 30);
        lbl_nvdht_2.setFont(new Font("Arial", Font.BOLD, 27));
        panel_nhiemVuDaHoanThanh.add(lbl_nvdht_2);

        JLabel lbl_nvdht_3 = new JLabel("Trong 1 tháng");
        lbl_nvdht_3.setBounds(10, 70, 200, 20);
        lbl_nvdht_3.setForeground(Color.GRAY);
        panel_nhiemVuDaHoanThanh.add(lbl_nvdht_3);

        JLabel lbl_nvcht_1 = new JLabel("Nhiệm vụ chưa hoàn thành");
        lbl_nvcht_1.setBounds(10, 10, 155, 20);
        lbl_nvcht_1.setFont(new Font("Arial", Font.BOLD, 12));
        panel_nhiemVuChuaHoanThanh.add(lbl_nvcht_1);

        JLabel lbl_nvcht_2 = new JLabel("...");
        lbl_nvcht_2.setBounds(10, 35, 100, 30);
        lbl_nvcht_2.setFont(new Font("Arial", Font.BOLD, 27));
        panel_nhiemVuChuaHoanThanh.add(lbl_nvcht_2);

        JLabel lbl_nvcht_3 = new JLabel("Trong 1 tháng");
        lbl_nvcht_3.setBounds(10, 70, 200, 20);
        lbl_nvcht_3.setForeground(Color.GRAY);
        panel_nhiemVuChuaHoanThanh.add(lbl_nvcht_3);

        JLabel lbl_cauHoi = new JLabel("Thư chào mừng");
        lbl_cauHoi.setBounds(20, 200, 200, 20);
        lbl_cauHoi.setForeground(Color.GRAY);
        lbl_cauHoi.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lbl_cauHoi);

        // Mục 1: Hỏi Coach Me AI
        JLabel coachMeAI = createMenuItem("Xin chào các vị phụ huynh", "D:\\CODE\\DuAnTotNghiep\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Images\\1.png");
        coachMeAI.setBounds(20, 240, 250, 30);

        // Mục 2: Liên hệ với chúng tôi
        JLabel lienHe = createMenuItem("Chào mừng đến với CMAI", "D:\\CODE\\DuAnTotNghiep\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Images\\2.png");
        lienHe.setBounds(20, 280, 250, 30);

        // Tiêu đề "Công cụ"
        JLabel labelCongCu = new JLabel("Lời cảm ơn");
        labelCongCu.setFont(new Font("Arial", Font.BOLD, 18));
        labelCongCu.setForeground(Color.GRAY);
        labelCongCu.setBounds(20, 320, 200, 20);

        // Mục 3: Tạo lịch học
        JLabel taoLichHoc = createMenuItem("Chúng tôi rất hân hạnh", "D:\\CODE\\DuAnTotNghiep\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Images\\3.png");
        taoLichHoc.setBounds(20, 360, 250, 30);

        // Mục 4: Bộ đếm giờ học
        JLabel boDemGio = createMenuItem("Hỗ trợ và đồng hành", "D:\\CODE\\DuAnTotNghiep\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Images\\4.png");
        boDemGio.setBounds(20, 400, 250, 30);

        JButton btn_ThongKeBangDiem = new JButton("Thống kê bảng điểm");
        btn_ThongKeBangDiem.setBounds(470, 400, 150, 30);
        btn_ThongKeBangDiem.setBackground(Color.decode("#064469")); // Màu nền
        btn_ThongKeBangDiem.setForeground(Color.WHITE); // Màu chữ thành trắng
        btn_ThongKeBangDiem.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        panel.add(btn_ThongKeBangDiem);

        // Thêm các thành phần vào panel
        panel.add(coachMeAI);
        panel.add(lienHe);
        panel.add(labelCongCu);
        panel.add(taoLichHoc);
        panel.add(boDemGio);
        btn_ThongKeBangDiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(blueCardPanel, "BLUE_tkbd"); // Chuyển sang giao diện giaoDien_1
            }
        });

        cbo_hv_thang.addActionListener(e -> capNhatThongKe(lbl_sgh_2, lbl_nvdht_2, lbl_nvcht_2, cbo_hv_thang, cbo_hv_nam));
        cbo_hv_nam.addActionListener(e -> capNhatThongKe(lbl_sgh_2, lbl_nvdht_2, lbl_nvcht_2, cbo_hv_thang, cbo_hv_nam));

        return panel;
    }

    public JPanel giaoDien_0_2_ThongKeBangDiem() {
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.decode("#D0EBF8"));
        panel2.setBounds(20, 150, 635, 450);
        panel2.setLayout(null); // Bỏ layout để tự set vị trí

        JLabel lbl_tkht = new JLabel("THỐNG KÊ BẢNG ĐIỂM");
        lbl_tkht.setBounds(20, 20, 250, 30);
        lbl_tkht.setFont(new Font("Arial", Font.BOLD, 20));
        panel2.add(lbl_tkht);

        // Combobox tháng và năm của giao diện 2
        JComboBox<String> cbo_hv_thangD = new JComboBox<>(new String[]{"Tháng", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"});
        cbo_hv_thangD.setBounds(307, 20, 150, 30);
        cbo_hv_thangD.setBackground(Color.decode("#064469"));
        cbo_hv_thangD.setFont(new Font("Arial", Font.BOLD, 12));
        cbo_hv_thangD.setBorder(null);
        cbo_hv_thangD.setForeground(Color.white);
        panel2.add(cbo_hv_thangD);

        JComboBox<String> cbo_hv_namD = new JComboBox<>(new String[]{"Năm", "Năm 2024", "Năm 2025", "Năm 2026", "Năm 2027"});
        cbo_hv_namD.setBounds(467, 20, 150, 30);
        cbo_hv_namD.setBackground(Color.decode("#064469"));
        cbo_hv_namD.setFont(new Font("Arial", Font.BOLD, 12));
        cbo_hv_namD.setBorder(null);
        cbo_hv_namD.setForeground(Color.white);
        panel2.add(cbo_hv_namD);

        JPanel panel_soGioHoc = new JPanel();
        panel_soGioHoc.setBackground(Color.WHITE);// Nền trắng
        panel_soGioHoc.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Viền đen dày 2px
        panel_soGioHoc.setBounds(20, 80, 190, 100);
        panel_soGioHoc.setLayout(null);
        panel2.add(panel_soGioHoc);

        JPanel panel_nhiemVuDaHoanThanh = new JPanel();
        panel_nhiemVuDaHoanThanh.setBackground(Color.WHITE); // Nền trắng
        panel_nhiemVuDaHoanThanh.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Viền đen dày 2px
        panel_nhiemVuDaHoanThanh.setBounds(225, 80, 190, 100);
        panel_nhiemVuDaHoanThanh.setLayout(null);
        panel2.add(panel_nhiemVuDaHoanThanh);

        JPanel panel_nhiemVuChuaHoanThanh = new JPanel();
        panel_nhiemVuChuaHoanThanh.setBackground(Color.WHITE); // Nền trắng
        panel_nhiemVuChuaHoanThanh.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Viền đen dày 2px
        panel_nhiemVuChuaHoanThanh.setBounds(430, 80, 190, 100);
        panel_nhiemVuChuaHoanThanh.setLayout(null);
        panel2.add(panel_nhiemVuChuaHoanThanh);

        JLabel lbl_sgh_1 = new JLabel("Điểm TB môn toán");
        lbl_sgh_1.setBounds(10, 10, 150, 20);
        lbl_sgh_1.setFont(new Font("Arial", Font.BOLD, 12));
        panel_soGioHoc.add(lbl_sgh_1);

        JLabel lbl_sgh_2 = new JLabel("...");
        lbl_sgh_2.setBounds(10, 35, 100, 30);
        lbl_sgh_2.setFont(new Font("Arial", Font.BOLD, 27));
        panel_soGioHoc.add(lbl_sgh_2);

        JLabel lbl_sgh_3 = new JLabel("Trong 1 tháng");
        lbl_sgh_3.setBounds(10, 70, 200, 20);
        lbl_sgh_3.setForeground(Color.GRAY);
        panel_soGioHoc.add(lbl_sgh_3);

        JLabel lbl_nvdht_1 = new JLabel("Điểm TB môn văn");
        lbl_nvdht_1.setBounds(10, 10, 150, 20);
        lbl_nvdht_1.setFont(new Font("Arial", Font.BOLD, 12));
        panel_nhiemVuDaHoanThanh.add(lbl_nvdht_1);

        JLabel lbl_nvdht_2 = new JLabel("...");
        lbl_nvdht_2.setBounds(10, 35, 100, 30);
        lbl_nvdht_2.setFont(new Font("Arial", Font.BOLD, 27));
        panel_nhiemVuDaHoanThanh.add(lbl_nvdht_2);

        JLabel lbl_nvdht_3 = new JLabel("Trong 1 tháng");
        lbl_nvdht_3.setBounds(10, 70, 200, 20);
        lbl_nvdht_3.setForeground(Color.GRAY);
        panel_nhiemVuDaHoanThanh.add(lbl_nvdht_3);

        JLabel lbl_nvcht_1 = new JLabel("Điểm TB môn anh");
        lbl_nvcht_1.setBounds(10, 10, 155, 20);
        lbl_nvcht_1.setFont(new Font("Arial", Font.BOLD, 12));
        panel_nhiemVuChuaHoanThanh.add(lbl_nvcht_1);

        JLabel lbl_nvcht_2 = new JLabel("...");
        lbl_nvcht_2.setBounds(10, 35, 100, 30);
        lbl_nvcht_2.setFont(new Font("Arial", Font.BOLD, 27));
        panel_nhiemVuChuaHoanThanh.add(lbl_nvcht_2);

        JLabel lbl_nvcht_3 = new JLabel("Trong 1 tháng");
        lbl_nvcht_3.setBounds(10, 70, 200, 20);
        lbl_nvcht_3.setForeground(Color.GRAY);
        panel_nhiemVuChuaHoanThanh.add(lbl_nvcht_3);

        JLabel lbl_cauHoi = new JLabel("Thư chào mừng");
        lbl_cauHoi.setBounds(20, 200, 200, 20);
        lbl_cauHoi.setForeground(Color.GRAY);
        lbl_cauHoi.setFont(new Font("Arial", Font.BOLD, 18));
        panel2.add(lbl_cauHoi);

        // Mục 1: Hỏi Coach Me AI
        JLabel coachMeAI = createMenuItem("Xin chào các vị phụ huynh", "D:\\CODE\\DuAnTotNghiep\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Images\\1.png");
        coachMeAI.setBounds(20, 240, 250, 30);

        // Mục 2: Liên hệ với chúng tôi
        JLabel lienHe = createMenuItem("Chào mừng đến với CMAI", "D:\\CODE\\DuAnTotNghiep\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Images\\2.png");
        lienHe.setBounds(20, 280, 250, 30);

        // Tiêu đề "Công cụ"
        JLabel labelCongCu = new JLabel("Lời cảm ơn");
        labelCongCu.setFont(new Font("Arial", Font.BOLD, 18));
        labelCongCu.setForeground(Color.GRAY);
        labelCongCu.setBounds(20, 320, 200, 20);

        // Mục 3: Tạo lịch học
        JLabel taoLichHoc = createMenuItem("Chúng tôi rất hân hạnh", "D:\\CODE\\DuAnTotNghiep\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Images\\3.png");
        taoLichHoc.setBounds(20, 360, 250, 30);

        // Mục 4: Bộ đếm giờ học
        JLabel boDemGio = createMenuItem("Hỗ trợ và đồng hành", "D:\\CODE\\DuAnTotNghiep\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Images\\4.png");
        boDemGio.setBounds(20, 400, 250, 30);

        JButton btn_ThongKeBangDiem = new JButton("Thống kê học tập");
        btn_ThongKeBangDiem.setBounds(470, 400, 150, 30);
        btn_ThongKeBangDiem.setBackground(Color.decode("#064469")); // Màu nền
        btn_ThongKeBangDiem.setForeground(Color.WHITE); // Màu chữ thành trắng
        btn_ThongKeBangDiem.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        panel2.add(btn_ThongKeBangDiem);

        // Thêm các thành phần vào panel
        panel2.add(coachMeAI);
        panel2.add(lienHe);
        panel2.add(labelCongCu);
        panel2.add(taoLichHoc);
        panel2.add(boDemGio);

        btn_ThongKeBangDiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(blueCardPanel, "BLUE_tkht"); // Chuyển sang giao diện giaoDien_1
            }
        });

        cbo_hv_thangD.addActionListener(e -> capNhatThongKeDiem(lbl_sgh_2, lbl_nvdht_2, lbl_nvcht_2, cbo_hv_thangD, cbo_hv_namD));
        cbo_hv_namD.addActionListener(e -> capNhatThongKeDiem(lbl_sgh_2, lbl_nvdht_2, lbl_nvcht_2, cbo_hv_thangD, cbo_hv_namD));

        return panel2;
    }

    public void capNhatThongKe(JLabel lbl_sgh, JLabel lbl_nvdht, JLabel lbl_nvcht, JComboBox<String> cbo_hv_thang, JComboBox<String> cbo_hv_nam) {

        int idTaiKhoan = 1; // ID của học viên (có thể lấy từ session hoặc input)

        int thang = cbo_hv_thang.getSelectedIndex(); // 0 if "Tháng" not selected
        String namSelected = cbo_hv_nam.getSelectedItem().toString();

        // Check if user selected valid options
        if (thang == 0 || "Năm".equals(namSelected)) {
            lbl_sgh.setText("...");
            lbl_nvdht.setText("...");
            lbl_nvcht.setText("...");
            return; // exit early to avoid invalid parsing
        }

        // Continue normally if valid inputs are chosen
        int nam = Integer.parseInt(namSelected.replace("Năm ", ""));

        int soGioHoc = Dao.getSoGioHocPH(idTaiKhoan, thang, nam);
        int nhiemVuHoanThanh = Dao.getNhiemVuHoanThanhPH(idTaiKhoan, thang, nam);
        int nhiemVuChuaHoanThanh = Dao.getNhiemVuChuaHoanThanhPH(idTaiKhoan, thang, nam);

        lbl_sgh.setText(String.valueOf(soGioHoc));
        lbl_nvdht.setText(String.valueOf(nhiemVuHoanThanh));
        lbl_nvcht.setText(String.valueOf(nhiemVuChuaHoanThanh));
    }

    public void capNhatThongKeDiem(JLabel lbl_toan, JLabel lbl_van, JLabel lbl_anh, JComboBox<String> cbo_hv_thang, JComboBox<String> cbo_hv_nam) {
        int idTaiKhoan = 1; // ID của học viên (có thể lấy từ session hoặc input)

        int thang = cbo_hv_thang.getSelectedIndex(); // 0 nếu "Tháng" chưa được chọn
        String namSelected = cbo_hv_nam.getSelectedItem().toString();

        // Kiểm tra nếu người dùng chưa chọn tháng/năm hợp lệ
        if (thang == 0 || "Năm".equals(namSelected)) {
            lbl_toan.setText("...");
            lbl_van.setText("...");
            lbl_anh.setText("...");
            return; // Dừng hàm sớm để tránh lỗi parse
        }

        // Chuyển đổi năm từ chuỗi sang số nguyên
        int nam = Integer.parseInt(namSelected.replace("Năm ", ""));

        // Lấy điểm trung bình từ DAO
        Map<String, Float> diemTBMap = Dao.getDiemTrungBinhPH(idTaiKhoan, thang, nam);

        // Cập nhật điểm lên giao diện
        lbl_toan.setText(String.valueOf(diemTBMap.getOrDefault("Toán", 0.0f)));
        lbl_van.setText(String.valueOf(diemTBMap.getOrDefault("Văn", 0.0f)));
        lbl_anh.setText(String.valueOf(diemTBMap.getOrDefault("Anh", 0.0f)));
    }

    private void loadStudents() {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "SELECT tk.ID_Tai_Khoan, tk.Ho_Va_Ten "
                    + "FROM dbo.Tai_Khoan tk "
                    + "JOIN dbo.Phu_Huynh_Hoc_Sinh phhs ON phhs.ID_Tai_Khoan_Hoc_Sinh = tk.ID_Tai_Khoan "
                    + "WHERE tk.Role = N'Học viên' AND phhs.ID_Tai_Khoan_Phu_Huynh IS NOT NULL";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            cbo_hv.removeAllItems(); // Xóa dữ liệu cũ trước khi load lại

            while (rs.next()) {
                int id = rs.getInt("ID_Tai_Khoan");
                String name = rs.getString("Ho_Va_Ten");
                System.out.println("Tìm thấy: " + id + " - " + name); // Debug kiểm tra

                cbo_hv.addItem(id + " - " + name);
            }

            rs.close();
            stmt.close();
            conn.close();

            cbo_hv.repaint();
            cbo_hv.revalidate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối CSDL!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Thông tin cá nhân");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(710, 650);
            frame.setResizable(false);
            ThongKeHocTap ttcnPanel = new ThongKeHocTap();
            frame.add(ttcnPanel);
            frame.setLocationRelativeTo(null); // Căn giữa màn hình
            frame.setVisible(true);
        });
    }

    public class DatabaseConnection {

        private static final String URL = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";

        public static Connection getConnection() {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    }

}
