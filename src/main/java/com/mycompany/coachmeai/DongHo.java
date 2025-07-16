/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DongHo extends JPanel {

    private JLabel timerLabel;
    private JButton startButton, pauseButton, resumeButton;
    private JComboBox<String> timeSelector;
    private Timer timer;
    private int remainingTime;
    private boolean isPaused = false;

    public DongHo() {
        giaoDien_DongHo(); // Gọi hàm tạo giao diện
    }

    private void giaoDien_DongHo() {
        this.setLayout(null);

        JPanel whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setBounds(0, 0, 710, 650);
        whitePanel.setLayout(null);
        this.add(whitePanel);

        timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 45));
        timerLabel.setBounds(170, 200, 300, 50);
        whitePanel.add(timerLabel);

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.white);
        controlPanel.setBounds(80, 440, 600, 300);
        controlPanel.setLayout(null);
        whitePanel.add(controlPanel);

        timeSelector = new JComboBox<>(new String[]{"30 phút", "1 giờ", "2 giờ", "3 giờ", "4 giờ", "5 giờ"});
        timeSelector.setBounds(150, 60, 200, 40);
        timeSelector.setBackground(new Color(10, 58, 95));
        timeSelector.setForeground(Color.WHITE);
        timeSelector.setFont(new Font("Arial", Font.BOLD, 15));
        controlPanel.add(timeSelector);

        startButton = new JButton("Bắt Đầu");
        startButton.setBounds(10, 10, 150, 40);
        controlPanel.add(startButton);

        pauseButton = new JButton("Tạm Dừng");
        pauseButton.setBounds(180, 10, 150, 40);
        controlPanel.add(pauseButton);

        resumeButton = new JButton("Kết Thúc");
        resumeButton.setBounds(350, 10, 150, 40);
        controlPanel.add(resumeButton);

        ImageIcon imageIcon = new ImageIcon("D:\\coachmeai\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\nenDongHo.png");
        JLabel backgroundLabel = new JLabel(imageIcon);
        backgroundLabel.setBounds(0, 0, 690, 650);
        whitePanel.add(backgroundLabel);

        startButton.addActionListener(this::startCountdown);
        pauseButton.addActionListener(e -> toggleCountdown());
        resumeButton.addActionListener(e -> resetCountdown());
    }

    private void startCountdown(ActionEvent e) {
        String selectedTime = (String) timeSelector.getSelectedItem();
        switch (selectedTime) {
            case "30 phút":
                remainingTime = 30 * 60;
                break;
            case "1 giờ":
                remainingTime = 60 * 60;
                break;
            case "2 giờ":
                remainingTime = 2 * 60 * 60;
                break;
            case "3 giờ":
                remainingTime = 3 * 60 * 60;
                break;
            case "4 giờ":
                remainingTime = 4 * 60 * 60;
                break;
            case "5 giờ":
                remainingTime = 5 * 60 * 60;
                break;
        }

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(1000, event -> {
            if (remainingTime > 0) {
                remainingTime--;
                updateTimerLabel();
            } else {
                timer.stop();
                JOptionPane.showMessageDialog(null, "Hết thời gian!");
            }
        });
        timer.start();
    }

    private void toggleCountdown() {
        if (timer != null) {
            if (isPaused) {
                timer.start();
                pauseButton.setText("Tạm Dừng");
            } else {
                timer.stop();
                pauseButton.setText("Tiếp Tục");
            }
            isPaused = !isPaused;
        }
    }

    private void resetCountdown() {
        if (timer != null) {
            timer.stop();
        }
        timerLabel.setText("00:00:00");
        pauseButton.setText("Tạm Dừng");
        isPaused = false;
    }

    private void updateTimerLabel() {
        int hours = remainingTime / 3600;
        int minutes = (remainingTime % 3600) / 60;
        int seconds = remainingTime % 60;
        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Đồng Hồ Đếm Ngược");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(710, 650);
            frame.setResizable(false);

            DongHo dongHoPanel = new DongHo();
            frame.add(dongHoPanel);

            frame.setLocationRelativeTo(null); // Căn giữa màn hình
            frame.setVisible(true);
        });
    }

}
