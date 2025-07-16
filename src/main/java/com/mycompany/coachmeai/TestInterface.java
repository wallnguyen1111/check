package com.mycompany.coachmeai;

import com.mycompany.DAO.DatabaseConnection;
import com.mycompany.DAO.DatabaseManager;
import com.mycompany.utils.Auth;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.Border;

public class TestInterface extends JPanel {

    public static String[] cauTraloi;
    private Map<Integer, String> answers = new HashMap<>();
    private ButtonGroup[] buttonGroups;
    private JRadioButton[][] optionButtons;

    public TestInterface(String[][] questions, String stuID, String sub, String taskLab, int id_bai_test, int ID_nhiem_Vu) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        cauTraloi = new String[questions.length];

        // Panel chứa thông tin học viên
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setBackground(Color.WHITE); // Nền trắng

        JLabel studentIdLabel = new JLabel("Mã học viên: " + stuID);
        JLabel subjectLabel = new JLabel("Môn học: " + sub);
        JLabel taskLabel = new JLabel("Nhiệm vụ: " + taskLab);

        Font infoFont = new Font("Arial", Font.BOLD, 14);
        studentIdLabel.setFont(infoFont);
        subjectLabel.setFont(infoFont);
        taskLabel.setFont(infoFont);

        infoPanel.add(studentIdLabel);
        infoPanel.add(subjectLabel);
        infoPanel.add(taskLabel);
        add(infoPanel, BorderLayout.NORTH);

        // Panel chứa các câu hỏi
        JPanel panel = new JPanel();
        panel.setBackground(Color.white); // Nền trắng
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        buttonGroups = new ButtonGroup[questions.length];
        optionButtons = new JRadioButton[questions.length][4];

        for (int i = 0; i < questions.length; i++) {
            JPanel questionPanel = new JPanel(new BorderLayout());

            JLabel questionLabel = new JLabel("<html><b>" + (i + 1) + ". " + questions[i][0] + "</b></html>");
            questionPanel.add(questionLabel, BorderLayout.NORTH);
            questionPanel.setBackground(Color.WHITE); // Nền trắng

            buttonGroups[i] = new ButtonGroup();
            JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            optionsPanel.setBackground(Color.WHITE); // Nền trắng

            for (int j = 0; j < 4; j++) {
                optionButtons[i][j] = new JRadioButton((char) ('A' + j) + ". " + questions[i][j + 1]);
                optionButtons[i][j].setBackground(Color.WHITE); // Nền trắng
                buttonGroups[i].add(optionButtons[i][j]);
                optionsPanel.add(optionButtons[i][j]);
            }
            questionPanel.add(optionsPanel, BorderLayout.CENTER);
            panel.add(questionPanel);
        }
        JPanel panelTong = new JPanel(new BorderLayout());
        Box box = Box.createHorizontalBox();
        JPanel spaceLeft = new JPanel();
        spaceLeft.setPreferredSize(new Dimension(25, 0)); // Chiều rộng 25px, chiều cao 0
        spaceLeft.setBackground(Color.WHITE);

        JPanel spaceTop = new JPanel();
        spaceTop.setPreferredSize(new Dimension(0, 25)); // Chiều rộng 0, chiều cao 25px
        spaceTop.setBackground(Color.WHITE);

        JPanel spaceRight = new JPanel();
        spaceRight.setPreferredSize(new Dimension(25, 0));
        spaceRight.setBackground(Color.WHITE);

        panelTong.add(spaceLeft, BorderLayout.WEST);
        panelTong.add(spaceTop, BorderLayout.NORTH);
        panelTong.add(spaceRight, BorderLayout.EAST);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBackground(Color.WHITE); // Nền trắng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Trái, trên, phải, dưới
        Border outerBorder = BorderFactory.createLineBorder(new Color(3, 39, 87), 2);  // Viền ngoài dày 5px
        Border innerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10); // Khoảng cách bên trong
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        scrollPane.setBorder(compoundBorder);
        scrollPane.getViewport().setBackground(Color.WHITE); // Đảm bảo nền trắng khi cuộn
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelTong.add(scrollPane, BorderLayout.CENTER);
        add(panelTong, BorderLayout.CENTER);

        // Panel chứa nút "Nộp bài"
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        JButton submitButton = new RoundedButton("📥 Nộp bài");
        submitButton.setForeground(Color.WHITE);
        bottomPanel.add(submitButton);
        add(bottomPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAllQuestionsAnswered()) {
                    JOptionPane.showMessageDialog(null, "⚠ Bạn chưa chọn đầy đủ các câu hỏi!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                for (int i = 0; i < questions.length; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (optionButtons[i][j].isSelected()) {
//                            answers.put(i, questions[i][j + 1]);
//                            cauTraloi[i] = questions[i][j + 1];  // Lưu đáp án vào biến static
                            answers.put(i, String.valueOf((char) ('A' + j)));  // Thay đổi ở đây
                            cauTraloi[i] = String.valueOf((char) ('A' + j));  // Thêm dòng này để lưu vào mảng static

                        }
                    }
                }

                float diem = DatabaseManager.ChamDiem(cauTraloi, id_bai_test);
                if (diem >= 8) {
                    boolean dauHieu = true;
                    JOptionPane.showMessageDialog(null, "✅ Bài làm đã được nộp!");
                    if (dauHieu) {
                        try (Connection conn = DatabaseConnection.getConnection()) {
                            String sql = "UPDATE Nhiem_Vu SET Trang_Thai = N'Hoàn thành' WHERE ID_Nhiem_Vu=?";
                            PreparedStatement stmt = conn.prepareStatement(sql);
                            stmt.setInt(1, ID_nhiem_Vu);
                            stmt.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Đã hoàn thành nhiệm vụ!");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật trạng thái: " + ex.getMessage());
                        }
                    } else {
                        System.out.println("bai lam chua duoc nop");
                    }
                    trangchu.addPanel(new TestResultPanel(DatabaseManager.getTestans(cauTraloi, id_bai_test), stuID, sub, taskLab, String.valueOf(diem)), "ket_qua");

                } else {
                    JOptionPane.showMessageDialog(null, "Bạn phải hoàn thành trên 80%");
                }
            }
        });
    }

    private boolean isAllQuestionsAnswered() {
        for (int i = 0; i < buttonGroups.length; i++) {
            if (buttonGroups[i].getSelection() == null) {
                return false;
            }
        }
        return true;
    }

}
