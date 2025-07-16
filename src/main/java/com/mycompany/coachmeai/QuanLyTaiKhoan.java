/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import com.mycompany.DAO.DatabaseConnection;
import javax.swing.JPanel;
import com.mycompany.coachmeai.ChatTool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class QuanLyTaiKhoan extends JPanel {
    
    private JLabel lbl_tsph_so_luong;
    private JLabel lbl_tshv_so_luong;
    private JLabel lbl_tshv_tang_truong;
    private JLabel lbl_tsph_tang_truong;
    // Khai báo trong class
    private JComboBox<String> cbo_thang;
    private JComboBox<String> cbo_nam;

    public QuanLyTaiKhoan() {
        giaoDien_QuanLyTaiKhoan();
    }

    public void giaoDien_QuanLyTaiKhoan() {
        this.setLayout(null);

        JPanel whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setBounds(0, 0, 710, 650);
        whitePanel.setLayout(null);

        JLabel lbl_qltk = new JLabel("QUẢN LÝ TÀI KHOẢN");
        lbl_qltk.setBounds(20, 5, 250, 30);
        lbl_qltk.setFont(new Font("Arial", Font.BOLD, 20));
        whitePanel.add(lbl_qltk);

        JPanel panel_tong_so_hv = new JPanel();
        panel_tong_so_hv.setBackground(Color.WHITE); // Nền trắng
        panel_tong_so_hv.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Viền đen dày 2px
        panel_tong_so_hv.setBounds(  20, 40, 245, 120);
        panel_tong_so_hv.setLayout(null);

        whitePanel.add(panel_tong_so_hv);

        JLabel lbl_tongSo_hv = new JLabel("Tổng số học viên");
        lbl_tongSo_hv.setBounds(20, 10, 150, 20);
        lbl_tongSo_hv.setFont(new Font("Arial", Font.BOLD, 15));
        panel_tong_so_hv.add(lbl_tongSo_hv);

        lbl_tshv_so_luong = new JLabel("....");
        lbl_tshv_so_luong.setBounds(20, 35, 100, 30);
        lbl_tshv_so_luong.setFont(new Font("Arial", Font.BOLD, 27));
        panel_tong_so_hv.add(lbl_tshv_so_luong);

        lbl_tshv_tang_truong = new JLabel("....");
        lbl_tshv_tang_truong.setBounds(20, 70, 200, 20);
        lbl_tshv_tang_truong.setForeground(Color.GRAY);
        panel_tong_so_hv.add(lbl_tshv_tang_truong);

        JLabel lbl_tongSo_hv_2 = new JLabel("   so với tháng trước");
        lbl_tongSo_hv_2.setBounds(50, 70, 200, 20);
        lbl_tongSo_hv_2.setForeground(Color.GRAY);
        panel_tong_so_hv.add(lbl_tongSo_hv_2);

        JLabel lbl_tongSo_hv_3 = new JLabel("Trong 1 tháng");
        lbl_tongSo_hv_3.setBounds(20, 90, 200, 20);
        lbl_tongSo_hv_3.setForeground(Color.GRAY);
        panel_tong_so_hv.add(lbl_tongSo_hv_3);

        JPanel panel_tong_so_ph = new JPanel();
        panel_tong_so_ph.setBackground(Color.WHITE); // Nền trắng
        panel_tong_so_ph.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Viền đen dày 2px
        panel_tong_so_ph.setBounds(290, 40, 245, 120);
        panel_tong_so_ph.setLayout(null);
        whitePanel.add(panel_tong_so_ph);

        JLabel lbl_tongSo_ph = new JLabel("Tổng số phụ huynh");
        lbl_tongSo_ph.setBounds(20, 10, 150, 20);
        lbl_tongSo_ph.setFont(new Font("Arial", Font.BOLD, 15));
        panel_tong_so_ph.add(lbl_tongSo_ph);

        lbl_tsph_so_luong = new JLabel("....");
        lbl_tsph_so_luong.setBounds(20, 35, 100, 30);
        lbl_tsph_so_luong.setFont(new Font("Arial", Font.BOLD, 27));
        panel_tong_so_ph.add(lbl_tsph_so_luong);

        lbl_tsph_tang_truong = new JLabel("....");
        lbl_tsph_tang_truong.setBounds(20, 70, 200, 20);
        lbl_tsph_tang_truong.setForeground(Color.GRAY);
        panel_tong_so_ph.add(lbl_tsph_tang_truong);

        JLabel lbl_tongSo_ph_2 = new JLabel("   so với tháng trước");
        lbl_tongSo_ph_2.setBounds(50, 70, 200, 20);
        lbl_tongSo_ph_2.setForeground(Color.GRAY);
        panel_tong_so_ph.add(lbl_tongSo_ph_2);

        JLabel lbl_tongSo_ph_3 = new JLabel("Trong 1 tháng");
        lbl_tongSo_ph_3.setBounds(20, 90, 200, 20);
        lbl_tongSo_ph_3.setForeground(Color.GRAY);
        panel_tong_so_ph.add(lbl_tongSo_ph_3);

        cbo_thang = new JComboBox<>(new String[]{
            "Tháng", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5",
            "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        });
        cbo_thang.setBounds(550, 40, 100, 30);
        cbo_thang.setBackground(Color.decode("#064469"));
        cbo_thang.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        cbo_thang.setBorder(null);
        cbo_thang.setForeground(Color.white);
        whitePanel.add(cbo_thang);

        cbo_nam = new JComboBox<>(new String[]{"Năm", "Năm 2023", "Năm 2024", "Năm 2025"});
        cbo_nam.setBounds(550, 80, 100, 30);
        cbo_nam.setBackground(Color.decode("#064469"));
        cbo_nam.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        cbo_nam.setBorder(null);
        cbo_nam.setForeground(Color.white);
        whitePanel.add(cbo_nam);

        // Đặt tháng và năm mặc định là hiện tại
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        cbo_thang.setSelectedIndex(currentMonth); // Set tháng hiện tại
        cbo_nam.setSelectedItem("Năm " + currentYear); // Set năm hiện tại

        // Gọi cập nhật thống kê ngay sau khi set giá trị mặc định
        capNhatThongKe(lbl_tshv_so_luong, lbl_tsph_so_luong, lbl_tshv_tang_truong, lbl_tsph_tang_truong, cbo_thang, cbo_nam);

        cbo_thang.addActionListener(e -> capNhatThongKe(lbl_tshv_so_luong, lbl_tsph_so_luong, lbl_tshv_tang_truong, lbl_tsph_tang_truong, cbo_thang, cbo_nam));
        cbo_nam.addActionListener(e -> capNhatThongKe(lbl_tshv_so_luong, lbl_tsph_so_luong, lbl_tshv_tang_truong, lbl_tsph_tang_truong, cbo_thang, cbo_nam));

        JTabbedPane tabbedPanel_qltk_hv_ph = new JTabbedPane();
        JPanel panel_qltk_hv = qltk_hv("Học Viên");
        JPanel panel_qltk_ph = qltk_ph("Phụ Huynh");

        tabbedPanel_qltk_hv_ph.addTab("Học Viên", panel_qltk_hv);
        tabbedPanel_qltk_hv_ph.addTab("Phụ Huynh", panel_qltk_ph);
        tabbedPanel_qltk_hv_ph.setBounds(20, 170, 630, 430); // Đặt kích thước và vị trí cho tabbedPane
        whitePanel.add(tabbedPanel_qltk_hv_ph);

        this.add(whitePanel);
        this.setVisible(true);
    }

    public JPanel qltk_hv(String title) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.decode("#D0EBF8"));

        String placeholder = "  Mời nhập ID hoặc tên học viên cần tìm";
        JTextField txt_hv_timKiem = new JTextField(placeholder);
        txt_hv_timKiem.setBounds(10, 10, 500, 30);
        txt_hv_timKiem.setBackground(Color.white);
        txt_hv_timKiem.setForeground(Color.GRAY);
        // Cách 1: Xóa viền (nếu muốn ô nhập không có viền)
        //txt_hv_timKiem.setBorder(new EmptyBorder(0, 5, 0, 5)); 
        // Cách 2: Đặt viền tùy chỉnh (nếu muốn viền mỏng màu xám)
        txt_hv_timKiem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txt_hv_timKiem.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt_hv_timKiem.getText().equals(placeholder)) {
                    txt_hv_timKiem.setText(""); // Xóa nội dung khi nhấn vào
                    txt_hv_timKiem.setForeground(Color.BLACK); // Đổi lại màu chữ bình thường
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt_hv_timKiem.getText().trim().isEmpty()) {
                    txt_hv_timKiem.setText(placeholder); // Đặt lại placeholder nếu ô trống
                    txt_hv_timKiem.setForeground(Color.GRAY); // Đổi màu chữ về xám
                }
            }
        });
        panel.add(txt_hv_timKiem);

        JButton btn_hv_timKiem = new JButton("Tìm Kiếm");
        btn_hv_timKiem.setBounds(520, 10, 100, 30);
        btn_hv_timKiem.setBackground(new Color(10, 38, 74));
        btn_hv_timKiem.setForeground(Color.WHITE);
        btn_hv_timKiem.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        panel.add(btn_hv_timKiem);

        JComboBox<String> cbo_hv_trangThai = new JComboBox<>(new String[]{
            "Trạng thái", "Hoạt Động", "Khóa"
        });
        cbo_hv_trangThai.setBounds(10, 60, 150, 30);
        cbo_hv_trangThai.setBackground(Color.decode("#064469"));
        cbo_hv_trangThai.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        cbo_hv_trangThai.setBorder(null);
        cbo_hv_trangThai.setForeground(Color.white);
        panel.add(cbo_hv_trangThai);

        JComboBox<String> cbo_hv_thang = new JComboBox<>(new String[]{
            "Tháng", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5",
            "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"});
        cbo_hv_thang.setBounds(180, 60, 150, 30);
        cbo_hv_thang.setBackground(Color.decode("#064469"));
        cbo_hv_thang.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        cbo_hv_thang.setBorder(null);
        cbo_hv_thang.setForeground(Color.white);
        panel.add(cbo_hv_thang);

        JComboBox<String> cbo_hv_nam = new JComboBox<>(new String[]{
            "Năm", "Năm 2023", "Năm 2024", "Năm 2025"
        });
        cbo_hv_nam.setBounds(350, 60, 150, 30);
        cbo_hv_nam.setBackground(Color.decode("#064469"));
        cbo_hv_nam.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        cbo_hv_nam.setBorder(null);
        cbo_hv_nam.setForeground(Color.white);
        panel.add(cbo_hv_nam);

        DefaultTableModel tbl_qltk = new DefaultTableModel();
        tbl_qltk.addColumn("ID");
        tbl_qltk.addColumn("Tên tài khoản");
        tbl_qltk.addColumn("Họ và tên");
        tbl_qltk.addColumn("Email");
        tbl_qltk.addColumn("Ngày đăng ký");
        tbl_qltk.addColumn("Vai trò");
        tbl_qltk.addColumn("Trạng thái");
        tbl_qltk.addColumn("Chức năng");

        // Fill học viên
        fillTableTheoVaiTro("Học viên", tbl_qltk);
        JTable tb_data = new JTable();
        tb_data.setRowHeight(30);
        tb_data.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tb_data.getTableHeader().setBackground(new Color(6, 68, 105));
        tb_data.setModel(tbl_qltk);

        // Đặt bảng vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(tb_data);
        scrollPane.setBounds(0, 100, 630, 300);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Cấu hình combo box
        String[] options = {"Chọn", "Xoá", "Khoá", "Mở khoá"};
        JComboBox<String> comboBox = new JComboBox<>(options);

