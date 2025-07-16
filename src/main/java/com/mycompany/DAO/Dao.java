/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DAO;

import com.mycompany.entity.BaiViet;
import com.mycompany.entity.TaiKhoan;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dao {

    Connection conn = DatabaseConnection.getConnection();
    private static final String URL = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";

    public Dao() {
        try {
            // Đảm bảo JDBC Driver được tải
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Kết nối database
            conn = DriverManager.getConnection(URL);
            System.out.println("✅ Kết nối thành công đến database!");

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Không tìm thấy JDBC Driver: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TaiKhoan findByUsernameOrEmail(String input) {
        String sql = "SELECT * FROM Tai_Khoan WHERE (Username = ? OR Email = ?)";
        TaiKhoan user = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, input);
            stmt.setString(2, input);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new TaiKhoan(
                        rs.getInt("ID_Tai_Khoan"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Ho_Va_Ten"),
                        rs.getString("So_Dien_Thoai"),
                        rs.getString("Email"),
                        rs.getString("Role"),
                        rs.getString("Trang_Thai"),
                        rs.getString("Avatar"),
                        rs.getDate("Ngay_Dang_Ki")
                );
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public ArrayList<BaiViet> getAllBaiViet() {
        ArrayList<BaiViet> danhSachBaiViet = new ArrayList<>();
        BaiViet bv = null;
        String query = "SELECT * FROM Bai_Viet WHERE Trang_Thai = N'Xuất Bản' ORDER BY Ngay_Dang DESC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bv = new BaiViet(rs.getInt("ID"),
                        rs.getInt("ID_TAI_KHOAN_ADMIN"),
                        rs.getString("Tieu_De"),
                        rs.getString("Noi_Dung"),
                        rs.getDate("Ngay_Dang"),
                        rs.getString("Trang_Thai"),
                        rs.getString("Hinh_Anh"));
                danhSachBaiViet.add(bv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachBaiViet;
    }

    public TaiKhoan login(String userInput, String password) {
        String sql = "SELECT * FROM Tai_Khoan WHERE (Username = ? OR Email = ?) AND Password = ? AND Trang_Thai = N'Hoạt động'";
        TaiKhoan user = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userInput); // Dùng cho Username
            stmt.setString(2, userInput); // Dùng cho Email
            stmt.setString(3, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new TaiKhoan(
                        rs.getInt("ID_Tai_Khoan"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Ho_Va_Ten"),
                        rs.getString("So_Dien_Thoai"),
                        rs.getString("Email"),
                        rs.getString("Role"),
                        rs.getString("Trang_Thai"),
                        rs.getString("Avatar"), // nhớ thêm cột này trong CSDL nếu chưa có
                        rs.getDate("Ngay_Dang_Ki")
                );
            }

            rs.close();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi truy vấn đăng nhập: " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }

    public static int getSoGioHocHV(int idHocVien, int thang, int nam) {
        String query = "SELECT SUM(DATEDIFF(HOUR, Thoi_Gian_Bat_Dau, Thoi_Gian_Ket_Thuc)) AS TongGioHoc FROM Nhiem_Vu "
                + "WHERE ID_Tai_Khoan = ? AND MONTH(Thoi_Gian_Bat_Dau) = ? AND YEAR(Thoi_Gian_Bat_Dau) = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idHocVien);
            ps.setInt(2, thang);
            ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TongGioHoc");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNhiemVuHoanThanhHV(int idHocVien, int thang, int nam) {
        String query = "SELECT COUNT(*) AS SoNhiemVu FROM Nhiem_Vu "
                + "WHERE ID_Tai_Khoan = ? AND Trang_Thai = N'Hoàn thành' "
                + "AND MONTH(Thoi_Gian_Bat_Dau) = ? AND YEAR(Thoi_Gian_Bat_Dau) = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idHocVien);
            ps.setInt(2, thang);
            ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("SoNhiemVu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNhiemVuChuaHoanThanhHV(int idHocVien, int thang, int nam) {
        String query = "SELECT COUNT(*) AS SoNhiemVu FROM Nhiem_Vu "
                + "WHERE ID_Tai_Khoan = ? AND Trang_Thai = N'Qua han' "
                + "AND MONTH(Thoi_Gian_Bat_Dau) = ? AND YEAR(Thoi_Gian_Bat_Dau) = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idHocVien);
            ps.setInt(2, thang);
            ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("SoNhiemVu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Map<String, Float> getDiemTrungBinhHV(int idHocVien, int thang, int nam) {
        Map<String, Float> diemTBMap = new HashMap<>();

        String sql = "SELECT mh.Ten AS Mon_Hoc, COALESCE(AVG(bt.Diem), 0) AS Diem_Trung_Binh "
                + "FROM Mon_Hoc mh "
                + "LEFT JOIN Nhiem_Vu nv ON mh.ID_Mon_Hoc = nv.ID_Mon_Hoc AND nv.ID_Tai_Khoan = ? "
                + "LEFT JOIN Bai_Test bt ON bt.ID_Nhiem_Vu = nv.ID_Nhiem_Vu "
                + "AND MONTH(nv.Thoi_Gian_Bat_Dau) = ? "
                + "AND YEAR(nv.Thoi_Gian_Bat_Dau) = ? "
                + // Sửa ở đây
                "GROUP BY mh.Ten "
                + "ORDER BY mh.Ten";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idHocVien);
            stmt.setInt(2, thang);
            stmt.setInt(3, nam);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String monHoc = rs.getString("Mon_Hoc");
                float diemTB = rs.getFloat("Diem_Trung_Binh");
                diemTBMap.put(monHoc, diemTB);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return diemTBMap;
    }

    public static int getSoGioHocPH(int idTaiKhoan, int thang, int nam) {
        String query = "SELECT SUM(DATEDIFF(HOUR, Thoi_Gian_Bat_Dau, Thoi_Gian_Ket_Thuc)) AS TongGioHoc FROM Nhiem_Vu "
                + "WHERE ID_Tai_Khoan = ? AND MONTH(Thoi_Gian_Bat_Dau) = ? AND YEAR(Thoi_Gian_Bat_Dau) = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idTaiKhoan);
            ps.setInt(2, thang);
            ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TongGioHoc");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNhiemVuHoanThanhPH(int idTaiKhoan, int thang, int nam) {
        String query = "SELECT COUNT(*) AS SoNhiemVu FROM Nhiem_Vu "
                + "WHERE ID_Tai_Khoan = ? AND Trang_Thai = N'Hoàn thành' "
                + "AND MONTH(Thoi_Gian_Bat_Dau) = ? AND YEAR(Thoi_Gian_Bat_Dau) = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idTaiKhoan);
            ps.setInt(2, thang);
            ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("SoNhiemVu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNhiemVuChuaHoanThanhPH(int idTaiKhoan, int thang, int nam) {
        String query = "SELECT COUNT(*) AS SoNhiemVu FROM Nhiem_Vu "
                + "WHERE ID_Tai_Khoan = ? AND Trang_Thai = N'Qua han' "
                + "AND MONTH(Thoi_Gian_Bat_Dau) = ? AND YEAR(Thoi_Gian_Bat_Dau) = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idTaiKhoan);
            ps.setInt(2, thang);
            ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("SoNhiemVu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Map<String, Float> getDiemTrungBinhPH(int idTaiKhoan, int thang, int nam) {
        Map<String, Float> diemTBMap = new HashMap<>();

        String sql = "SELECT mh.Ten AS Mon_Hoc, COALESCE(AVG(bt.Diem), 0) AS Diem_Trung_Binh "
                + "FROM Mon_Hoc mh "
                + "LEFT JOIN Nhiem_Vu nv ON mh.ID_Mon_Hoc = nv.ID_Mon_Hoc AND nv.ID_Tai_Khoan = ? "
                + "LEFT JOIN Bai_Test bt ON bt.ID_Nhiem_Vu = nv.ID_Nhiem_Vu "
                + "AND MONTH(nv.Thoi_Gian_Bat_Dau) = ? "
                + "AND YEAR(nv.Thoi_Gian_Bat_Dau) = ? "
                + // Sửa ở đây
                "GROUP BY mh.Ten "
                + "ORDER BY mh.Ten";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTaiKhoan);
            stmt.setInt(2, thang);
            stmt.setInt(3, nam);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String monHoc = rs.getString("Mon_Hoc");
                float diemTB = rs.getFloat("Diem_Trung_Binh");
                diemTBMap.put(monHoc, diemTB);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return diemTBMap;
    }
}
