/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import com.mycompany.DAO.DatabaseConnection;
import com.mycompany.entity.NhiemVu;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NhiemVuPhuHuynh extends JPanel {

    private JPanel taskListPanel;
    private JLabel studentLabel;
    private List<String[]> tasks;
    private int idTaiKhoan;
    private RoundedComboBox cboMaHV;
    private int idPhuHuynh;

    private LocalDate selectedDate = LocalDate.now();

    public void setSelectedDate(LocalDate date) {
        this.selectedDate = date;
    }
    
        String url = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";


    public NhiemVuPhuHuynh(int idPhuHuynh) {
        this.idPhuHuynh = idPhuHuynh;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        cboMaHV = new RoundedComboBox(new String[]{}); // ✅ Truyền vào một mảng rỗng ban đầu
        cboMaHV.addItem("Chọn học viên"); // thêm dòng này trước khi load dữ liệu
        loadHocVienToComboBox(); // Khởi tạo combo box học viên trước

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel spaceRight = new JPanel();
        spaceRight.setPreferredSize(new Dimension(25, 0));
        spaceRight.setBackground(Color.WHITE);
        topPanel.add(spaceRight, BorderLayout.EAST);

        studentLabel = new JLabel("Mã học viên");
        studentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        studentLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(studentLabel, BorderLayout.WEST);
        topPanel.add(cboMaHV, BorderLayout.CENTER);

        cboMaHV.addActionListener(e -> {
            String selectedIdStr = (String) cboMaHV.getSelectedItem();
            if (selectedIdStr != null && !selectedIdStr.equals("Chọn học viên")) {
                try {
                    idTaiKhoan = Integer.parseInt(selectedIdStr);
                    // Gọi lại fill để hiển thị nhiệm vụ theo ngày hiện tại
                    fillDanhSachNhiemVuTheoNgay(selectedDate);

                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(topPanel, BorderLayout.NORTH);

        tasks = new ArrayList<>();
        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        taskListPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(taskListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateTaskList() {
        taskListPanel.removeAll();
        for (String[] task : tasks) {
            taskListPanel.add(createTaskCard(task));
            taskListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        taskListPanel.revalidate();
        taskListPanel.repaint();
    }

    private JPanel createTaskCard(String[] taskData) {
        int idNhiemVu = Integer.parseInt(taskData[0]);
        String tieuDe = taskData[1];
        String monHoc = taskData[2];
        String thoiGianTu = taskData[3];
        String thoiGianDen = taskData[4];
        String noiDung = taskData[5];
        String ghiChu = taskData[6];
        int idMonHoc = Integer.parseInt(taskData[7]);
        JPanel card = new JPanel();
        card.setLayout(new GridLayout(6, 1));
        card.setBackground(Color.white);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true)));
        card.setPreferredSize(new Dimension(300, 240));
        card.setMaximumSize(new Dimension(300, 240));

        JLabel titleLabel = new JLabel("Nhiệm vụ: " + taskData[1]);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel subjectLabel = new JLabel("Môn học: " + taskData[2]);
        JLabel timeLabel = new JLabel("Thời gian: " + taskData[3] + " - " + taskData[4]);
        JLabel descriptionLabel = new JLabel("Nội dung: " + taskData[5]);
        JLabel noteLabel = new JLabel("Ghi chú: " + taskData[6]);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton viewButton = new JButton("Chi tiết");

        viewButton.addActionListener((e) -> {
            Timestamp tsTu = Timestamp.valueOf(selectedDate + " " + thoiGianTu + ":00");
            Timestamp tsDen = Timestamp.valueOf(selectedDate + " " + thoiGianDen + ":00");
            String trangThai = "Chưa hoàn thành"; // hoặc lấy đúng từ DB nếu có

            NhiemVu nhiemVu = new NhiemVu();
            nhiemVu.setIdNhiemVu(idNhiemVu);
            nhiemVu.setTieuDe(tieuDe);
            nhiemVu.setNoiDung(noiDung);
            nhiemVu.setTrangThai(trangThai);
            nhiemVu.setGhiChu(ghiChu);
            nhiemVu.setIdMonHoc(idMonHoc);
            nhiemVu.setThoiGianBatDau(tsTu);
            nhiemVu.setThoiGianKetThuc(tsDen);

            TaskEditor editor = new TaskEditor("phuhuynh", nhiemVu, null, this, idTaiKhoan, selectedDate);
            editor.setVisible(true); // mở form
        });

        viewButton.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(viewButton);

        card.add(titleLabel);
        card.add(subjectLabel);
        card.add(timeLabel);
        card.add(descriptionLabel);
        card.add(noteLabel);
        card.add(buttonPanel);

        return card;
    }

    private void loadHocVienToComboBox() {
        String sql = "SELECT ID_Tai_Khoan_Hoc_Sinh "
                + "FROM Phu_Huynh_Hoc_Sinh "
                + "WHERE ID_Tai_Khoan_Phu_Huynh = ? AND Tinh_Trang_Ket_Noi = N'Đã kết nối'";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, this.idPhuHuynh); // truyền ID phụ huynh

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID_Tai_Khoan_Hoc_Sinh");
                cboMaHV.addItem(String.valueOf(id));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshTasks() {
        this.tasks = getTasksFromDatabase();
        updateTaskList();
    }

    private List<String[]> getTasksFromDatabase() {
        List<String[]> taskList = new ArrayList<>();

        String query = "SELECT NV.ID_Nhiem_Vu, NV.Tieu_De, M.Ten AS Mon_Hoc_Ten, "
                + "NV.Thoi_Gian_Bat_Dau, NV.Thoi_Gian_Ket_Thuc, NV.Noi_Dung, NV.Ghi_Chu, NV.ID_Mon_Hoc "
                + "FROM Nhiem_Vu NV "
                + "JOIN Mon_Hoc M ON NV.ID_Mon_Hoc = M.ID_Mon_Hoc "
                + "WHERE NV.ID_Tai_Khoan = ? AND NV.Trang_Thai = N'Chưa hoàn thành'";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, this.idTaiKhoan);
            ResultSet rs = stmt.executeQuery();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            while (rs.next()) {
                Timestamp tsBatDau = rs.getTimestamp("Thoi_Gian_Bat_Dau");
                Timestamp tsKetThuc = rs.getTimestamp("Thoi_Gian_Ket_Thuc");

                String gioBatDau = sdf.format(tsBatDau);
                String gioKetThuc = sdf.format(tsKetThuc);
                if (tsKetThuc.after(now)) {
                    taskList.add(new String[]{
                        String.valueOf(rs.getInt("ID_Nhiem_Vu")),
                        rs.getString("Tieu_De"),
                        rs.getString("Mon_Hoc_Ten"),
                        gioBatDau,
                        gioKetThuc,
                        rs.getString("Noi_Dung"),
                        rs.getString("Ghi_Chu"),
                        String.valueOf(rs.getInt("ID_Mon_Hoc"))
                    });
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // hoặc log
        }

        return taskList;
    }

    public void fillDanhSachNhiemVuTheoNgay(LocalDate ngayDuocChon) {
        taskListPanel.removeAll();

        String ngayStr = ngayDuocChon.toString(); // định dạng yyyy-MM-dd

        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT NV.ID_Nhiem_Vu, NV.Tieu_De, NV.Thoi_Gian_Bat_Dau, NV.Thoi_Gian_Ket_Thuc, "
                    + "NV.Noi_Dung, NV.Ghi_Chu, NV.ID_Mon_Hoc, M.Ten AS Mon_Hoc_Ten "
                    + "FROM Nhiem_Vu NV "
                    + "JOIN Mon_Hoc M ON NV.ID_Mon_Hoc = M.ID_Mon_Hoc "
                    + "WHERE CONVERT(date, NV.Thoi_Gian_Bat_Dau) = ? "
                    + "AND NV.ID_Tai_Khoan = ? "
                    + "AND NV.Trang_Thai = N'Chưa hoàn thành'";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ngayStr);
            stmt.setInt(2, this.idTaiKhoan);

            ResultSet rs = stmt.executeQuery();

            LocalDateTime now = LocalDateTime.now();
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            while (rs.next()) {
                LocalDateTime startTime = rs.getTimestamp("Thoi_Gian_Bat_Dau").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("Thoi_Gian_Ket_Thuc").toLocalDateTime();
                LocalDate taskDate = startTime.toLocalDate();

                // Chỉ hiển thị nếu:
                // - Là ngày khác hôm nay
                // - Hoặc là hôm nay nhưng chưa quá thời gian kết thúc
                if (!taskDate.equals(today) || endTime.isAfter(now)) {
                    String[] taskData = new String[]{
                        String.valueOf(rs.getInt("ID_Nhiem_Vu")),
                        rs.getString("Tieu_De"),
                        rs.getString("Mon_Hoc_Ten"),
                        startTime.toLocalTime().format(formatter),
                        endTime.toLocalTime().format(formatter),
                        rs.getString("Noi_Dung"),
                        rs.getString("Ghi_Chu"),
                        String.valueOf(rs.getInt("ID_Mon_Hoc"))
                    };

                    JPanel taskCard = createTaskCard(taskData);
                    taskListPanel.add(taskCard);
                }
            }

            if (taskListPanel.getComponentCount() == 0) {
                JLabel emptyLabel = new JLabel("Không có nhiệm vụ nào.");
                emptyLabel.setFont(new Font("Arial", Font.ITALIC, 14));
                emptyLabel.setForeground(Color.GRAY);
                emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JPanel wrapper = new JPanel();
                wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
                wrapper.setPreferredSize(new Dimension(300, 300));
                wrapper.add(Box.createVerticalGlue());
                wrapper.add(emptyLabel);
                wrapper.add(Box.createVerticalGlue());
                wrapper.setBackground(Color.WHITE);

                taskListPanel.add(wrapper);
            }

            taskListPanel.revalidate();
            taskListPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý Nhiệm Vụ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 650);
        frame.add(new NhiemVuPhuHuynh(7));
        frame.setVisible(true);
    }
}