// Gắn editor
        tb_data.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(comboBox));

// Gắn renderer
        tb_data.getColumnModel().getColumn(7).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JComboBox<String> cb = new JComboBox<>(options);
            cb.setSelectedItem(value);
            return cb;
        });

        cbo_hv_trangThai.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String keywordRaw = txt_hv_timKiem.getText();
                String keyword = keywordRaw.equals("  Mời nhập ID hoặc tên học viên cần tìm") ? "" : keywordRaw.trim();
                String trangThai = cbo_hv_trangThai.getSelectedItem().toString();
                String thang = cbo_hv_thang.getSelectedItem().toString();
                String nam = cbo_hv_nam.getSelectedItem().toString();

                // Gọi hàm tìm kiếm và lọc dữ liệu
                timKiemLocHVTongHop("Học viên", keyword, trangThai, thang, nam, tbl_qltk);
            }
        });

        cbo_hv_thang.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String keywordRaw = txt_hv_timKiem.getText();
                String keyword = keywordRaw.equals("  Mời nhập ID hoặc tên học viên cần tìm") ? "" : keywordRaw.trim();
                String trangThai = cbo_hv_trangThai.getSelectedItem().toString();
                String thang = cbo_hv_thang.getSelectedItem().toString();
                String nam = cbo_hv_nam.getSelectedItem().toString();

                // Gọi hàm tìm kiếm và lọc dữ liệu
                timKiemLocHVTongHop("Học viên", keyword, trangThai, thang, nam, tbl_qltk);
            }
        });

        cbo_hv_nam.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String keywordRaw = txt_hv_timKiem.getText();
                String keyword = keywordRaw.equals("  Mời nhập ID hoặc tên học viên cần tìm") ? "" : keywordRaw.trim();
                String trangThai = cbo_hv_trangThai.getSelectedItem().toString();
                String thang = cbo_hv_thang.getSelectedItem().toString();
                String nam = cbo_hv_nam.getSelectedItem().toString();

                // Gọi hàm tìm kiếm và lọc dữ liệu
                timKiemLocHVTongHop("Học viên", keyword, trangThai, thang, nam, tbl_qltk);
            }
        });

        btn_hv_timKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keywordRaw = txt_hv_timKiem.getText();
                String keyword = keywordRaw.equals(placeholder) ? "" : keywordRaw.trim();
                String trangThai = cbo_hv_trangThai.getSelectedItem().toString();
                String thang = cbo_hv_thang.getSelectedItem().toString();
                String nam = cbo_hv_nam.getSelectedItem().toString();

                // Validate keyword
                // Cho phép để trống để hiển thị lại toàn bộ bảng
                if (!keyword.isEmpty()) {
                    if (keyword.length() > 50) {
                        JOptionPane.showMessageDialog(null, "Từ khóa tìm kiếm quá dài (tối đa 50 ký tự).");
                        return;
                    }
                    if (!keyword.matches("[\\p{L}\\p{N}\\s]*")) {
                        JOptionPane.showMessageDialog(null, "Từ khóa chỉ được chứa chữ cái, số và khoảng trắng.");
                        return;
                    }
                }

                timKiemLocHVTongHop("Học viên", keyword, trangThai, thang, nam, tbl_qltk);
            }
        });

        tb_data.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"Chọn", "Xoá", "Khoá", "Mở khoá"})) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                JComboBox<String> comboBox = new JComboBox<>(new String[]{"Chọn", "Xoá", "Khoá", "Mở khoá"});

                final boolean[] isHandling = {false}; // ✅ chống gọi lặp

                comboBox.addActionListener(e -> {
                    if (isHandling[0]) {
                        return;
                    }
                    isHandling[0] = true;

                    SwingUtilities.invokeLater(() -> {
                        try {
                            String selected = (String) comboBox.getSelectedItem();
                            if ("Chọn".equals(selected)) {
                                isHandling[0] = false;
                                return;
                            }

                            int currentRow = table.getEditingRow(); // ✅ luôn lấy lại row đúng
                            DefaultTableModel model = (DefaultTableModel) table.getModel();

                            if (currentRow < 0 || currentRow >= model.getRowCount() || model.getColumnCount() < 7) {
                                System.err.println("⚠ Dòng hoặc cột không hợp lệ.");
                                return;
                            }

                            int id = (int) model.getValueAt(currentRow, 0);
                            String currentStatus = model.getValueAt(currentRow, 6).toString().trim();

                            if ("Xoá".equals(selected)) {
                                int confirm = JOptionPane.showConfirmDialog(null,
                                        "Bạn có chắc muốn xóa tài khoản ID: " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    xoaTaiKhoan(id);
                                    if (table.isEditing()) {
                                        table.getCellEditor().stopCellEditing();
                                    }
                                    model.removeRow(currentRow);

                                    int thang = cbo_thang.getSelectedIndex() > 0 ? cbo_thang.getSelectedIndex() : LocalDate.now().getMonthValue();
                                    Integer nam = (cbo_nam.getSelectedIndex() > 0)
                                            ? Integer.parseInt(cbo_nam.getSelectedItem().toString().replace("Năm ", ""))
                                            : LocalDate.now().getYear();

                                    int soLuonghv = demSoLuongTichLuy("Học viên", thang, nam);
                                    lbl_tshv_so_luong.setText(String.valueOf(soLuonghv));

                                    int prevThang = (thang > 1) ? thang - 1 : 12;
                                    int prevNam = (thang > 1) ? nam : nam - 1;
                                    int hvTruoc = demSoLuongTichLuy("Học viên", prevThang, prevNam);

                                    String hvGrowth = (hvTruoc == 0 && soLuonghv > 0) ? "+100%"
                                            : (hvTruoc == 0) ? "0%"
                                                    : ((soLuonghv - hvTruoc >= 0 ? "+" : "") + (soLuonghv - hvTruoc) * 100 / hvTruoc + "%");

                                    lbl_tshv_tang_truong.setText(hvGrowth);

                                    JOptionPane.showMessageDialog(null, "Đã xoá tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } else if ("Khoá".equals(selected)) {
                                if ("Khóa".equalsIgnoreCase(currentStatus)) {
                                    JOptionPane.showMessageDialog(null, "Tài khoản đã bị khoá trước đó!");
                                } else {
                                    int confirm = JOptionPane.showConfirmDialog(null,
                                            "Bạn có chắc muốn khoá tài khoản ID: " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                                    if (confirm == JOptionPane.YES_OPTION) {
                                        capNhatTrangThaiTaiKhoan(id, "Khóa");
                                        model.setValueAt("Khóa", currentRow, 6);
                                        JOptionPane.showMessageDialog(null, "Tài khoản đã được khoá thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                            } else if ("Mở khoá".equals(selected)) {
                                if ("Hoạt động".equalsIgnoreCase(currentStatus)) {
                                    JOptionPane.showMessageDialog(null, "Tài khoản đã được mở khoá trước đó!");
                                } else {
                                    int confirm = JOptionPane.showConfirmDialog(null,
                                            "Bạn có chắc muốn mở khoá tài khoản ID: " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                                    if (confirm == JOptionPane.YES_OPTION) {
                                        capNhatTrangThaiTaiKhoan(id, "Hoạt động");
                                        model.setValueAt("Hoạt động", currentRow, 6);
                                        JOptionPane.showMessageDialog(null, "Tài khoản đã được mở khoá thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                            }

                            comboBox.setSelectedItem("Chọn"); // reset
                        } finally {
                            isHandling[0] = false;
                        }
                    });
                });

                return comboBox;
            }
        });

        return panel;
    }

    public JPanel qltk_ph(String title) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.decode("#D0EBF8"));

        String placeholder = "  Mời nhập ID hoặc tên phụ huynh cần tìm";
        JTextField txt_hv_timKiem = new JTextField(placeholder);
        txt_hv_timKiem.setBounds(10, 10, 500, 30);
        txt_hv_timKiem.setBackground(Color.white);
        txt_hv_timKiem.setForeground(Color.GRAY);
        // Cách 1: Xóa viền (nếu muốn ô nhập không có viền)
        //txt_hv_timKiem.setBorder(new EmptyBorder(0, 5, 0, 5)); 
        // Cách 2: Đặt viền tùy chỉnh (nếu muốn viền mỏng màu xám)
        txt_hv_timKiem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txt_hv_timKiem.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt_hv_timKiem.getText().equals(placeholder)) {
                    txt_hv_timKiem.setText(""); // Xóa nội dung khi nhấn vào
                    txt_hv_timKiem.setForeground(Color.BLACK); // Đổi lại màu chữ bình thường
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt_hv_timKiem.getText().trim().isEmpty()) {
                    txt_hv_timKiem.setText(placeholder); // Đặt lại placeholder nếu ô trống
                    txt_hv_timKiem.setForeground(Color.GRAY); // Đổi màu chữ về xám
                }
            }
        });
        panel.add(txt_hv_timKiem);

        JButton btn_hv_timKiem = new JButton("Tìm Kiếm");
        btn_hv_timKiem.setBounds(520, 10, 100, 30);
        btn_hv_timKiem.setBackground(new Color(10, 38, 74));
        btn_hv_timKiem.setForeground(Color.WHITE);
        btn_hv_timKiem.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        panel.add(btn_hv_timKiem);

        JComboBox<String> cbo_hv_trangThai = new JComboBox<>(new String[]{
            "Trạng thái", "Hoạt Động", "Khóa"
        });
        cbo_hv_trangThai.setBounds(10, 60, 150, 30);
        cbo_hv_trangThai.setBackground(Color.decode("#064469"));
        cbo_hv_trangThai.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        cbo_hv_trangThai.setBorder(null);
        cbo_hv_trangThai.setForeground(Color.white);
        panel.add(cbo_hv_trangThai);

        JComboBox<String> cbo_hv_thang = new JComboBox<>(new String[]{
            "Tháng", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5",
            "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"});
        cbo_hv_thang.setBounds(180, 60, 150, 30);
        cbo_hv_thang.setBackground(Color.decode("#064469"));
        cbo_hv_thang.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        cbo_hv_thang.setBorder(null);
        cbo_hv_thang.setForeground(Color.white);
        panel.add(cbo_hv_thang);

        JComboBox<String> cbo_hv_nam = new JComboBox<>(new String[]{
            "Năm", "Năm 2023", "Năm 2024", "Năm 2025"
        });
        cbo_hv_nam.setBounds(350, 60, 150, 30);
        cbo_hv_nam.setBackground(Color.decode("#064469"));
        cbo_hv_nam.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        cbo_hv_nam.setBorder(null);
        cbo_hv_nam.setForeground(Color.white);
        panel.add(cbo_hv_nam);

        DefaultTableModel tbl_qltk = new DefaultTableModel();
        tbl_qltk.addColumn("ID");
        tbl_qltk.addColumn("Tên tài khoản");
        tbl_qltk.addColumn("Họ và tên");
        tbl_qltk.addColumn("Email");
        tbl_qltk.addColumn("Ngày đăng ký");
        tbl_qltk.addColumn("Vai trò");
        tbl_qltk.addColumn("Trạng thái");
        tbl_qltk.addColumn("Chức năng");

        // Fill phụ huynh
        fillTableTheoVaiTro("Phụ huynh", tbl_qltk);
        JTable tb_data = new JTable();
        tb_data.setRowHeight(30);
        tb_data.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tb_data.getTableHeader().setBackground(new Color(6, 68, 105));
        tb_data.setModel(tbl_qltk);

        // Cấu hình combo box
        String[] options = {"Chọn", "Xoá", "Khoá", "Mở khoá"};
        JComboBox<String> comboBox = new JComboBox<>(options);

// Gắn editor
        tb_data.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(comboBox));

// Gắn renderer
        tb_data.getColumnModel().getColumn(7).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JComboBox<String> cb = new JComboBox<>(options);
            cb.setSelectedItem(value);
            return cb;
        });

        // Đặt bảng vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(tb_data);
        scrollPane.setBounds(0, 100, 630, 300);
        panel.add(scrollPane, BorderLayout.CENTER);

        cbo_hv_trangThai.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String keywordRaw = txt_hv_timKiem.getText();
                String keyword = keywordRaw.equals(placeholder) ? "" : keywordRaw.trim();
                String trangThai = cbo_hv_trangThai.getSelectedItem().toString();
                String thang = cbo_hv_thang.getSelectedItem().toString();
                String nam = cbo_hv_nam.getSelectedItem().toString();

                // Gọi hàm tìm kiếm và lọc dữ liệu
                timKiemLocHVTongHop("Phụ huynh", keyword, trangThai, thang, nam, tbl_qltk);
            }
        });

        cbo_hv_thang.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String keywordRaw = txt_hv_timKiem.getText();
                String keyword = keywordRaw.equals(placeholder) ? "" : keywordRaw.trim();
                String trangThai = cbo_hv_trangThai.getSelectedItem().toString();
                String thang = cbo_hv_thang.getSelectedItem().toString();
                String nam = cbo_hv_nam.getSelectedItem().toString();

                // Gọi hàm tìm kiếm và lọc dữ liệu
                timKiemLocHVTongHop("Phụ huynh", keyword, trangThai, thang, nam, tbl_qltk);
            }
        });

        cbo_hv_nam.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String keywordRaw = txt_hv_timKiem.getText();
                String keyword = keywordRaw.equals(placeholder) ? "" : keywordRaw.trim();
                String trangThai = cbo_hv_trangThai.getSelectedItem().toString();
                String thang = cbo_hv_thang.getSelectedItem().toString();
                String nam = cbo_hv_nam.getSelectedItem().toString();

                // Gọi hàm tìm kiếm và lọc dữ liệu
                timKiemLocHVTongHop("Phụ huynh", keyword, trangThai, thang, nam, tbl_qltk);
            }
        });

        btn_hv_timKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keywordRaw = txt_hv_timKiem.getText();
                String keyword = keywordRaw.equals(placeholder) ? "" : keywordRaw.trim();
                String trangThai = cbo_hv_trangThai.getSelectedItem().toString();
                String thang = cbo_hv_thang.getSelectedItem().toString();
                String nam = cbo_hv_nam.getSelectedItem().toString();

                // Validate keyword
                // Cho phép để trống để hiển thị lại toàn bộ bảng
                if (!keyword.isEmpty()) {
                    if (keyword.length() > 50) {
                        JOptionPane.showMessageDialog(null, "Từ khóa tìm kiếm quá dài (tối đa 50 ký tự).");
                        return;
                    }
                    if (!keyword.matches("[\\p{L}\\p{N}\\s]*")) {
                        JOptionPane.showMessageDialog(null, "Từ khóa chỉ được chứa chữ cái, số và khoảng trắng.");
                        return;
                    }
                }

                timKiemLocHVTongHop("Phụ huynh", keyword, trangThai, thang, nam, tbl_qltk);
            }
        });
        // Custom renderer & editor cho cột cuối (Chức năng)

        tb_data.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"Chọn", "Xoá", "Khoá", "Mở khoá"})) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                JComboBox<String> comboBox = new JComboBox<>(new String[]{"Chọn", "Xoá", "Khoá", "Mở khoá"});

                final boolean[] isHandling = {false}; // ✅ chống gọi lặp

                comboBox.addActionListener(e -> {
                    if (isHandling[0]) {
                        return;
                    }
                    isHandling[0] = true;

                    SwingUtilities.invokeLater(() -> {
                        try {
                            String selected = (String) comboBox.getSelectedItem();
                            if ("Chọn".equals(selected)) {
                                isHandling[0] = false;
                                return;
                            }

                            int currentRow = table.getEditingRow(); // ✅ luôn lấy lại row đúng
                            DefaultTableModel model = (DefaultTableModel) table.getModel();

                            if (currentRow < 0 || currentRow >= model.getRowCount() || model.getColumnCount() < 7) {
                                System.err.println("⚠ Dòng hoặc cột không hợp lệ.");
                                return;
                            }

                            int id = (int) model.getValueAt(currentRow, 0);
                            String currentStatus = model.getValueAt(currentRow, 6).toString().trim();

                            if ("Xoá".equals(selected)) {
                                int confirm = JOptionPane.showConfirmDialog(null,
                                        "Bạn có chắc muốn xóa tài khoản ID: " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    xoaTaiKhoan(id);
                                    if (table.isEditing()) {
                                        table.getCellEditor().stopCellEditing();
                                    }
                                    model.removeRow(currentRow);

                                    int thang = cbo_thang.getSelectedIndex() > 0 ? cbo_thang.getSelectedIndex() : LocalDate.now().getMonthValue();
                                    Integer nam = (cbo_nam.getSelectedIndex() > 0)
                                            ? Integer.parseInt(cbo_nam.getSelectedItem().toString().replace("Năm ", ""))
                                            : LocalDate.now().getYear();

                                    int soLuonghv = demSoLuongTichLuy("Phụ huynh", thang, nam);
                                    lbl_tsph_so_luong.setText(String.valueOf(soLuonghv));

                                    int prevThang = (thang > 1) ? thang - 1 : 12;
                                    int prevNam = (thang > 1) ? nam : nam - 1;
                                    int hvTruoc = demSoLuongTichLuy("Phụ huynh", prevThang, prevNam);

                                    String hvGrowth = (hvTruoc == 0 && soLuonghv > 0) ? "+100%"
                                            : (hvTruoc == 0) ? "0%"
                                                    : ((soLuonghv - hvTruoc >= 0 ? "+" : "") + (soLuonghv - hvTruoc) * 100 / hvTruoc + "%");

                                    lbl_tsph_tang_truong.setText(hvGrowth);

                                    JOptionPane.showMessageDialog(null, "Đã xoá tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } else if ("Khoá".equals(selected)) {
                                if ("Không khóa".equalsIgnoreCase(currentStatus)) {
                                    JOptionPane.showMessageDialog(null, "Tài khoản đã bị khoá trước đó!");
                                } else {
                                    int confirm = JOptionPane.showConfirmDialog(null,
                                            "Bạn có chắc muốn khoá tài khoản ID: " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                                    if (confirm == JOptionPane.YES_OPTION) {
                                        capNhatTrangThaiTaiKhoan(id, "Khóa");
                                        model.setValueAt("Khóa", currentRow, 6);
                                        JOptionPane.showMessageDialog(null, "Tài khoản đã được khoá thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                            } else if ("Mở khoá".equals(selected)) {
                                if ("Hoạt động".equalsIgnoreCase(currentStatus)) {
                                    JOptionPane.showMessageDialog(null, "Tài khoản đã được mở khoá trước đó!");
                                } else {
                                    int confirm = JOptionPane.showConfirmDialog(null,
                                            "Bạn có chắc muốn mở khoá tài khoản ID: " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                                    if (confirm == JOptionPane.YES_OPTION) {
                                        capNhatTrangThaiTaiKhoan(id, "Hoạt động");
                                        model.setValueAt("Hoạt động", currentRow, 6);
                                        JOptionPane.showMessageDialog(null, "Tài khoản đã được mở khoá thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                            }

                            comboBox.setSelectedItem("Chọn"); // reset
                        } finally {
                            isHandling[0] = false;
                        }
                    });
                });

                return comboBox;
            }
        });

        return panel;
    }

    public void xoaTaiKhoan(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            // 1. Xóa liên kết phụ huynh - học sinh (nếu có)
            PreparedStatement xoaLienKet = conn.prepareStatement(
                    "DELETE FROM Phu_Huynh_Hoc_Sinh WHERE ID_Tai_Khoan_Phu_Huynh = ? OR ID_Tai_Khoan_Hoc_Sinh = ?");
            xoaLienKet.setInt(1, id);
            xoaLienKet.setInt(2, id);
            xoaLienKet.executeUpdate();

            // 2. Xóa phản hồi liên quan trong bảng Tra_Loi_FeedBack (nếu tài khoản là Admin)
            PreparedStatement xoaFeedBack = conn.prepareStatement(
                    "DELETE FROM Tra_Loi_FeedBack WHERE ID_Admin = ?");
            xoaFeedBack.setInt(1, id);
            xoaFeedBack.executeUpdate();

            // 3. Xóa tài khoản chính
            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM Tai_Khoan WHERE ID_Tai_Khoan = ?");
            stmt2.setInt(1, id);
            stmt2.executeUpdate();

            System.out.println("✅ Đã xóa tài khoản ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void capNhatTrangThaiTaiKhoan(int id, String trangThai) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Tai_Khoan SET Trang_Thai = ? WHERE ID_Tai_Khoan = ?");
            stmt.setString(1, trangThai);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillTableTheoVaiTro(String role, DefaultTableModel model) {
        try {
           Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT ID_Tai_Khoan, Username, Ho_Va_Ten, Email, Ngay_Dang_Ki, Role, Trang_Thai FROM Tai_Khoan WHERE Role = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();

            model.setRowCount(0); // Clear table
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("ID_Tai_Khoan"),
                    rs.getString("Username"),
                    rs.getString("Ho_Va_Ten"),
                    rs.getString("Email"),
                    rs.getDate("Ngay_Dang_Ki"),
                    rs.getString("Role"),
                    rs.getString("Trang_Thai"),
                    "Chọn" // không dùng new JComboBox!
                });
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void timKiemLocHVTongHop(String role, String keyword, String trangThai, String thang, String nam, DefaultTableModel model) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            StringBuilder sql = new StringBuilder("SELECT ID_Tai_Khoan, Username, Ho_Va_Ten, Email, Ngay_Dang_Ki, Role, Trang_Thai FROM Tai_Khoan WHERE Role = ?");
            if (!keyword.isEmpty()) {
                sql.append(" AND (CAST(ID_Tai_Khoan AS NVARCHAR) LIKE ? OR Ho_Va_Ten LIKE ?)");
            }
            if (!trangThai.equals("Trạng thái")) {
                sql.append(" AND Trang_Thai = ?");
            }
            if (!thang.equals("Tháng")) {
                sql.append(" AND MONTH(Ngay_Dang_Ki) = ?");
            }
            if (!nam.equals("Năm")) {
                sql.append(" AND YEAR(Ngay_Dang_Ki) = ?");
            }

            PreparedStatement stmt = conn.prepareStatement(sql.toString());

            int paramIndex = 1;
            stmt.setString(paramIndex++, role);
            if (!keyword.isEmpty()) {
                stmt.setString(paramIndex++, "%" + keyword + "%");
                stmt.setString(paramIndex++, "%" + keyword + "%");
            }
            if (!trangThai.equals("Trạng thái")) {
                stmt.setString(paramIndex++, trangThai);
            }
            if (!thang.equals("Tháng")) {
                stmt.setInt(paramIndex++, Integer.parseInt(thang.replace("Tháng ", "")));
            }
            if (!nam.equals("Năm")) {
                stmt.setInt(paramIndex++, Integer.parseInt(nam.replace("Năm ", "")));
            }

            ResultSet rs = stmt.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("ID_Tai_Khoan"),
                    rs.getString("Username"),
                    rs.getString("Ho_Va_Ten"),
                    rs.getString("Email"),
                    rs.getDate("Ngay_Dang_Ki"),
                    rs.getString("Role"),
                    rs.getString("Trang_Thai"),
                    "Chọn"
                });
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int demSoLuongTichLuy(String role, int thang, int nam) {
        int count = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT COUNT(*) FROM Tai_Khoan WHERE Role = ? AND "
                    + "((YEAR(Ngay_Dang_Ki) < ?) OR (YEAR(Ngay_Dang_Ki) = ? AND MONTH(Ngay_Dang_Ki) <= ?))";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, role);
            stmt.setInt(2, nam);      // YEAR(Ngay_Dang_Ki) < nam
            stmt.setInt(3, nam);      // YEAR(Ngay_Dang_Ki) = nam
            stmt.setInt(4, thang);    // MONTH(Ngay_Dang_Ki) <= thang

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int demSoLuongTaiKhoan(String role, Integer thang, Integer nam) {
        int count = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT COUNT(*) FROM Tai_Khoan WHERE Role = ?";
            if (thang != null) {
                sql += " AND MONTH(Ngay_Dang_Ki) = ?";
            }
            if (nam != null) {
                sql += " AND YEAR(Ngay_Dang_Ki) = ?";
            }

            PreparedStatement stmt = conn.prepareStatement(sql);
            int index = 1;
            stmt.setString(index++, role);
            if (thang != null) {
                stmt.setInt(index++, thang);
            }
            if (nam != null) {
                stmt.setInt(index++, nam);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public void capNhatThongKe(JLabel lblHocVien, JLabel lblPhuHuynh,
            JLabel lblTangTruongHV, JLabel lblTangTruongPH,
            JComboBox<String> cboThang, JComboBox<String> cboNam) {

        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();

        int thang = cboThang.getSelectedIndex() > 0 ? cboThang.getSelectedIndex() : currentMonth;
        Integer nam = (cboNam.getSelectedIndex() > 0)
                ? Integer.parseInt(cboNam.getSelectedItem().toString().replace("Năm ", ""))
                : null;

        if (nam == null) {
            return;
        }
        int hvTichLuy = demSoLuongTichLuy("Học viên", thang, nam);
        int phTichLuy = demSoLuongTichLuy("Phụ huynh", thang, nam);

        lblHocVien.setText(String.valueOf(hvTichLuy));
        lblPhuHuynh.setText(String.valueOf(phTichLuy));

        int prevThang = (thang > 1) ? thang - 1 : 12;
        int prevNam = (thang > 1) ? nam : nam - 1;

        int hvMoi = demSoLuongTichLuy("Học viên", thang, nam);
        int phMoi = demSoLuongTichLuy("Phụ huynh", thang, nam);

        int hvMoiTruoc = demSoLuongTichLuy("Học viên", prevThang, prevNam);
        int phMoiTruoc = demSoLuongTichLuy("Phụ huynh", prevThang, prevNam);

        String hvGrowth = (hvMoiTruoc == 0 && hvMoi > 0) ? "+100%"
                : (hvMoiTruoc == 0) ? "0%"
                        : ((hvMoi - hvMoiTruoc >= 0 ? "+" : "") + (hvMoi - hvMoiTruoc) * 100 / hvMoiTruoc + "%");

        String phGrowth = (phMoiTruoc == 0 && phMoi > 0) ? "+100%"
                : (phMoiTruoc == 0) ? "0%"
                        : ((phMoi - phMoiTruoc >= 0 ? "+" : "") + (phMoi - phMoiTruoc) * 100 / phMoiTruoc + "%");

        lblTangTruongHV.setText(hvGrowth);
        lblTangTruongPH.setText(phGrowth);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Feedback");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(710, 650);
            frame.setResizable(false);

            QuanLyTaiKhoan quanLyTaiKhoanPanel = new QuanLyTaiKhoan();
            frame.add(quanLyTaiKhoanPanel);

            frame.setLocationRelativeTo(null); // Căn giữa màn hình
            frame.setVisible(true);
        });
    }
}
