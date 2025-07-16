/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class RoundedComboBox extends JComboBox<String> {
    public RoundedComboBox(String[] items) {
        super(items);
        setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton(" V");
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setContentAreaFilled(false);
                return button;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(Color.WHITE); // Đặt màu nền cho JComboBox thành trắng
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });
        
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setForeground(Color.BLACK); // Đổi màu chữ thành đen để phù hợp với nền trắng
        setBackground(Color.WHITE); // Đảm bảo màu nền của JComboBox là trắng
        setOpaque(true); // Đảm bảo màu trắng luôn hiển thị
        setFont(new Font("Arial", Font.BOLD, 14));
        
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                label.setBackground(Color.WHITE); // Luôn giữ nền trắng
                label.setForeground(isSelected ? Color.BLUE : Color.BLACK);
                label.setOpaque(true);
                return label;
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rounded ComboBox");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new FlowLayout());

            String[] items = {"Option 1", "Option 2", "Option 3"};
            RoundedComboBox comboBox = new RoundedComboBox(items);
            
            JPanel panel = new JPanel(); // Không override paintComponent để tránh thay đổi màu nền
            panel.setLayout(new BorderLayout());
            panel.add(comboBox, BorderLayout.CENTER);
            panel.setOpaque(true);
            panel.setBackground(Color.WHITE); // Đảm bảo màu nền của panel là trắng
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            frame.add(panel);
            frame.getContentPane().setBackground(Color.WHITE); // Đảm bảo cả frame có nền trắng
            frame.setVisible(true);
        });
    }

    
}