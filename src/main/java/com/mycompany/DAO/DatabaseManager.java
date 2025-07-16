/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DAO;

import com.mycompany.coachmeai.TestInterface;
import com.mycompany.entity.NhiemVu;
import com.mycompany.utils.Auth;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 * Quản lý kết nối và thao tác với cơ sở dữ liệu
 */
public class DatabaseManager {

    private static final String URL = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";
    JSONObject taskData = new JSONObject();
    private NhiemVu Nv = new NhiemVu();
    int id_bai_test = 0;

    /**
     * Kết nối đến cơ sở dữ liệu
     */
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    /**
     * Thêm lịch học/nhiệm vụ vào database
     */
    public int search4Test(int ID_Nhiem_Vu) {
        String sql = "select * from Bai_Test where ID_Nhiem_Vu = ?";
        String sql2 = "select * from Cau_Hoi_Bai_Test where ID_Bai_Test = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID_Nhiem_Vu);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int i = rs.getInt("ID_Bai_Test");
                System.out.println("id bai test la:" + i);
                if (i > 0) {
                    try (Connection conne = getConnection(); PreparedStatement pstmts = conne.prepareStatement(sql2)) {
                        pstmts.setInt(1, i);
                        ResultSet rss = pstmts.executeQuery();
                        if (rss.next()) {
                            System.out.println("hello helo hellodsianfuisdn");
                            return i;
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public NhiemVu getNV(int ID_Nhiem_Vu) {
        String sql = "select * from Nhiem_Vu where ID_Nhiem_Vu = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID_Nhiem_Vu);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Nv.setIdNhiemVu(rs.getInt("ID_Nhiem_Vu"));
                Nv.setIdTaiKhoan(rs.getInt("ID_Tai_Khoan"));
                Nv.setIdMonHoc(rs.getInt("ID_Mon_Hoc"));
                Nv.setTieuDe(rs.getString("Tieu_De"));
                Nv.setNoiDung(rs.getString("Noi_Dung"));
                Nv.setTrangThai(rs.getString("Trang_Thai"));
                Nv.setThoiGianBatDau(rs.getDate("Thoi_Gian_Bat_Dau"));
                Nv.setThoiGianKetThuc(rs.getDate("Thoi_Gian_Ket_Thuc"));
                return Nv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[][] getTestques(int ID_Bai_Test) {
        String sql = "SELECT Cau_Hoi, Dap_An_A, Dap_An_B, Dap_An_C, Dap_An_D FROM Cau_Hoi_Bai_Test where ID_Bai_Test = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            pstmt.setInt(1, ID_Bai_Test);
            ResultSet rs = pstmt.executeQuery();

            rs.last(); // Di chuyển đến dòng cuối cùng để lấy tổng số dòng
            int rowCount = rs.getRow();
            rs.beforeFirst(); // Di chuyển lại về đầu

            String[][] questions = new String[rowCount][5]; // 5 cột: Câu hỏi + 4 đáp án
            int index = 0;

            while (rs.next()) {
                questions[index][0] = rs.getString("Cau_Hoi");
                questions[index][1] = rs.getString("Dap_An_A");
                questions[index][2] = rs.getString("Dap_An_B");
                questions[index][3] = rs.getString("Dap_An_C");
                questions[index][4] = rs.getString("Dap_An_D");
                index++;
            }

            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[0][0];
    }

    public static Object[][] getTestans(String[] cauTraloi, int ID_Bai_Test) {
        String sql = "SELECT Cau_Hoi, Dap_An_Dung FROM Cau_Hoi_Bai_Test where ID_Bai_Test = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            pstmt.setInt(1, ID_Bai_Test);
            ResultSet rs = pstmt.executeQuery();

            rs.last(); // Di chuyển đến dòng cuối cùng để lấy tổng số dòng
            int rowCount = rs.getRow();
            rs.beforeFirst(); // Di chuyển lại về đầu

            Object[][] questions = new Object[rowCount][5]; // 5 cột: Câu hỏi + 4 đáp án
            int index = 0;

            while (rs.next()) {
                questions[index][0] = index + 1;
                questions[index][1] = rs.getString("Cau_Hoi");
                if (cauTraloi.length > 0) {
                    questions[index][2] = cauTraloi[index];
                    questions[index][3] = rs.getString("Dap_An_Dung");
                    if (rs.getString("Dap_An_Dung").toLowerCase().equals(cauTraloi[index].toLowerCase())) {
                        questions[index][4] = "Đúng";
                    } else {
                        questions[index][4] = "Sai";
                    }
                    index++;
                } else {
                    System.out.println("chưa có câu trả lời");
                }
            }

            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[0][0];
    }

    public void addTask(String title, String subject, String content, String note, Timestamp startTime, Timestamp endTime) {
        String sql = "INSERT INTO Nhiem_Vu (ID_Tai_Khoan, ID_Mon_Hoc, Tieu_De, Noi_Dung, Trang_Thai, Thoi_Gian_Bat_Dau, Thoi_Gian_Ket_Thuc) VALUES (?,?,?,?,N'Chưa hoàn thành',?,?)";
        String sql2 = "select ID_Mon_Hoc from Mon_Hoc where Ten = ?";
        int idMonhoc = 0;
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql2)) {
            pstmt.setString(1, subject);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                idMonhoc = rs.getInt("ID_Mon_Hoc");
                System.out.println(idMonhoc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Auth.getUser().getID_Tai_Khoan());
            pstmt.setInt(2, idMonhoc);
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.setTimestamp(5, startTime);
            pstmt.setTimestamp(6, endTime);

            int i = pstmt.executeUpdate();
            if (i > 0) {
                System.out.println("Thêm nhiệm vụ thành công!");
                getTask(title, idMonhoc, content, note, startTime, endTime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getTask(String title, int subject_id, String content, String note, Timestamp startTime, Timestamp endTime) {
        String sql = "select * from Nhiem_Vu where ID_Tai_Khoan = ? and Trang_Thai = N'Chưa hoàn thành' and ID_Mon_Hoc = ? and Tieu_De = ? and Noi_Dung = ? and Thoi_Gian_Bat_Dau = ? and Thoi_Gian_Ket_Thuc = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Auth.getUser().getID_Tai_Khoan());
            pstmt.setInt(2, subject_id);
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.setTimestamp(5, startTime);
            pstmt.setTimestamp(6, endTime);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Nv.setIdNhiemVu(rs.getInt("ID_Nhiem_Vu"));
                Nv.setIdTaiKhoan(rs.getInt("ID_Tai_Khoan"));
                Nv.setIdMonHoc(rs.getInt("ID_Mon_Hoc"));
                Nv.setTieuDe(rs.getString("Tieu_De"));
                Nv.setNoiDung(rs.getString("Noi_Dung"));
                Nv.setTrangThai(rs.getString("Trang_Thai"));
                Nv.setThoiGianBatDau(rs.getDate("Thoi_Gian_Bat_Dau"));
                Nv.setThoiGianKetThuc(rs.getDate("Thoi_Gian_Ket_Thuc"));
                addBaitest(Nv.getIdNhiemVu());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static float ChamDiem(String[] dapAnnguoiDung, int id_bai_test) {
        String sql = "select Dap_An_Dung from Cau_Hoi_Bai_Test where ID_Bai_Test = ?";
        ArrayList<String> list = new ArrayList<>();
        int count = 0;
        list.clear();
        if (dapAnnguoiDung.length <= 0) {
            System.out.println("không tồn tại đáp án trả lời");
        }
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id_bai_test);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Dap_An_Dung"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < dapAnnguoiDung.length; i++) {
            String NG = dapAnnguoiDung[i].toLowerCase();
            String True = list.get(i).toLowerCase().toString();
            if (NG.equals(True)) {
                count++;
            }
        }
        float diem = (float) count / dapAnnguoiDung.length * 10;
        diem = Math.round(diem * 100) / 100.0f;
        String sql2 = "update bai_test set Diem = ? where ID_Bai_Test = ?";
        if (diem > 8.0) {
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql2)) {
                pstmt.setFloat(1, diem);
                pstmt.setInt(2, id_bai_test);
                int i = pstmt.executeUpdate();
                if (i > 0) {
                    System.out.println("cap nhat diem thanh cong");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return diem;

    }

    public List<String> getAllTasks() {
        List<String> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String task = String.format("ID: %d - %s (%s) từ %s đến %s",
                        rs.getInt("id"), rs.getString("title"), rs.getString("subject"),
                        rs.getTimestamp("start_time"), rs.getTimestamp("end_time"));
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Xoá nhiệm vụ theo ID
     */
    public void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, taskId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xoá nhiệm vụ thành công!");
            } else {
                System.out.println("Không tìm thấy nhiệm vụ với ID: " + taskId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int layIDbaiTest(int ID_Nhiem_Vu) {
        String sql = "select * from Bai_Test where ID_Nhiem_Vu = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ID_Nhiem_Vu);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int i = rs.getInt("ID_Bai_Test");
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addBaitest(int ID_Nhiem_Vu) {
        String sql = "INSERT INTO Bai_Test (ID_Nhiem_Vu) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ID_Nhiem_Vu);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Insert bang bai test thanh cong!");
            } else {
                System.out.println("Không tìm thấy nhiệm vụ với ID: " + ID_Nhiem_Vu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addQuesTest(String Cau_Hoi, String Dap_An_A, String Dap_An_B, String Dap_An_C, String Dap_An_D, String Dap_An_Dung) {
        id_bai_test = layIDbaiTest(Nv.getIdNhiemVu());
        System.out.println(id_bai_test);
        String sql = "INSERT INTO Cau_Hoi_Bai_Test (ID_Bai_Test, Cau_Hoi, Dap_An_A, Dap_An_B, Dap_An_C, Dap_An_D, Dap_An_Dung) VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id_bai_test);
            pstmt.setString(2, Cau_Hoi);
            pstmt.setString(3, Dap_An_A);
            pstmt.setString(4, Dap_An_B);
            pstmt.setString(5, Dap_An_C);
            pstmt.setString(6, Dap_An_D);
            pstmt.setString(7, Dap_An_Dung);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Insert cau hoi thanh cong!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
