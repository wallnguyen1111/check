/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DAO;

/**
 *
 * @author Do Tuong Minh
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LienKetTaiKhoanDAO {

    // Gửi yêu cầu liên kết (Phụ huynh gửi yêu cầu)
    public boolean sendLinkRequest(int idPhuHuynh, int idHocSinh) {
        String sql = "INSERT INTO Phu_Huynh_Hoc_Sinh (ID_Tai_Khoan_Phu_Huynh, ID_Tai_Khoan_Hoc_Sinh, Tinh_Trang_Ket_Noi) "
                + "VALUES (?, ?, N'Chờ xác nhận')";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPhuHuynh);
            stmt.setInt(2, idHocSinh);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra các yêu cầu liên kết đang chờ xác nhận khi học viên đăng nhập
    public List<String> getPendingRequests(int idHocSinh) {
        List<String> requests = new ArrayList<>();
        String sql = "SELECT tk.Ho_Va_Ten FROM Phu_Huynh_Hoc_Sinh phhs "
                + "JOIN Tai_Khoan tk ON phhs.ID_Tai_Khoan_Phu_Huynh = tk.ID_Tai_Khoan "
                + "WHERE phhs.ID_Tai_Khoan_Hoc_Sinh = ? AND phhs.Tinh_Trang_Ket_Noi = N'Chờ xác nhận'";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idHocSinh);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                requests.add(rs.getString("Ho_Va_Ten"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    // Học viên xác nhận liên kết với phụ huynh
    public boolean confirmLink(int idHocSinh, String tenPhuHuynh) {
        String sql = "UPDATE Phu_Huynh_Hoc_Sinh "
                + "SET Tinh_Trang_Ket_Noi = N'Đã kết nối' "
                + "WHERE ID_Tai_Khoan_Hoc_Sinh = ? "
                + "AND ID_Tai_Khoan_Phu_Huynh = (SELECT ID_Tai_Khoan FROM Tai_Khoan WHERE Ho_Va_Ten = ? AND Role = N'Phụ huynh')";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idHocSinh);
            stmt.setString(2, tenPhuHuynh);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Học viên từ chối yêu cầu liên kết
    public boolean rejectLink(int idHocSinh, String tenPhuHuynh) {
        String sql = "DELETE FROM Phu_Huynh_Hoc_Sinh "
                + "WHERE ID_Tai_Khoan_Hoc_Sinh = ? "
                + "AND ID_Tai_Khoan_Phu_Huynh = (SELECT ID_Tai_Khoan FROM Tai_Khoan WHERE Ho_Va_Ten = ? AND Role = N'Phụ huynh')";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idHocSinh);
            stmt.setString(2, tenPhuHuynh);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLinked(int idPhuHuynh, int idHocSinh) {
        String sql = "SELECT COUNT(*) FROM Phu_Huynh_Hoc_Sinh WHERE ID_Tai_Khoan_Phu_Huynh = ? AND ID_Tai_Khoan_Hoc_Sinh = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPhuHuynh);
            stmt.setInt(2, idHocSinh);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Đã liên kết
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Chưa liên kết
    }

    public String getHoTenHocVien(int idHocSinh) {
        String sql = "SELECT Ho_Va_Ten FROM Tai_Khoan WHERE ID_Tai_Khoan = ? AND Role = N'Học viên'";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idHocSinh);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Ho_Va_Ten");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy
    }

    public class DatabaseConnection {

        private static final String URL = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";

        public static Connection getConnection() {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    }
}
