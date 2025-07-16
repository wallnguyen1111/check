/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.time.LocalDate;
import java.time.YearMonth;

public class LichHocGD extends JPanel {

    private JLabel monthLabel;
    private JPanel daysPanel;
    private LocalDate selectedDate;
    private YearMonth currentMonth;
    private NgayDuocChonListener ngayListener;

    public LichHocGD(int width, int height) {
        setLayout(new BorderLayout());
        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMaximumSize(size);
        currentMonth = YearMonth.now();
        selectedDate = LocalDate.now();

        // Panel chứa tiêu đề (tháng, năm, nút chuyển)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        JButton prevButton = new JButton("◀");
        JButton nextButton = new JButton("▶");
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 20));

        prevButton.addActionListener(e -> changeMonth(-1));
        nextButton.addActionListener(e -> changeMonth(1));

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);

        // Panel chứa các ngày
        daysPanel = new JPanel(new GridLayout(0, 7));
        daysPanel.setBackground(Color.WHITE);
        add(headerPanel, BorderLayout.NORTH);
        add(daysPanel, BorderLayout.CENTER);

        updateCalendar();

    }

    private void changeMonth(int delta) {
        currentMonth = currentMonth.plusMonths(delta);
        updateCalendar();
    }

    private void updateCalendar() {
        monthLabel.setText(currentMonth.getMonth() + " " + currentMonth.getYear());
        daysPanel.removeAll();

        String[] weekDays = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        for (String day : weekDays) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            daysPanel.add(label);
        }

        int firstDay = currentMonth.atDay(1).getDayOfWeek().getValue() % 7;
        int totalDays = currentMonth.lengthOfMonth();

        // Thêm khoảng trống trước ngày 1
        for (int i = 0; i < firstDay; i++) {
            daysPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= totalDays; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setBackground(Color.WHITE);
            dayButton.setFont(new Font("Arial", Font.PLAIN, 14));
            dayButton.setFocusPainted(false);

            LocalDate date = currentMonth.atDay(day);
            if (date.equals(selectedDate)) {
                dayButton.setBackground(Color.CYAN);
            }

            dayButton.addActionListener(e -> {
                selectedDate = date;
                if (ngayListener != null) {
                    ngayListener.onNgayDuocChon(selectedDate);
                }

                System.out.println(date);
                updateCalendar();
            });

            daysPanel.add(dayButton);
        }

        daysPanel.revalidate();
        daysPanel.repaint();

    }

    public void setNgayDuocChonListener(NgayDuocChonListener listener) {
        this.ngayListener = listener;
    }

}
