/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import com.mycompany.DAO.DatabaseConnection;
import com.mycompany.entity.NhiemVu;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class NhiemVuHocVien extends JPanel {

    private JPanel taskListPanel;
    private JLabel studentLabel;
    private RoundButton addTaskButton;
    private List<String[]> tasks;
    private int idTaiKhoan;
    private LocalDate selectedDate = LocalDate.now();

    public void setSelectedDate(LocalDate date) {
        this.selectedDate = date;
    }

    String url = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";

    public NhiemVuHocVien(int idTaiKhoan) {
        setLayout(new BorderLayout());
        this.idTaiKhoan = idTaiKhoan;

        studentLabel = new JLabel("Học viên: HV" + getStudentNameById(idTaiKhoan));
        studentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        studentLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(studentLabel, BorderLayout.NORTH);

        // tasks = getSampleTasks(); ❌
        tasks = getTasksFromDatabase(); // ✅

        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        taskListPanel.setBackground(Color.WHITE);
        updateTaskList();

        JScrollPane scrollPane = new JScrollPane(taskListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        addTaskButton = new RoundButton("C:\\Users\\Do Tuong Minh\\Desktop\\hihi\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\them_btn.png", 45);
        addTaskButton.setFont(new Font("Arial", Font.BOLD, 20));
        addTaskButton.setPreferredSize(new Dimension(50, 50));
        addTaskButton.setFocusPainted(false);
        addTaskButton.setBackground(Color.BLACK);
        addTaskButton.setForeground(Color.WHITE);
        addTaskButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        addTaskButton.addActionListener(e -> {
            // Kiểm tra nếu ngày được chọn là trong quá khứ thì không cho thêm
            if (selectedDate.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this,
                        "Không thể thêm nhiệm vụ cho ngày trong quá khứ!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Nếu ngày hợp lệ thì mở form thêm nhiệm vụ
            TaskEditor editor = new TaskEditor("them", null, this, null, idTaiKhoan, selectedDate);
            editor.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    fillDanhSachNhiemVuTheoNgay(selectedDate);
                }
            });

            editor.setVisible(true); // Mở form sau khi gắn listener
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addTaskButton);
        add(buttonPanel, BorderLayout.SOUTH);
//        muốn real hơn nữa chỉnh xuống 5000 5 giây cho nó chuẩn real mà nó ngốn lắm
        Timer autoRefreshTimer = new Timer(10000, e -> {
            capNhatTrangThaiQuaHan();         // 👉 Cập nhật trạng thái quá hạn
            fillDanhSachNhiemVuTheoNgay(selectedDate); // 👉 Làm mới danh sách nếu có thay đổi
            kiemTraNhiemVuSapBatDau();
        });
        autoRefreshTimer.start();
    }

    private void updateTaskList() {
        taskListPanel.removeAll();

        if (tasks == null || tasks.isEmpty()) {
            JPanel emptyPanel = new JPanel(new BorderLayout());
            emptyPanel.setPreferredSize(new Dimension(300, 300)); // giữ khung cao 300px
            emptyPanel.setMaximumSize(new Dimension(300, 300));
            emptyPanel.setBackground(Color.WHITE);

            JLabel emptyLabel = new JLabel("Không có nhiệm vụ nào", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);

            emptyPanel.add(emptyLabel, BorderLayout.CENTER);

            taskListPanel.add(Box.createVerticalStrut(10)); // ép một khoảng trống để không bị co lại
            taskListPanel.add(emptyPanel);
            taskListPanel.add(Box.createVerticalGlue()); // giữ giãn
        } else {
            for (String[] task : tasks) {
                taskListPanel.add(createTaskCard(task));
                taskListPanel.add(Box.createVerticalStrut(10));
            }
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
//        card.setBackground(new Color(220, 235, 245));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true)));
        card.setPreferredSize(new Dimension(300, 240));
        card.setMaximumSize(new Dimension(300, 240));

        JLabel titleLabel = new JLabel("Nhiệm vụ: " + tieuDe);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel subjectLabel = new JLabel("Môn học: " + monHoc);
        JLabel timeLabel = new JLabel("Thời gian: " + thoiGianTu + " - " + thoiGianDen);
        JLabel descriptionLabel = new JLabel("Nội dung: " + noiDung);
        JLabel noteLabel = new JLabel("Ghi chú: " + ghiChu);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton viewButton = new JButton("Xem chi tiết");

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

            TaskEditor editor = new TaskEditor("chitiet", nhiemVu, this, null, idTaiKhoan, selectedDate);
            editor.setVisible(true); // mở form
        });

        JButton deleteButton = new JButton("Xóa");

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhiệm vụ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = xoaNhiemVu(idNhiemVu);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    fillDanhSachNhiemVuTheoNgay(selectedDate);
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                }
            }
        });

        viewButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);

        card.add(titleLabel);
        card.add(subjectLabel);
        card.add(timeLabel);
        card.add(descriptionLabel);
        card.add(noteLabel);
        card.add(buttonPanel);

        return card;
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

    public JPanel getPanel() {
        return this;
    }

    public void refreshTasks() {
        this.tasks = getTasksFromDatabase();
        updateTaskList();
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

    public void capNhatTrangThaiQuaHan() {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "UPDATE Nhiem_Vu SET Trang_Thai = N'Qua han' "
                    + "WHERE Thoi_Gian_Ket_Thuc < GETDATE() "
                    + "AND Trang_Thai = N'Chưa hoàn thành' "
                    + "AND ID_Tai_Khoan = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.idTaiKhoan);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean xoaNhiemVu(int idNhiemVu) {
        String sql = "DELETE FROM Nhiem_Vu WHERE ID_Nhiem_Vu = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idNhiemVu);
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getStudentNameById(int idTaiKhoan) {
        String name = "";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT ID_Tai_Khoan FROM Tai_Khoan WHERE ID_Tai_Khoan = ?")) {
            stmt.setInt(1, idTaiKhoan);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("ID_Tai_Khoan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    private Set<Integer> danhSachDaThongBao = new HashSet<>(); // Lưu ID đã gửi thông báo

    private void kiemTraNhiemVuSapBatDau() {
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(
                "SELECT ID_Nhiem_Vu, ID_Tai_Khoan, Tieu_De, Thoi_Gian_Bat_Dau "
                + "FROM Nhiem_Vu "
                + "WHERE DATEDIFF(MINUTE, GETDATE(), Thoi_Gian_Bat_Dau) <= 5 "
                + "AND DATEDIFF(MINUTE, GETDATE(), Thoi_Gian_Bat_Dau) > 0 "
                + "AND Trang_Thai = N'Chưa hoàn thành'")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idNhiemVu = rs.getInt("ID_Nhiem_Vu");
                int idTaiKhoan = rs.getInt("ID_Tai_Khoan");
                String tieuDe = rs.getString("Tieu_De");

                // Nếu nhiệm vụ này đã có thông báo trước đó, thì bỏ qua
                if (danhSachDaThongBao.contains(idNhiemVu)) {
                    continue;
                }

                // Thêm thông báo mới
                String noiDung = "Nhiệm vụ '" + tieuDe + "' sắp tới rồi bạn hãy vào kiểm tra nhé!";
                themThongBao(idNhiemVu, noiDung);

                // Đánh dấu nhiệm vụ đã được thông báo
                danhSachDaThongBao.add(idNhiemVu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void themThongBao(int idNhiemVu, String tieuDe) {
        String insertThongBao = "INSERT INTO Data_Thong_Bao (Tieu_De, Noi_Dung) VALUES (?, ?)";
        String insertThongBaoDaGui = "INSERT INTO Thong_Bao_Da_Gui (ID_Thong_Bao, ID_Tra_Loi, ID_Tai_Khoan, Thoi_Gian_Gui) VALUES (?, NULL, ?, GETDATE())";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmtThongBao = conn.prepareStatement(insertThongBao, Statement.RETURN_GENERATED_KEYS); PreparedStatement stmtThongBaoDaGui = conn.prepareStatement(insertThongBaoDaGui)) {

            // Thêm vào bảng Thong_Bao
            stmtThongBao.setString(1, "Nhiệm vụ sắp bắt đầu");
            stmtThongBao.setString(2, "Nhiệm vụ '" + tieuDe + "' sẽ bắt đầu trong 5 phút!");
            stmtThongBao.executeUpdate();
            // Lấy ID_Thong_Bao vừa chèn
            ResultSet rs = stmtThongBao.getGeneratedKeys();
            if (rs.next()) {
                int idThongBao = rs.getInt(1);

                // Chèn vào bảng Thong_Bao_Da_Gui
                stmtThongBaoDaGui.setInt(1, idThongBao);
                stmtThongBaoDaGui.setInt(2, this.idTaiKhoan);
                stmtThongBaoDaGui.executeUpdate();
                System.out.println("✅ Đã thêm thông báo: " + tieuDe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý Nhiệm Vụ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 650);
        frame.add(new NhiemVuHocVien(1));
        frame.setVisible(true);
    }
}
