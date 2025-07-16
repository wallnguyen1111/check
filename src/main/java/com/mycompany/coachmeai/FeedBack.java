/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import com.mycompany.DAO.DatabaseConnection;
import com.mycompany.entity.TaiKhoan;
import com.mycompany.utils.Auth;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FeedBack extends JPanel {

    static String connectionUrl = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";

    private JFrame frame;
    private JPanel feedbackPanel;
    private JScrollPane scrollPane;

    public FeedBack() {
        giaoDien_feedback();
    }

    //ModelTaiKhoan user = new ModelTaiKhoan(10, "Admin", "AD12345678", "Nguyen Van A", "0972684163", "admin@gmail.com", "Admin", "Hoạt động", "C:\\Users\\nguye\\OneDrive\\Pictures\\Screenshots\\Screenshot 2023-08-27 092627.png", Date.valueOf("2025-03-19"));
    TaiKhoan user = Auth.getUser();

    public void giaoDien_feedback() {
        this.setLayout(null);

        JPanel whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setBounds(0, 0, 710, 650);
        whitePanel.setLayout(null);

        // Tiêu đề
        JLabel titleLabel = new JLabel("FEEDBACK");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setBounds(250, 20, 300, 50);
        whitePanel.add(titleLabel);

        // Tạo panel chứa feedback
        feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
        //feedbackPanel.setBackground(Color.white);

        // ScrollPane chứa feedbackPanel
        scrollPane = new JScrollPane(feedbackPanel);
        scrollPane.setBounds(20, 80, 650, 500);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        whitePanel.add(scrollPane);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        loadFeedback(); // Load dữ liệu từ CSDL

        this.add(whitePanel);
        this.setVisible(true);
    }

    private void loadFeedback() {
        feedbackPanel.removeAll();

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(
                "SELECT ID_FeedBack, Noi_Dung_FeedBack, Ngay_Gui, ID_Tai_Khoan "
                + "FROM FeedBack ORDER BY Ngay_Gui DESC")) {

            while (rs.next()) {
                int feedbackID = rs.getInt("ID_FeedBack");
                String content = rs.getString("Noi_Dung_FeedBack");
                String date = rs.getString("Ngay_Gui");
                int userID = rs.getInt("ID_Tai_Khoan");

                // Gọi hàm hiển thị, không cần truyền phản hồi nữa
                addFeedbackItem(feedbackID, userID, content, date);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        feedbackPanel.revalidate();
        feedbackPanel.repaint();
    }

    private void addFeedbackItem(int feedbackID, int userID, String content, String date) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Feedback từ tài khoản: " + userID));
        panel.setBackground(Color.WHITE);

        // Hiển thị nội dung feedback
        JTextArea txtContent = new JTextArea("Ngày gửi: " + date + "\nNội dung: " + content);
        txtContent.setWrapStyleWord(true);
        txtContent.setLineWrap(true);
        txtContent.setEditable(false);
        txtContent.setBackground(Color.WHITE);
        txtContent.setFont(new Font("Arial", Font.PLAIN, 14));
        txtContent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane contentScroll = new JScrollPane(txtContent);
        contentScroll.setBorder(null);

        // Lấy danh sách các phản hồi từ CSDL
        StringBuilder replyText = new StringBuilder();
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(
                "SELECT Noi_Dung_Tra_Loi FROM Tra_Loi_FeedBack WHERE ID_FeedBack = ? ORDER BY Thoi_Gian ASC")) {

            stmt.setInt(1, feedbackID);
            ResultSet rs = stmt.executeQuery();
            int replyCount = 1;
            while (rs.next()) {
                String reply = rs.getString("Noi_Dung_Tra_Loi");
                replyText.append("Trả lời ").append(replyCount++).append(": ").append(reply).append("\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JTextArea txtReply = new JTextArea(replyText.toString().trim());
        txtReply.setWrapStyleWord(true);
        txtReply.setLineWrap(true);
        txtReply.setEditable(false);
        txtReply.setBackground(Color.WHITE);
        txtReply.setFont(new Font("Arial", Font.PLAIN, 14));
        txtReply.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane replyScroll = new JScrollPane(txtReply);
        replyScroll.setBorder(null);
        replyScroll.setPreferredSize(new Dimension(400, 150));
        replyScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Gộp nội dung và phản hồi
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(contentScroll, BorderLayout.NORTH);
        contentPanel.add(replyScroll, BorderLayout.CENTER);

        // Nút Trả Lời & Xóa
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnReply = new JButton("Trả Lời");
        JButton btnDelete = new JButton("Xóa");

        btnReply.setBackground(Color.decode("#064469"));
        btnReply.setForeground(Color.WHITE);
        btnReply.setFont(new Font("Arial", Font.BOLD, 12));

        btnDelete.setBackground(Color.decode("#064469"));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 12));

        btnReply.addActionListener(e -> replyFeedback(feedbackID, userID));
        btnDelete.addActionListener(e -> deleteFeedback(feedbackID));

        buttonPanel.add(btnReply);
        buttonPanel.add(btnDelete);

        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        feedbackPanel.add(panel);
    }

    private void replyFeedback(int feedbackID, int userID) {
        JTextArea textArea = new JTextArea(5, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        String placeholder = "Phản hồi tại đây...";
        textArea.setText(placeholder);
        textArea.setForeground(Color.GRAY);

        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        textArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(placeholder)) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().trim().isEmpty()) {
                    textArea.setText(placeholder);
                    textArea.setForeground(Color.GRAY);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);
        Object[] options = {"Gửi", "Hủy"};

        int option = JOptionPane.showOptionDialog(
                null, scrollPane, "Nhập nội dung phản hồi:",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (option == 0) {
            String reply = textArea.getText().trim();

            if (!reply.isEmpty() && !reply.equals(placeholder)) {
                try (Connection conn = DatabaseConnection.getConnection()) {
                    // Insert trả lời
                    try (PreparedStatement pstmt = conn.prepareStatement(
                            "INSERT INTO Tra_Loi_FeedBack (ID_FeedBack, Noi_Dung_Tra_Loi, Thoi_Gian, ID_Admin) VALUES (?, ?, GETDATE(), ?)",
                            Statement.RETURN_GENERATED_KEYS)) {

                        pstmt.setInt(1, feedbackID);
                        pstmt.setString(2, reply);
                        pstmt.setInt(3, user.getID_Tai_Khoan());

                        int affectedRows = pstmt.executeUpdate();

                        if (affectedRows > 0) {
                            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    int idTraLoi = generatedKeys.getInt(1);

                                    // Thêm vào bảng thông báo
                                    try (PreparedStatement stmt = conn.prepareStatement(
                                            "INSERT INTO Thong_Bao_Da_Gui(ID_Tra_Loi, ID_Tai_Khoan, Thoi_Gian_Gui) VALUES (?, ?, GETDATE())")) {
                                        stmt.setInt(1, idTraLoi);
                                        stmt.setInt(2, userID);
                                        stmt.executeUpdate();
                                    }
                                }
                            }

                            JOptionPane.showMessageDialog(null, "Phản hồi đã được gửi!");

                            // Làm mới danh sách
                            loadFeedback();
                        } else {
                            JOptionPane.showMessageDialog(null, "Không thể gửi phản hồi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nội dung phản hồi không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteFeedback(int feedbackID) {
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa feedback này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement("DELETE FROM FeedBack WHERE ID_FeedBack = ?")) {
                pstmt.setInt(1, feedbackID);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Feedback đã bị xóa!");
                loadFeedback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Feedback");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(710, 650);
            frame.setResizable(false);

            FeedBack feedbackPanel = new FeedBack();
            frame.add(feedbackPanel);

            frame.setLocationRelativeTo(null); // Căn giữa màn hình
            frame.setVisible(true);
        });
    }
}
