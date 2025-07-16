/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;

/**
 *
 * @author Do Tuong Minh
 */
public class RoundButton extends JButton {
    private int diameter; // Đường kính nút
    private Image image; // Hình ảnh

    public RoundButton(String imagePath, int size) {
        this.diameter = size;
        setPreferredSize(new Dimension(diameter, diameter)); // Kích thước cố định
        setContentAreaFilled(false);
        
        setFocusPainted(false);
        setBorderPainted(false);

        // Tải ảnh
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ hình tròn nền
        g2.setColor(Color.DARK_GRAY);
        g2.fillOval(0, 0, diameter, diameter);

        // Vẽ ảnh vào giữa
        if (image != null) {
            int imgSize = (int) (diameter * 1.3); // 60% kích thước nút
            int x = (diameter - imgSize) / 2;
            int y = (diameter - imgSize) / 2;
            g2.drawImage(image, x, y, imgSize, imgSize, this);
        }

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Không vẽ viền
    }
}
