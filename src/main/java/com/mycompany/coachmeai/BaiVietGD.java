/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BaiVietGD extends JFrame {
    public BaiVietGD(String tieuDe, String noiDung, String duongDanHinhAnh) {
        setBackground(Color.WHITE);
        // Cấu hình JFrame
        setResizable(false);
        setTitle("Bài Viết");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel chính chứa tất cả nội dung
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Tiêu đề bài viết
        JLabel titleLabel = new JLabel(tieuDe, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        titleLabel.setBackground(Color.WHITE);

        // Nội dung bài viết (có thể dài)
        JTextArea contentArea = new JTextArea(noiDung);
        contentArea.setBackground(Color.WHITE);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setOpaque(false); // Không có nền
        contentArea.setBorder(BorderFactory.createEmptyBorder()); // Không viền
        
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // ScrollPane cho nội dung văn bản
        

        // Hình ảnh bài viết
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Load ảnh từ đường dẫn
        try {
            BufferedImage originalImage = ImageIO.read(new File(duongDanHinhAnh));
            Image scaledImage = originalImage.getScaledInstance(400, 250, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            imageLabel.setText("Không thể tải ảnh!");
        }

        // Panel chứa nội dung để căn trái
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        contentPanel.add(contentArea, BorderLayout.CENTER);
        contentPanel.setBackground(Color.WHITE);

        // Thêm các thành phần vào mainPanel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(imageLabel, BorderLayout.SOUTH);

        // ScrollPane tổng bọc cả mainPanel
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Thêm ScrollPane vào JFrame
        add(mainScrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
        setVisible(true);
    }
}
