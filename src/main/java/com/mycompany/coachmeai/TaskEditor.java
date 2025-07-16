/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import com.mycompany.DAO.DatabaseConnection;
import com.mycompany.DAO.DatabaseManager;
import com.mycompany.entity.NhiemVu;
import com.mycompany.utils.Auth;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class RoundedButton extends JButton {

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ nền bo tròn
        g2.setColor(new Color(0, 51, 102)); // Màu xanh đậm
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        // Vẽ viền (nếu cần)
        g2.setColor(getBackground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Không cần vẽ viền
    }
}

public class TaskEditor extends JFrame {

    private JTextField txtTitle, txtTask;
    private JTextArea txtNotes;
    private JComboBox<String> cboSubject;
    private JSpinner spnStart, spnEnd;
    private Map<String, Integer> monHocMap = new HashMap<>();
    private NhiemVu currentNhiemVu;
    private NhiemVuHocVien parentUI;
    private int idTaiKhoan;
    private LocalDate ngayDuocChon;
    private NhiemVuPhuHuynh parentPhuHuynh;

    public TaskEditor(String mode, NhiemVu nhiemVu, NhiemVuHocVien parentUI, NhiemVuPhuHuynh parentPhuHuynh, int idTaiKhoan, LocalDate ngayDuocChon) {
        this.currentNhiemVu = nhiemVu;
        this.parentUI = parentUI;
        this.parentPhuHuynh = parentPhuHuynh;
        this.idTaiKhoan = idTaiKhoan;
        this.ngayDuocChon = ngayDuocChon;
        System.out.println("✅ Đã gọi đúng constructor mới của TaskEditor!");

        System.out.println("Ngày được truyền vào TaskEditor: " + ngayDuocChon);

        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTitle = new JTextField(20);
        txtTask = new JTextField(20);
        txtNotes = new JTextArea(3, 20);
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtNotes);
        cboSubject = new JComboBox<>();

        spnStart = new JSpinner(new SpinnerDateModel());
        spnStart.setEditor(new JSpinner.DateEditor(spnStart, "HH:mm"));

        spnEnd = new JSpinner(new SpinnerDateModel()); // ✅ Thêm dòng này
        spnEnd.setEditor(new JSpinner.DateEditor(spnEnd, "HH:mm")); // ✅ Và dòng này

        LocalTime gioBatDau;
        LocalTime gioKetThuc;
        LocalDate today = LocalDate.now();

        if (ngayDuocChon.isEqual(today)) {
            gioBatDau = LocalTime.now().withSecond(0).withNano(0);
            gioKetThuc = gioBatDau.plusHours(1); // Ví dụ kết thúc sau 1 giờ
        } else {
            gioBatDau = LocalTime.of(8, 0);
            gioKetThuc = LocalTime.of(8, 0);
        }

        LocalDateTime dateTimeStart = LocalDateTime.of(ngayDuocChon, gioBatDau);
        Date dateStart = Date.from(dateTimeStart.atZone(ZoneId.systemDefault()).toInstant());
        spnStart.setValue(dateStart);

        LocalDateTime dateTimeEnd = LocalDateTime.of(ngayDuocChon, gioKetThuc);
        Date dateEnd = Date.from(dateTimeEnd.atZone(ZoneId.systemDefault()).toInstant());
        spnEnd.setValue(dateEnd);

        addField(panel, gbc, 0, "Tiêu đề", txtTitle);
        addField(panel, gbc, 1, "Môn học", cboSubject);
        addTimeField(panel, gbc, 2); // bên trong hàm này chắc có spnStart và spnEnd
        addField(panel, gbc, 3, "Nhiệm vụ", txtTask);
        addField(panel, gbc, 4, "Ghi chú", scrollPane);

