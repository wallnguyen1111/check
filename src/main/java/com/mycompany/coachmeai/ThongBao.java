/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.DAO.DatabaseConnection;
import static com.mycompany.coachmeai.ThongBao.connectionUrl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import javax.swing.Timer;

public class ThongBao extends JPanel {

    static String connectionUrl = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";

    private JPanel panelThongBao;
    private JScrollPane scrollPane;
    private int idTaiKhoan;
    private int soLuongThongBaoHienTai = 0; // Biến theo dõi số lượng thông báo hiện tại

    public ThongBao(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
        this.setLayout(null);
        JPanel whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setBounds(0, 0, 710, 650);
        whitePanel.setLayout(null); // Cho phép đặt vị trí tùy chỉnh

        JLabel chuongLabel = new JLabel();
        ImageIcon chuongImage = new ImageIcon("D:\\coachmeai\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\chuong.png");

        // Kiểm tra ảnh có tồn tại không
        if (chuongImage.getIconWidth() > 0) {
            // Chỉnh kích thước ảnh
            Image img = chuongImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            chuongLabel.setIcon(new ImageIcon(img));
        } else {
            chuongLabel.setText("Ảnh không tồn tại!");
        }

        chuongLabel.setBounds(20, 20, 50, 50); // Vị trí ảnh

        // Tiêu đề "THÔNG BÁO"
        JLabel titleLabel = new JLabel("THÔNG BÁO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setBounds(80, 20, 300, 50); // Vị trí tiêu đề

        //button xóa thông báo 
        // Nút "Xóa thông báo"
        JButton btn_xoaThongBao = new JButton("Xóa thông báo");
        btn_xoaThongBao.setBounds(500, 60, 150, 40);
        btn_xoaThongBao.setBackground(new Color(10, 58, 95));
        btn_xoaThongBao.setForeground(Color.WHITE);
        btn_xoaThongBao.setFont(new Font("Arial", Font.BOLD, 15));

        // Sự kiện khi nhấn nút "Xóa thông báo"
        btn_xoaThongBao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn xóa tất cả thông báo không?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    xoaTatCaThongBao();
                    JOptionPane.showMessageDialog(null, "Đã xóa tất cả thông báo!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        panelThongBao = new JPanel();
        panelThongBao.setLayout(new BoxLayout(panelThongBao, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panelThongBao);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelThongBao.setBackground(Color.WHITE);

        scrollPane.setBounds(20, 120, 650, 500); // Định vị trí của ScrollPane
        whitePanel.add(scrollPane);
        // Thêm vào whitePanel
        whitePanel.add(chuongLabel);
        whitePanel.add(titleLabel);
        whitePanel.add(btn_xoaThongBao);

        // Thêm vào JFrame
        this.add(whitePanel);

        Timer timer = new Timer(1000, (ActionEvent e) -> {
            loadThongBao();
        });
        timer.start();

        loadThongBao();
        this.setVisible(true);
    }

    private boolean daKiemTraLanDau = false; // Để kiểm soát lần đầu tiên

    private void loadThongBao() {

        List<String[]> thongBaoList = getThongBaoFromDatabase();

// Nếu số lượng thông báo không thay đổi, không cần load lại
        if (thongBaoList.size() == soLuongThongBaoHienTai) {
            return;
        }

// Nếu không phải lần đầu kiểm tra và có thông báo mới, hiển thị tất cả thông báo mới
        if (daKiemTraLanDau && thongBaoList.size() > soLuongThongBaoHienTai) {
            for (int i = soLuongThongBaoHienTai; i < thongBaoList.size(); i++) {
                String[] thongBaoMoi = thongBaoList.get(i);
                SwingUtilities.invokeLater(() -> {
                    ThongBaoPopup popup = new ThongBaoPopup(thongBaoMoi[0], thongBaoMoi[3]); // Truyền Tiêu Đề, Nội Dung, và Thời Gian
                    popup.showPopup();
                });

                // Thêm một khoảng thời gian giữa các popup để tránh hiển thị chồng lên nhau
                try {
                    Thread.sleep(1000); // Hiển thị mỗi popup cách nhau 1 giây
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

// Đánh dấu đã kiểm tra lần đầu tiên
        daKiemTraLanDau = true;

// Cập nhật số lượng thông báo mới
        soLuongThongBaoHienTai = thongBaoList.size();

        // Cập nhật lại danh sách thông báo trên UI
        panelThongBao.removeAll();

           for (String[] tb : thongBaoList) {
            int thongBaoID = Integer.parseInt(tb[2]);

            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(220, 240, 255));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // Giới hạn chiều cao

            // Tiêu đề
            JLabel lblTieuDe = new JLabel("📌 " + tb[0]);
            lblTieuDe.setFont(new Font("Arial", Font.BOLD, 14));

            // Ngày gửi
            JLabel lblNgay = new JLabel(tb[1]);
            lblNgay.setFont(new Font("Arial", Font.ITALIC, 12));

            // Nội dung thông báo (hiển thị trực tiếp)
            JTextArea txtNoiDung = new JTextArea(tb[3]);
            txtNoiDung.setFont(new Font("Arial", Font.PLAIN, 13));
            txtNoiDung.setLineWrap(true);
            txtNoiDung.setWrapStyleWord(true);
            txtNoiDung.setEditable(false);
            txtNoiDung.setBackground(new Color(220, 240, 255)); // Đồng bộ màu nền
            txtNoiDung.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            // Nút "Xóa"
            JButton btnXoa = new JButton("🗑 Xóa");
            btnXoa.addActionListener((ActionEvent e) -> xoaThongBao(thongBaoID));

            // Panel chứa nút
            JPanel panelButtons = new JPanel();
            panelButtons.setBackground(new Color(220, 240, 255));
            panelButtons.add(btnXoa);

            // Sắp xếp layout
            JPanel panelContent = new JPanel(new BorderLayout());
            panelContent.setBackground(new Color(220, 240, 255));
            panelContent.add(lblTieuDe, BorderLayout.NORTH);
            panelContent.add(txtNoiDung, BorderLayout.CENTER);
            panelContent.add(lblNgay, BorderLayout.SOUTH);

            panel.add(panelContent, BorderLayout.CENTER);
            panel.add(panelButtons, BorderLayout.EAST);

            panelThongBao.add(Box.createVerticalStrut(10), 0);
            panelThongBao.add(panel, 0);
        }

        panelThongBao.revalidate();
        panelThongBao.repaint();
    }

    private List<String[]> getThongBaoFromDatabase() {
        List<String[]> thongBaoList = new ArrayList<>();
        String sql1 = "SELECT tbdg.ID, dtb.Tieu_De, dtb.Noi_Dung, tbdg.Thoi_Gian_Gui "
                + "FROM Thong_Bao_Da_Gui tbdg "
                + "JOIN Data_Thong_Bao dtb ON tbdg.ID_Thong_Bao = dtb.ID "
                + "WHERE tbdg.ID_Tai_Khoan = ?";

        String sql2 = "SELECT tbdg.ID, tlf.Noi_Dung_Tra_Loi, fb.Noi_Dung_FeedBack, tbdg.Thoi_Gian_Gui\n"
                + "FROM Thong_Bao_Da_Gui tbdg \n"
                + "JOIN Tra_Loi_FeedBack tlf ON tbdg.ID_Tra_Loi = tlf.ID_Tra_Loi\n"
                + "JOIN FeedBack fb on tlf.ID_FeedBack = fb.ID_FeedBack\n"
                + "WHERE tbdg.ID_Tai_Khoan = ? ";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

            pstmt1.setInt(1, idTaiKhoan);
            ResultSet rs1 = pstmt1.executeQuery();

            while (rs1.next()) {
                String id = rs1.getString("ID");
                String tieuDe = rs1.getString("Tieu_De");
                String noiDung = rs1.getString("Noi_Dung");
                String ngayGui = rs1.getTimestamp("Thoi_Gian_Gui").toLocalDateTime().toString();
                thongBaoList.add(new String[]{tieuDe, ngayGui, id, noiDung});
            }

            pstmt2.setInt(1, idTaiKhoan);
            ResultSet rs2 = pstmt2.executeQuery();

            while (rs2.next()) {
                String id = rs2.getString("ID");
                String tieuDe = rs2.getString("Noi_Dung_FeedBack");
                String noiDung = rs2.getString("Noi_Dung_Tra_Loi");
                String ngayGui = rs2.getTimestamp("Thoi_Gian_Gui").toLocalDateTime().toString();
                thongBaoList.add(new String[]{tieuDe, ngayGui, id, noiDung});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        thongBaoList.sort((a, b)
                -> LocalDateTime.parse(a[1], formatter).compareTo(LocalDateTime.parse(b[1], formatter))
        );

        return thongBaoList;
    }

    private void xemChiTiet(String tieuDe, String noiDung) {
        JTextArea textArea = new JTextArea(noiDung);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 150));

        JOptionPane.showMessageDialog(this, scrollPane, "📜 " + tieuDe, JOptionPane.INFORMATION_MESSAGE);
    }

    private void xoaThongBao(int idThongBao) {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa thông báo này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Thong_Bao_Da_Gui WHERE ID = ?")) {
                pstmt.setInt(1, idThongBao);
                pstmt.executeUpdate();
                loadThongBao(); // Reload lại danh sách thông báo
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void xoaTatCaThongBao() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa tất cả thông báo?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM Thong_Bao_Da_Gui");
                loadThongBao();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void themThongBao(String tieuDe, String noiDung) {
        String sql = "INSERT INTO Thong_Bao_Da_Gui (Tieu_De, Noi_Dung, Thoi_Gian_Gui) VALUES (?, ?, GETDATE())";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tieuDe);
            pstmt.setString(2, noiDung);
            pstmt.executeUpdate();

            // 🔄 Load lại thông báo sau khi thêm mới
            loadThongBao();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Thông báo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(710, 650);
            frame.setResizable(false);
            ThongBao thongBaoPanel = new ThongBao(1);
            frame.add(thongBaoPanel);
            frame.setLocationRelativeTo(null); // Căn giữa màn hình
            frame.setVisible(true);
        });
    }
}