        JPanel buttonPanel = new JPanel();
        if (mode.equals("them")) {
            setTitle("Thêm nhiệm vụ");
            JButton btnAdd = new JButton("Thêm");
            btnAdd.addActionListener(e -> themNhiemVu());
            buttonPanel.add(btnAdd);
        } else if (mode.equals("chitiet") && nhiemVu != null) {
            setTitle("Chi tiết nhiệm vụ");
            fillData(nhiemVu);
            JButton btnUpdate = new JButton("Cập nhật");
            btnUpdate.addActionListener(e -> capNhatNhiemVu());
            JButton btnComplete = new JButton("Hoàn thành");
            btnComplete.addActionListener(e -> hoanThanhNhiemVu());
            buttonPanel.add(btnUpdate);
            buttonPanel.add(btnComplete);
        } else if (mode.equals("phuhuynh") && nhiemVu != null) {
            setTitle("Chi tiết nhiệm vụ");
            fillData(nhiemVu);
        }

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);
        loadMonHoc();  // Đảm bảo bạn đã load tất cả môn học vào monHocMap
        for (Map.Entry<String, Integer> entry : monHocMap.entrySet()) {
            if (mode.equals("chitiet") && nhiemVu != null) {
                // So sánh ID môn học từ NhiemVu với value của monHocMap
                if (entry.getValue().equals(nhiemVu.getIdMonHoc())) {
                    // Set tên môn học vào ComboBox
                    cboSubject.setSelectedItem(entry.getKey());
                    break; // Thoát khỏi vòng lặp khi đã tìm thấy môn học
                }
            }
        }
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int y, String label, Component comp) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        panel.add(comp, gbc);
    }

    private void addTimeField(JPanel panel, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Thời gian"), gbc);
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePanel.add(spnStart);
        timePanel.add(new JLabel("-"));
        timePanel.add(spnEnd);
        gbc.gridx = 1;
        panel.add(timePanel, gbc);
    }

    private void loadMonHoc() {
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT ID_Mon_Hoc, Ten FROM Mon_Hoc"); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID_Mon_Hoc");
                String ten = rs.getString("Ten");
                cboSubject.addItem(ten);
                monHocMap.put(ten, id);
                // In debug khi thêm vào monHocMap
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void themNhiemVu() {
        if (!validateForm(null)) {
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Lấy thông tin từ các trường trong form
            String title = txtTitle.getText().trim();
            String task = txtTask.getText().trim();
            String notes = txtNotes.getText().trim();
            int subjectId = monHocMap.get(cboSubject.getSelectedItem());

            // Lấy thời gian bắt đầu và kết thúc từ DatePicker
            Date startTime = (Date) spnStart.getValue();
            Date endTime = (Date) spnEnd.getValue();
            ZoneId zone = ZoneId.systemDefault();

            LocalTime startLocalTime = startTime.toInstant().atZone(zone).toLocalTime();
            LocalTime endLocalTime = endTime.toInstant().atZone(zone).toLocalTime();

            LocalDateTime startDateTime = LocalDateTime.of(ngayDuocChon, startLocalTime);
            LocalDateTime endDateTime = LocalDateTime.of(ngayDuocChon, endLocalTime);

            System.out.println("🟢 [THÊM] Ngày được chọn: " + ngayDuocChon);
            System.out.println("🕒 Bắt đầu: " + startDateTime);
            System.out.println("🕒 Kết thúc: " + endDateTime);

            // Nếu thời gian kết thúc nhỏ hơn bắt đầu => cộng thêm 1 ngày
            if (endDateTime.isBefore(startDateTime)) {
                endDateTime = endDateTime.plusDays(1);
            }

            // Kiểm tra trùng nhiệm vụ
            if (isDuplicateTask(title, subjectId, task, notes, startDateTime, endDateTime, null)) {
                JOptionPane.showMessageDialog(this, "Nhiệm vụ này đã tồn tại! Vui lòng thay đổi ít nhất một thông tin.");
                return;
            }

            // Thực hiện thêm nhiệm vụ vào cơ sở dữ liệu nếu không trùng
            String sql = "INSERT INTO Nhiem_Vu (ID_Tai_Khoan, ID_Mon_Hoc, Tieu_De, Noi_Dung, Ghi_Chu, Thoi_Gian_Bat_Dau, Thoi_Gian_Ket_Thuc, Trang_Thai) VALUES (?, ?, ?, ?, ?, ?, ?, N'Chưa hoàn thành')";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idTaiKhoan);
            stmt.setInt(2, subjectId);
            stmt.setString(3, title);
            stmt.setString(4, task);
            stmt.setString(5, notes);
            stmt.setTimestamp(6, Timestamp.valueOf(startDateTime));
            stmt.setTimestamp(7, Timestamp.valueOf(endDateTime));

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Thêm nhiệm vụ thành công!");
                if (parentUI != null) {
                    parentUI.refreshTasks();
                    parentUI.fillDanhSachNhiemVuTheoNgay(ngayDuocChon); // ✅ đúng với ngày được chọn
                }
                dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm: " + e.getMessage());
        }
    }

    private void fillData(NhiemVu nv) {
        txtTitle.setText(nv.getTieuDe());
        txtTask.setText(nv.getNoiDung());
        txtNotes.setText(nv.getGhiChu());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        spnStart.setValue(nv.getThoiGianBatDau());
        spnEnd.setValue(nv.getThoiGianKetThuc());

        // Debug để kiểm tra ID môn học
        System.out.println("Filling data for NhiemVu ID: " + nv.getIdMonHoc());

        // Lặp qua danh sách các môn học
        for (Map.Entry<String, Integer> entry : monHocMap.entrySet()) {
            System.out.println("Comparing: entry.getValue() = " + entry.getValue() + " with nv.getIdMonHoc() = " + nv.getIdMonHoc());

            if (entry.getValue().equals(nv.getIdMonHoc())) {
                cboSubject.setSelectedItem(entry.getKey());
                System.out.println("Selected Mon Hoc: " + entry.getKey()); // Kiểm tra môn học đã được chọn
                break;
            }
        }

        // Kiểm tra lại trạng thái ComboBox
        System.out.println("Selected Mon Hoc in ComboBox: " + cboSubject.getSelectedItem());
    }

    private void capNhatNhiemVu() {
        if (!validateForm(currentNhiemVu.getIdNhiemVu())) {
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Nhiem_Vu SET Tieu_De=?, Noi_Dung=?, Ghi_Chu=?, Thoi_Gian_Bat_Dau=?, Thoi_Gian_Ket_Thuc=?, ID_Mon_Hoc=? WHERE ID_Nhiem_Vu=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, txtTitle.getText().trim());
            stmt.setString(2, txtTask.getText().trim());
            stmt.setString(3, txtNotes.getText().trim());

            Date startTime = (Date) spnStart.getValue();
            Date endTime = (Date) spnEnd.getValue();
            ZoneId zone = ZoneId.systemDefault();

            LocalTime startLocalTime = startTime.toInstant().atZone(zone).toLocalTime();
            LocalTime endLocalTime = endTime.toInstant().atZone(zone).toLocalTime();

            LocalDateTime startDateTime = LocalDateTime.of(ngayDuocChon, startLocalTime);
            LocalDateTime endDateTime = LocalDateTime.of(ngayDuocChon, endLocalTime);
            System.out.println("🟡 [CẬP NHẬT] Ngày được chọn: " + ngayDuocChon);
            System.out.println("🕒 Bắt đầu: " + startDateTime);
            System.out.println("🕒 Kết thúc: " + endDateTime);

            if (!endDateTime.isAfter(startDateTime)) {
                endDateTime = endDateTime.plusDays(1);
            }

            Object selected = cboSubject.getSelectedItem();
            if (selected == null || !monHocMap.containsKey(selected)) {
                JOptionPane.showMessageDialog(this, "Môn học không hợp lệ!");
                return;
            }

            stmt.setTimestamp(4, Timestamp.valueOf(startDateTime));
            stmt.setTimestamp(5, Timestamp.valueOf(endDateTime));
            stmt.setInt(6, monHocMap.get(selected));
            stmt.setInt(7, currentNhiemVu.getIdNhiemVu());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            if (parentUI != null) {
                parentUI.fillDanhSachNhiemVuTheoNgay(ngayDuocChon); // giữ nguyên ngày
            }
            if (parentPhuHuynh != null) {
                parentUI.fillDanhSachNhiemVuTheoNgay(ngayDuocChon); // ✅ đúng với ngày được chọn
            }
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    public void hoanThanhNhiemVu() {
        DatabaseManager dbMana = new DatabaseManager();
        int id_test = dbMana.search4Test(currentNhiemVu.getIdNhiemVu());
        System.out.println("id test = = = = = = " + id_test);
        if (id_test != 0) {
            int i = JOptionPane.showConfirmDialog(rootPane, "Bạn có một bài kiểm tra để hoàn thành nhiệm vụ này");
            if (i == JOptionPane.YES_OPTION) {
                this.dispose();
                String monHoc = "";
                if (currentNhiemVu.getIdMonHoc() == 1) {
                    monHoc = "Toán";
                } else if (currentNhiemVu.getIdMonHoc() == 2) {
                    monHoc = "Văn";
                } else if (currentNhiemVu.getIdMonHoc() == 3) {
                    monHoc = "Anh";
                } else if (currentNhiemVu.getIdMonHoc() == 4) {
                    monHoc = "Khác";
                }
                
                trangchu.addPanel(new TestInterface(dbMana.getTestques(id_test), String.valueOf(Auth.getUser().getID_Tai_Khoan()), monHoc, currentNhiemVu.getTieuDe(), id_test, currentNhiemVu.getIdNhiemVu()), "bai_test");
                if (parentUI != null) {
                    parentUI.refreshTasks();
                }
                if (parentPhuHuynh != null) {
                    parentPhuHuynh.refreshTasks();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Bạn cần phải hoàn thành bài kiểm tra để hoàn thành nhiệm vụ");
            }
        } else if (id_test == 0) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "UPDATE Nhiem_Vu SET Trang_Thai = N'Hoàn thành' WHERE ID_Nhiem_Vu=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, currentNhiemVu.getIdNhiemVu());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Đã hoàn thành nhiệm vụ!");
                if (parentUI != null) {
                    parentUI.refreshTasks();
                }
                if (parentPhuHuynh != null) {
                    parentPhuHuynh.refreshTasks();
                }
                dispose();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật trạng thái: " + e.getMessage());
            }
        }
    }
//    public void hoanThanhNhiemVu() {
//        DatabaseManager dbMana = new DatabaseManager();
//        int id_test = dbMana.search4Test(currentNhiemVu.getIdNhiemVu());
//        System.out.println("id test = = = = = = " + id_test);
//        try (Connection conn = DatabaseConnection.getConnection()) {
//            String sql = "UPDATE Nhiem_Vu SET Trang_Thai = N'Hoàn thành' WHERE ID_Nhiem_Vu=?";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, currentNhiemVu.getIdNhiemVu());
//            stmt.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Đã hoàn thành nhiệm vụ!");
//            if (parentUI != null) {
//                parentUI.refreshTasks();
//            }
//            if (parentPhuHuynh != null) {
//                parentPhuHuynh.refreshTasks();
//            }
//            dispose();
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật trạng thái: " + e.getMessage());
//        }
//
//    }

        private boolean validateForm(Integer taskId) {
        String title = txtTitle.getText().trim();
        String task = txtTask.getText().trim();
        String note = txtNotes.getText().trim();
        String selectedSubject = (String) cboSubject.getSelectedItem();
        Integer subjectId = monHocMap.get(selectedSubject);

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tiêu đề không được để trống.");
            return false;
        }

        if (cboSubject.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học.");
            return false;
        }

        if (task.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nội dung nhiệm vụ không được để trống.");
            return false;
        }

        if (note.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ghi chú của nhiệm vụ không được để trống.");
            return false;
        }

        // Kiểm tra nếu giá trị trong Spinner không phải là null
        if (spnStart.getValue() == null || spnEnd.getValue() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thời gian bắt đầu và kết thúc.");
            return false;
        }

        // Chuyển từ Date sang LocalDateTime
        Date startTime = (Date) spnStart.getValue();
        Date endTime = (Date) spnEnd.getValue();
        LocalDateTime startDateTime = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDateTime = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        if (startDateTime.equals(endDateTime)) {
            JOptionPane.showMessageDialog(this, "Thời gian bắt đầu và kết thúc không được trùng nhau.");
            return false;
        }

        if (endDateTime.isBefore(startDateTime)) {
            JOptionPane.showMessageDialog(this, "Không được chọn thời gian vượt sang ngày hôm sau.");
            return false;
        }

        long diffInMinutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime);
        if (diffInMinutes < 5) { // Điều kiện tối thiểu 5 phút
            JOptionPane.showMessageDialog(this, "Thời gian kết thúc phải cách thời gian bắt đầu ít nhất 5 phút.");
            return false;
        }

        // Kiểm tra xem nhiệm vụ đã tồn tại chưa
        if (isDuplicateTask(title, subjectId, task, note, startDateTime, endDateTime, taskId)) {
            JOptionPane.showMessageDialog(this, "Nhiệm vụ này đã tồn tại! Vui lòng thay đổi ít nhất một thông tin.");
            return false;
        }

        return true;
    }

    public boolean isDuplicateTask(String title, int subjectId, String task, String notes, LocalDateTime startDateTime, LocalDateTime endDateTime, Integer taskIdToExclude) {
        String sql = "SELECT COUNT(*) FROM Nhiem_Vu WHERE ID_Mon_Hoc = ? AND Tieu_De = ? AND Noi_Dung = ? AND Ghi_Chu = ? AND Thoi_Gian_Bat_Dau = ? AND Thoi_Gian_Ket_Thuc = ?";

        if (taskIdToExclude != null) {
            sql += " AND ID_Nhiem_Vu != ?"; // Nếu đang sửa nhiệm vụ, loại trừ ID_Nhiem_Vu khỏi kiểm tra
        }

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, subjectId);
            stmt.setString(2, title);
            stmt.setString(3, task);
            stmt.setString(4, notes);
            stmt.setTimestamp(5, Timestamp.valueOf(startDateTime));
            stmt.setTimestamp(6, Timestamp.valueOf(endDateTime));

            if (taskIdToExclude != null) {
                stmt.setInt(7, taskIdToExclude);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu tìm thấy nhiệm vụ trùng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Không tìm thấy nhiệm vụ trùng
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LocalDate ngayTest = LocalDate.of(2025, 3, 23); // ví dụ ngày 23/03/2025
            new TaskEditor("them", null, null, null, 1, ngayTest).setVisible(true);
        });
    }

}
