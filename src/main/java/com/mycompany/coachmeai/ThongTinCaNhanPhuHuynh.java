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
import com.mycompany.DAO.GuiOTP;
import com.mycompany.DAO.ServiceThongTinTaiKhoan;
import com.mycompany.DAO.ServiceValidate;
import com.mycompany.entity.OTPObject;
import com.mycompany.entity.TaiKhoan;
import com.mycompany.utils.Auth;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.DefaultCellEditor;
import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class ThongTinCaNhanPhuHuynh extends JPanel {

    private JPanel blueCardPanel; // JPanel chứa blue_2_Panel và blue_3_Panel
    private CardLayout cardLayout;

    public ThongTinCaNhanPhuHuynh() {
        giaoDien_ThongTinCaNhan();
    }

    //ModelTaiKhoan user = new ModelTaiKhoan(3, "NguyenA", "VanA12345", "Nguyen Van A", "0389934465", "anv@gmail.com", "Phụ huynh", "Hoạt động", "C:\\Users\\nguye\\OneDrive\\Pictures\\Screenshots\\Screenshot 2023-08-27 092627.png", Date.valueOf("2025-03-19"));
    TaiKhoan user = Auth.getUser();

    public void setImageCircleLabel(JLabel label, String imagePath) {
        int size = Math.min(label.getWidth(), label.getHeight()); // Đảm bảo ảnh phù hợp với hình tròn

        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);

        label.setIcon(new ImageIcon(scaledImage));
    }
    public CircleLabel lbl_white_anhTron_ta;
    public JLabel txt_white_ten_ta;
    public JLabel lbl_white_trangThai_ta;
    public JRadioButton rdo_DaLienKet_ta;
    public JRadioButton rdo_ChuaLienKet_ta;

    public void HienThiTrangThai() {
        boolean check = ServiceThongTinTaiKhoan.CheckKetNoiPhuHuynh(user.getID_Tai_Khoan());
        if (check == true) {
            lbl_white_trangThai_ta.setText("Liên kết: Đã liên kết với HV");
            if (rdo_ChuaLienKet_ta != null && rdo_DaLienKet_ta != null) {
                rdo_DaLienKet_ta.setSelected(true);
                rdo_ChuaLienKet_ta.setSelected(false);
            }
        } else {
            lbl_white_trangThai_ta.setText("Liên kết: Chưa liên kết với HV");
            if (rdo_ChuaLienKet_ta != null && rdo_DaLienKet_ta != null) {
                rdo_DaLienKet_ta.setSelected(false);
                rdo_ChuaLienKet_ta.setSelected(true);
            }
        }
    }

    public void giaoDien_ThongTinCaNhan() {
        this.setLayout(null);

        JPanel whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setBounds(0, 0, 710, 650);
        whitePanel.setLayout(null);

        lbl_white_anhTron_ta = new CircleLabel(100);
        lbl_white_anhTron_ta.setBounds(50, 30, 100, 100);
        whitePanel.add(lbl_white_anhTron_ta);

        lbl_white_anhTron_ta.setImage(user.getAvatar());
        //Dan them
        //setImageCircleLabel(lbl_white_anhTron_ta, user.getAvatar());

        txt_white_ten_ta = new JLabel("Tên tài khoản");
        txt_white_ten_ta.setBounds(170, 30, 200, 30);
        txt_white_ten_ta.setFont(new Font("Arial", Font.BOLD, 20));
        whitePanel.add(txt_white_ten_ta);

        JLabel lbl_white_vaiTro_ta = new JLabel("Vai trò:");
        lbl_white_vaiTro_ta.setBounds(170, 60, 200, 30);
        lbl_white_vaiTro_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        whitePanel.add(lbl_white_vaiTro_ta);

        lbl_white_trangThai_ta = new JLabel("Liên kết:");
        lbl_white_trangThai_ta.setBounds(170, 90, 200, 30);
        lbl_white_trangThai_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        whitePanel.add(lbl_white_trangThai_ta);

        HienThiTrangThai();

        txt_white_ten_ta.setText(user.getUsername());

        lbl_white_vaiTro_ta.setText("Vai trò: " + user.getRole());

        // Tạo JButton
        JButton btn_white_ttcn_ta = new JButton("Thông tin cá nhân");
        btn_white_ttcn_ta.setBounds(480, 30, 180, 30);
        btn_white_ttcn_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        btn_white_ttcn_ta.setBackground(Color.WHITE);
        btn_white_ttcn_ta.setForeground(Color.BLACK);
        // Resize icon để vừa với button (kích thước 20x20)
        ImageIcon ttcn_icon_1 = new ImageIcon("C:\\Users\\Admin\\Documents\\Coach_Me_AI\\src\\Image_TuAnh\\avatar.png");
        Image ttcb_icon_2 = ttcn_icon_1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon ttcb_icon = new ImageIcon(ttcb_icon_2);
        btn_white_ttcn_ta.setIcon(ttcb_icon);
        btn_white_ttcn_ta.setHorizontalTextPosition(SwingConstants.RIGHT); // Chữ bên phải icon
        btn_white_ttcn_ta.setIconTextGap(10); // Khoảng cách giữa icon và chữ
        // Thêm button vào panel
        whitePanel.add(btn_white_ttcn_ta);

        JButton btn_white_dmk_ta = new JButton("Đổi mật khẩu");
        btn_white_dmk_ta.setBounds(480, 60, 180, 30);
        btn_white_dmk_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        btn_white_dmk_ta.setBackground(Color.WHITE);
        ImageIcon dmk_icon_1 = new ImageIcon("C:\\Users\\Admin\\Documents\\Coach_Me_AI\\src\\Image_TuAnh\\doiMatKhau.png");
        Image dmk_icon_2 = dmk_icon_1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon dmk_icon = new ImageIcon(dmk_icon_2);
        btn_white_dmk_ta.setIcon(dmk_icon);
        btn_white_dmk_ta.setHorizontalTextPosition(SwingConstants.RIGHT); // Chữ bên phải icon
        btn_white_dmk_ta.setIconTextGap(10);
        whitePanel.add(btn_white_dmk_ta);

        JButton btn_white_dx_ta = new JButton("Đăng xuất");
        btn_white_dx_ta.setBounds(480, 90, 180, 30);
        btn_white_dx_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        btn_white_dx_ta.setBackground(Color.WHITE);
        ImageIcon dx_icon_1 = new ImageIcon("C:\\Users\\Admin\\Documents\\Coach_Me_AI\\src\\Image_TuAnh\\dangXuat.png");
        Image dx_icon_2 = dx_icon_1.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon dx_icon = new ImageIcon(dx_icon_2);
        btn_white_dx_ta.setIcon(dx_icon);
        btn_white_dx_ta.setHorizontalTextPosition(SwingConstants.RIGHT); // Chữ bên phải icon
        btn_white_dx_ta.setIconTextGap(10);
        whitePanel.add(btn_white_dx_ta);

        btn_white_ttcn_ta.addActionListener(e -> cardLayout.show(blueCardPanel, "BLUE_2"));

        btn_white_ttcn_ta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_white_ttcn_ta.setBackground(Color.decode("#064469")); // Chuyển sang màu xanh đậm khi hover
                btn_white_ttcn_ta.setForeground(Color.WHITE); // Chữ trắng khi hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_white_ttcn_ta.setBackground(Color.WHITE); // Trở lại màu trắng khi rời chuột
                btn_white_ttcn_ta.setForeground(Color.BLACK); // Chữ quay về màu đen
            }
        });

        btn_white_dmk_ta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_white_dmk_ta.setBackground(Color.decode("#064469")); // Chuyển sang màu xanh đậm khi hover
                btn_white_dmk_ta.setForeground(Color.WHITE); // Chữ trắng khi hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_white_dmk_ta.setBackground(Color.WHITE); // Trở lại màu trắng khi rời chuột
                btn_white_dmk_ta.setForeground(Color.BLACK); // Chữ quay về màu đen
            }
        });
        btn_white_dx_ta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_white_dx_ta.setBackground(Color.decode("#064469")); // Chuyển sang màu xanh đậm khi hover
                btn_white_dx_ta.setForeground(Color.WHITE); // Chữ trắng khi hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_white_dx_ta.setBackground(Color.WHITE); // Trở lại màu trắng khi rời chuột
                btn_white_dx_ta.setForeground(Color.BLACK); // Chữ quay về màu đen
            }
        });

        // Khởi tạo CardLayout để quản lý các giao diện trong blueCardPanel
        cardLayout = new CardLayout();
        blueCardPanel = new JPanel(cardLayout);
        blueCardPanel.setBounds(20, 150, 635, 450);
        whitePanel.add(blueCardPanel);

        JPanel blue_tkht_Panel = giaoDien_0_BangLienKetHocVien();
        blueCardPanel.add(blue_tkht_Panel, "BLUE_lkhv");
        
        JPanel blue_2_Panel = giaoDien_1_ThongTinCaNhan();
        blueCardPanel.add(blue_2_Panel, "BLUE_2");

        JPanel blue_3_Panel = giaoDien_2_ChinhSuaThongTinCaNhan();
        blueCardPanel.add(blue_3_Panel, "BLUE_3");

        JPanel blue_4_Panel = giaoDien_3_DoiMatKhau();
        blueCardPanel.add(blue_4_Panel, "BLUE_4");

        cardLayout.show(blueCardPanel, "BLUE_lkhv");

        btn_white_ttcn_ta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel blue_2_Panel = giaoDien_1_ThongTinCaNhan();
                blueCardPanel.add(blue_2_Panel, "BLUE_2");
            }
        });

        btn_white_dmk_ta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(blueCardPanel, "BLUE_4"); // Chuyển sang giao diện giaoDien_1
            }
        });

        JFrame frame = new JFrame("Đăng Xuất");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        btn_white_dx_ta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn đăng xuất?",
                        "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(frame, "Bạn đã đăng xuất thành công!");
                    System.exit(0); // Thoát ứng dụng
                }
            }
        });
        this.add(whitePanel);
        this.setVisible(true);
    }

    public JPanel giaoDien_0_BangLienKetHocVien() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#D0EBF8"));
        panel.setBounds(20, 150, 635, 450);
        panel.setLayout(null); // Bỏ layout để tự set vị trí

        JLabel lbl_tkht = new JLabel("LIÊN KẾT HỌC VIÊN");
        lbl_tkht.setBounds(20, 20, 250, 30);
        lbl_tkht.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lbl_tkht);

        DefaultTableModel tbl_bangLienKet = new DefaultTableModel();
        tbl_bangLienKet.addColumn("ID học viên");
        tbl_bangLienKet.addColumn("Tình trạng liên kết");
        tbl_bangLienKet.addColumn("Chức năng");

        JTable tb_data = new JTable();
        tb_data.setRowHeight(30);
        tb_data.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tb_data.setModel(tbl_bangLienKet);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tb_data.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tb_data.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        loadDataToTable(user.getID_Tai_Khoan(), tbl_bangLienKet, tb_data);

        // Đặt bảng vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(tb_data);
        scrollPane.setBounds(0, 80, 640, 400);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadDataToTable(int idPhuHuynh, DefaultTableModel model, JTable table) {
        model.setRowCount(0);
        List<Object[]> danhSachLienKet = ServiceThongTinTaiKhoan.layThongTinLienKetPHHV(idPhuHuynh);

        for (Object[] row : danhSachLienKet) {
            int idHocVien = (int) row[1];
            String tinhTrang = row[2].toString();
            JButton btnChucNang;
            if (tinhTrang.equals("Đã kết nối")) {
                btnChucNang = new JButton("Hủy kết nối");
            } else {
                btnChucNang = new JButton("Hủy yêu cầu");
            }

            btnChucNang.setFont(new Font("Arial", Font.BOLD, 12));

            btnChucNang.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tinhTrang.equals("Đã kết nối")) {
                        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy kết nối với Học Viên?",
                                "Xác nhận", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            boolean Check = ServiceThongTinTaiKhoan.XoaLienKet(idPhuHuynh, idHocVien);
                            if (Check == true) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Bạn đã hủy kết nối với Học Viên thành công",
                                        "Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                                loadDataToTable(idPhuHuynh, model, table);
                                HienThiTrangThai();
                            } else {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Hủy thất bại, vui lòng thử lại",
                                        "Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                            }
                        }
                    } else {
                        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy yêu cầu kết nối với Học Viên?",
                                "Xác nhận", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            boolean Check = ServiceThongTinTaiKhoan.XoaLienKet(idPhuHuynh, idHocVien);
                            if (Check == true) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Bạn đã hủy yêu cầu kết nối thành công",
                                        "Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                                loadDataToTable(idPhuHuynh, model, table);
                            } else {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Hủy thất bại, vui lòng thử lại",
                                        "Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                            }
                        }
                    }
                }
            });

            // Chỉ thêm nút bấm vào bảng, chưa có sự kiện xử lý
            model.addRow(new Object[]{row[1], tinhTrang, btnChucNang});
        }

        // Custom renderer để hiển thị JButton
        table.getColumn("Chức năng").setCellRenderer(new ButtonRenderer());
        table.getColumn("Chức năng").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

//    public void reloadPanel() {
//        JPanel parentPanel = (JPanel) this.getParent(); // Lấy panel cha
//        if (parentPanel != null) {
//            parentPanel.remove(this);  // Xóa panel hiện tại
//            ThongTinCaNhanPhuHuynh newPanel = new ThongTinCaNhanPhuHuynh(); // Tạo lại panel mới
//            parentPanel.add(newPanel); // Thêm panel mới vào giao diện
//            parentPanel.revalidate();  // Làm mới bố cục
//            parentPanel.repaint();     // Vẽ lại giao diện
//        } else {
//            System.out.println("Lỗi: Không tìm thấy panel cha!");
//        }
//    }
// CLASS HIỂN THỊ NÚT TRONG JTABLE
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JButton) {
                JButton button = (JButton) value;
                return button;
            }
            return this;
        }
    }

// CLASS XỬ LÝ SỰ KIỆN KHI NHẤN NÚT
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (value instanceof JButton) {
                button = (JButton) value;
                label = button.getText();
            }
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return button;
        }
    }

    public JPanel giaoDien_1_ThongTinCaNhan() {
        // Màn hình xanh ngọc
        JPanel blue_2_Panel = new JPanel();
        blue_2_Panel.setBackground(Color.decode("#D0EBF8"));
        blue_2_Panel.setBounds(20, 150, 635, 450);
        blue_2_Panel.setLayout(null); // Bỏ layout để tự set vị trí

        JLabel blue_2_tieuDe_ta = new JLabel("THÔNG TIN CÁ NHÂN", JLabel.CENTER);
        blue_2_tieuDe_ta.setFont(new Font("Arial", Font.BOLD, 25));
        blue_2_tieuDe_ta.setBounds(100, 15, 400, 40);

        JLabel lbl_MaHV_ta = new JLabel("Mã phụ huynh:");
        lbl_MaHV_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_MaHV_ta.setBounds(20, 70, 100, 30);

        JTextField txt_MaHV_ta = new JTextField();
        txt_MaHV_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        txt_MaHV_ta.setBounds(120, 70, 300, 30);

        JLabel lbl_HoVaTen_ta = new JLabel("Họ và tên:");
        lbl_HoVaTen_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_HoVaTen_ta.setBounds(20, 120, 100, 30);

        JTextField txt_HoVaTen_ta = new JTextField();
        txt_HoVaTen_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        txt_HoVaTen_ta.setBounds(120, 120, 300, 30);

        JLabel lbl_TenTaiKhoan_ta = new JLabel("Tên tài khoản:");
        lbl_TenTaiKhoan_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_TenTaiKhoan_ta.setBounds(20, 170, 100, 30);

        JTextField txt_TenTaiKhoan_ta = new JTextField();
        txt_TenTaiKhoan_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        txt_TenTaiKhoan_ta.setBounds(120, 170, 300, 30);

        JLabel lbl_MatKhau_ta = new JLabel("Mật khẩu:");
        lbl_MatKhau_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_MatKhau_ta.setBounds(20, 220, 100, 30);

        JPasswordField pf_MatKhau_ta = new JPasswordField();
        pf_MatKhau_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        pf_MatKhau_ta.setBounds(120, 220, 300, 30);

        JLabel lbl_Email_ta = new JLabel("Email:");
        lbl_Email_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_Email_ta.setBounds(20, 270, 100, 30);

        JTextField txt_Email_ta = new JTextField();
        txt_Email_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        txt_Email_ta.setBounds(120, 270, 300, 30);

        JLabel lbl_SoDienThoai_ta = new JLabel("Số điện thoại:");
        lbl_SoDienThoai_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_SoDienThoai_ta.setBounds(20, 320, 100, 30);

        JTextField txt_SoDienThoai_ta = new JTextField();
        txt_SoDienThoai_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        txt_SoDienThoai_ta.setBounds(120, 320, 300, 30);

        JLabel lbl_lienKet_ta = new JLabel("Liên kết:");
        lbl_lienKet_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_lienKet_ta.setBounds(20, 370, 100, 30);

        rdo_DaLienKet_ta = new JRadioButton("Đã liên kết");
        rdo_ChuaLienKet_ta = new JRadioButton("Chưa liên kết");
        ButtonGroup group_TrangThai_ta = new ButtonGroup();
        rdo_ChuaLienKet_ta.setBackground(Color.decode("#D0EBF8"));
        rdo_DaLienKet_ta.setBackground(Color.decode("#D0EBF8"));
        group_TrangThai_ta.add(rdo_DaLienKet_ta);
        group_TrangThai_ta.add(rdo_ChuaLienKet_ta);
        rdo_DaLienKet_ta.setBounds(120, 370, 100, 30);
        rdo_ChuaLienKet_ta.setBounds(280, 370, 100, 30);

        HienThiTrangThai();

        JLabel lbl_Anh_ta = new JLabel();
        lbl_Anh_ta.setBounds(450, 80, 150, 227);
        lbl_Anh_ta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lbl_Anh_ta.setHorizontalAlignment(JLabel.CENTER);

        JButton btn_ChinhSua_ta = new JButton("Chỉnh Sửa");
        btn_ChinhSua_ta.setBounds(230, 410, 200, 30);
        btn_ChinhSua_ta.setFont(new Font("Arial", Font.BOLD, 14));
        btn_ChinhSua_ta.setForeground(Color.BLACK);
        btn_ChinhSua_ta.setBackground(Color.WHITE);

        JFrame frame = new JFrame("Chỉnh sửa");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        txt_MaHV_ta.setEditable(false);
        txt_HoVaTen_ta.setEditable(false);
        txt_TenTaiKhoan_ta.setEditable(false);
        pf_MatKhau_ta.setEditable(false);
        txt_Email_ta.setEditable(false);
        txt_SoDienThoai_ta.setEditable(false);

        rdo_ChuaLienKet_ta.setEnabled(false);
        rdo_DaLienKet_ta.setEnabled(false);

        txt_MaHV_ta.setText(user.getID_Tai_Khoan() + "");
        txt_HoVaTen_ta.setText(user.getHo_Va_Ten());
        txt_TenTaiKhoan_ta.setText(user.getUsername());
        pf_MatKhau_ta.setText("****************");
        txt_Email_ta.setText(user.getEmail());
        txt_SoDienThoai_ta.setText(user.getSo_Dien_Thoai());

        setImageAuto(lbl_Anh_ta, user.getAvatar());

//        boolean check = ServiceThongTinTaiKhoan.CheckKetNoiPhuHuynh(user.getID_Tai_Khoan());
//        if (check == true) {
//            rdo_DaLienKet_ta.setSelected(true);
//        } else {
//            rdo_ChuaLienKet_ta.setSelected(true);
//        }
        // Bắt sự kiện khi nhấn nút "Chỉnh sửa thông tin"
        btn_ChinhSua_ta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn chỉnh sửa thông tin cá nhân?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // Xóa panel cũ nếu có (theo tên "BLUE_3")
                    Component[] components = blueCardPanel.getComponents();
                    for (Component comp : components) {
                        if (comp.getName() != null && comp.getName().equals("BLUE_3")) {
                            blueCardPanel.remove(comp);
                            break;
                        }
                    }

                    // Tạo lại panel chỉnh sửa và gán tên để quản lý
                    JPanel blue_3_Panel = giaoDien_2_ChinhSuaThongTinCaNhan();
                    blue_3_Panel.setName("BLUE_3");
                    blueCardPanel.add(blue_3_Panel, "BLUE_3");

                    // Cập nhật lại giao diện sau khi thêm panel mới
                    blueCardPanel.revalidate();
                    blueCardPanel.repaint();

                    // Chuyển sang panel chỉnh sửa thông tin
                    cardLayout.show(blueCardPanel, "BLUE_3");
                }
            }
        });

        btn_ChinhSua_ta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_ChinhSua_ta.setBackground(Color.decode("#064469")); // Chuyển sang màu xanh đậm khi hover
                btn_ChinhSua_ta.setForeground(Color.WHITE); // Chữ trắng khi hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_ChinhSua_ta.setBackground(Color.WHITE); // Trở lại màu trắng khi rời chuột
                btn_ChinhSua_ta.setForeground(Color.BLACK); // Chữ quay về màu đen
            }
        });

        txt_MaHV_ta.setBackground(Color.decode("#D5E9F2"));
        txt_HoVaTen_ta.setBackground(Color.decode("#D5E9F2"));
        txt_TenTaiKhoan_ta.setBackground(Color.decode("#D5E9F2"));
        pf_MatKhau_ta.setBackground(Color.decode("#D5E9F2"));
        txt_Email_ta.setBackground(Color.decode("#D5E9F2"));
        txt_SoDienThoai_ta.setBackground(Color.decode("#D5E9F2"));

        blue_2_Panel.add(blue_2_tieuDe_ta);
        blue_2_Panel.add(lbl_MaHV_ta);
        blue_2_Panel.add(lbl_HoVaTen_ta);
        blue_2_Panel.add(lbl_TenTaiKhoan_ta);
        blue_2_Panel.add(lbl_MatKhau_ta);
        blue_2_Panel.add(lbl_Email_ta);
        blue_2_Panel.add(lbl_SoDienThoai_ta);
        blue_2_Panel.add(lbl_lienKet_ta);
        blue_2_Panel.add(txt_MaHV_ta);
        blue_2_Panel.add(txt_HoVaTen_ta);
        blue_2_Panel.add(txt_TenTaiKhoan_ta);
        blue_2_Panel.add(pf_MatKhau_ta);
        blue_2_Panel.add(txt_Email_ta);
        blue_2_Panel.add(txt_SoDienThoai_ta);
        blue_2_Panel.add(rdo_ChuaLienKet_ta);
        blue_2_Panel.add(rdo_DaLienKet_ta);
        blue_2_Panel.add(lbl_Anh_ta);
        blue_2_Panel.add(btn_ChinhSua_ta);
        return blue_2_Panel;
    }

    public void setImageAuto(JLabel lbl_Anh_ta, String imagePath) {
        int panelWidth = lbl_Anh_ta.getWidth() > 0 ? lbl_Anh_ta.getWidth() : 150;
        int panelHeight = lbl_Anh_ta.getHeight() > 0 ? lbl_Anh_ta.getHeight() : 150;

        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
        lbl_Anh_ta.setIcon(new ImageIcon(scaledImage));
    }
    public String Anh;

    public static boolean verifyOTP(OTPObject otpObject, String otpNhap) {
        return otpObject != null && otpObject.getOtp().equals(otpNhap);
    }

    public class DatabaseHelper {

        private static final String URL = "jdbc:sqlserver://WALLNGUYEN:1433;databaseName=Coach_Me_AI;trustServerCertificate=true;user=sa;password=gacon";

        // Phương thức lấy kết nối CSDL
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL);
        }

    }

    private OTPObject otpObject;

    public void FromXacMinhEmail(JTextField txt_Email) {
        JFrame FromXacMinh = new JFrame();
        FromXacMinh.setTitle("Xác minh tài khoản email");
        FromXacMinh.setSize(600, 350);
        FromXacMinh.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FromXacMinh.setLocationRelativeTo(null);
        FromXacMinh.setVisible(true);
        FromXacMinh.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(3, 39, 87));
        FromXacMinh.add(panel);

        ImageIcon robotIcon = new ImageIcon("D:\\Java\\New folder (2)\\z6416487122964_65ca6b22dce78d26959448de7cc7ae42.jpg");
        Image img = robotIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        JLabel lblRobot = new JLabel(new ImageIcon(img));
        lblRobot.setBounds(30, 50, 180, 180);
        panel.add(lblRobot);

        JLabel lblTitle = new JLabel("Xác minh tài khoản email", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(280, 60, 270, 30);
        panel.add(lblTitle);

        JLabel lbl_GT = new JLabel("Mã xác minh đã được gửi đến email đăng ký của bạn");
        lbl_GT.setForeground(Color.WHITE);
        lbl_GT.setBounds(280, 85, 280, 25);
        panel.add(lbl_GT);
        lbl_GT.setVisible(false);

        JLabel lbl_MaOTP = new JLabel("Nhập email");
        lbl_MaOTP.setForeground(Color.WHITE);
        lbl_MaOTP.setBounds(280, 120, 150, 25);
        panel.add(lbl_MaOTP);

        JTextField txt_MaOTP = new JPasswordField();
        txt_MaOTP.setBounds(280, 140, 280, 30);
        panel.add(txt_MaOTP);
        txt_MaOTP.setVisible(false);

        JTextField txt_NhapEmail = new JTextField();
        txt_NhapEmail.setBounds(280, 140, 280, 30);
        panel.add(txt_NhapEmail);

        // nut dang nhap
        JButton btn_Gui = new JButton("GỬI MÃ");
        btn_Gui.setBounds(280, 190, 280, 35);
        panel.add(btn_Gui);

        JButton btn_Check = new JButton("XÁC NHẬN");
        btn_Check.setBounds(280, 190, 280, 35);
        panel.add(btn_Check);
        btn_Check.setVisible(false);

        btn_Gui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean Check = true;
                String email = txt_NhapEmail.getText();
                if (!email.equals(user.getEmail())) {
                    String CheckEmail = ServiceValidate.CheckValidateEmail(email);
                    if (!CheckEmail.equals("")) {
                        JOptionPane.showMessageDialog(null, CheckEmail);
                        Check = false;
                    }
                }
                if (Check == true) {
                    try (Connection conn = DatabaseConnection.getConnection()) {
                        // Kiểm tra số lần gửi OTP (dùng email thay vì userId)

                        if (!GuiOTP.canSendOTP(email, conn)) {
                            JOptionPane.showMessageDialog(null, "Bạn đã gửi quá 5 lần trong 24 giờ qua. Không thể gửi thêm OTP.");
                            return;
                        }

                        // Gửi OTP và nhận đối tượng chứa OTP
                        otpObject = GuiOTP.sendOTP(email, conn); // Lưu OTP

                        if (otpObject.isSent()) {
                            JOptionPane.showMessageDialog(null, "Gửi mã thành công");
                            lbl_GT.setVisible(true);
                            lbl_MaOTP.setText("Nhập mã");
                            btn_Gui.setVisible(false);
                            txt_MaOTP.setVisible(true);
                            txt_NhapEmail.setVisible(false);
                            btn_Check.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Gửi mã xác minh email thất bại",
                                    "Thông báo gửi mã xác minh email",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi hệ thống! Vui lòng thử lại sau.");
                    }
                }
            }
        });

        btn_Check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String MaXacMinh = txt_MaOTP.getText();
                String email = txt_NhapEmail.getText();
                if (otpObject != null && verifyOTP(otpObject, MaXacMinh)) {
                    int ID = user.getID_Tai_Khoan();
                    boolean CheckDoiEmail = ServiceThongTinTaiKhoan.Upemail(email, ID);
                    if (CheckDoiEmail == true) {
                        JOptionPane.showMessageDialog(null, "Đổi Email thành công");
                        txt_Email.setText(email);
                        user = ServiceThongTinTaiKhoan.ThongTinTaiKhoan(ID);
                        Auth.login(user);
                        FromXacMinh.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Đổi Email thất bại");
                    }

                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "OTP không chính xác!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

    }

    public JPanel giaoDien_2_ChinhSuaThongTinCaNhan() {
        // Màn hình xanh ngọc
        JPanel blue_2_Panel = new JPanel();
        blue_2_Panel.setBackground(Color.decode("#D0EBF8"));
        blue_2_Panel.setBounds(20, 150, 635, 450);
        blue_2_Panel.setLayout(null); // Bỏ layout để tự set vị trí

        JLabel blue_2_tieuDe_ta = new JLabel("THÔNG TIN CÁ NHÂN", JLabel.CENTER);
        blue_2_tieuDe_ta.setFont(new Font("Arial", Font.BOLD, 25));
        blue_2_tieuDe_ta.setBounds(100, 15, 400, 40);

        JLabel lbl_MaHV_ta = new JLabel("Mã phụ huynh:");
        lbl_MaHV_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_MaHV_ta.setBounds(20, 70, 100, 30);

        JTextField txt_MaHV_ta = new JTextField();
        txt_MaHV_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        txt_MaHV_ta.setBounds(120, 70, 300, 30);

        JLabel lbl_HoVaTen_ta = new JLabel("Họ và tên:");
        lbl_HoVaTen_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_HoVaTen_ta.setBounds(20, 120, 100, 30);

        JLabel lbl_ErorHoTen = new JLabel("Eror");
        lbl_ErorHoTen.setBounds(121, 98, 300, 30);
        lbl_ErorHoTen.setForeground(Color.red);
        blue_2_Panel.add(lbl_ErorHoTen);

        JTextField txt_HoVaTen_ta = new JTextField();
        txt_HoVaTen_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        txt_HoVaTen_ta.setBounds(120, 120, 300, 30);

        JLabel lbl_TenTaiKhoan_ta = new JLabel("Tên tài khoản:");
        lbl_TenTaiKhoan_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_TenTaiKhoan_ta.setBounds(20, 170, 100, 30);

        JLabel lbl_ErorTaiKhoan = new JLabel("Eror");
        lbl_ErorTaiKhoan.setBounds(121, 148, 300, 30);
        lbl_ErorTaiKhoan.setForeground(Color.red);
        blue_2_Panel.add(lbl_ErorTaiKhoan);

        JTextField txt_TenTaiKhoan_ta = new JTextField();
        txt_TenTaiKhoan_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        txt_TenTaiKhoan_ta.setBounds(120, 170, 300, 30);

        JLabel lbl_MatKhau_ta = new JLabel("Mật khẩu:");
        lbl_MatKhau_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_MatKhau_ta.setBounds(20, 220, 100, 30);

        JPasswordField pf_MatKhau_ta = new JPasswordField();
        pf_MatKhau_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        pf_MatKhau_ta.setBounds(120, 220, 300, 30);

        JLabel lbl_Email_ta = new JLabel("Email:");
        lbl_Email_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_Email_ta.setBounds(20, 270, 100, 30);

//        JLabel lbl_ErorEmail = new JLabel("Eror");
//        lbl_ErorEmail.setBounds(121, 248, 300, 30);
//        lbl_ErorEmail.setForeground(Color.red);
//        blue_2_Panel.add(lbl_ErorEmail);
//
//        JTextField txt_Email_ta = new JTextField();
//        txt_Email_ta.setFont(new Font("Arial", Font.PLAIN, 15));
//        txt_Email_ta.setBounds(120, 270, 300, 30);
        JTextField txt_Email_ta = new JTextField();
        txt_Email_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        txt_Email_ta.setBounds(120, 270, 230, 30);

        JButton btn_SuaEmail = new JButton("Đổi");
        btn_SuaEmail.setBounds(360, 270, 60, 30);
        blue_2_Panel.add(btn_SuaEmail);

        txt_Email_ta.setEditable(false);

        btn_SuaEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FromXacMinhEmail(txt_Email_ta);
            }
        });

        JLabel lbl_SoDienThoai_ta = new JLabel("Số điện thoại:");
        lbl_SoDienThoai_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_SoDienThoai_ta.setBounds(20, 320, 100, 30);

        JLabel lbl_ErorSDT = new JLabel("Eror");
        lbl_ErorSDT.setBounds(121, 298, 300, 30);
        lbl_ErorSDT.setForeground(Color.red);
        blue_2_Panel.add(lbl_ErorSDT);

        JTextField txt_SoDienThoai_ta = new JTextField();
        txt_SoDienThoai_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        txt_SoDienThoai_ta.setBounds(120, 320, 300, 30);

        JLabel lbl_lienKet_ta = new JLabel("Liên kết:");
        lbl_lienKet_ta.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl_lienKet_ta.setBounds(20, 370, 100, 30);

        rdo_DaLienKet_ta = new JRadioButton("Đã liên kết");
        rdo_ChuaLienKet_ta = new JRadioButton("Chưa liên kết");
        rdo_ChuaLienKet_ta.setBackground(Color.decode("#D0EBF8"));
        rdo_DaLienKet_ta.setBackground(Color.decode("#D0EBF8"));
        ButtonGroup group_TrangThai_ta = new ButtonGroup();
        group_TrangThai_ta.add(rdo_DaLienKet_ta);
        group_TrangThai_ta.add(rdo_ChuaLienKet_ta);
        rdo_DaLienKet_ta.setBounds(120, 370, 100, 30);
        rdo_ChuaLienKet_ta.setBounds(280, 370, 100, 30);

        HienThiTrangThai();

//        JLabel lbl_ErorAnh = new JLabel("Eror");
//        lbl_ErorAnh.setBounds(451, 58, 150, 30);
//        lbl_ErorAnh.setForeground(Color.red);
//        blue_2_Panel.add(lbl_ErorAnh);
        JLabel lbl_Anh_ta = new JLabel();
        lbl_Anh_ta.setBounds(450, 80, 150, 227);
        lbl_Anh_ta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lbl_Anh_ta.setHorizontalAlignment(JLabel.CENTER);

        JButton btn_ChonAnh_ta = new JButton("Chọn Ảnh");
        btn_ChonAnh_ta.setBounds(475, 320, 100, 30);
        btn_ChonAnh_ta.setFont(new Font("Arial", Font.BOLD, 14));
        btn_ChonAnh_ta.setForeground(Color.BLACK);
        btn_ChonAnh_ta.setBackground(Color.WHITE);

        JButton btn_CapNhat_ta = new JButton("Cập Nhật");
        btn_CapNhat_ta.setBounds(230, 410, 200, 30);
        btn_CapNhat_ta.setFont(new Font("Arial", Font.BOLD, 14));
        btn_CapNhat_ta.setForeground(Color.BLACK);
        btn_CapNhat_ta.setBackground(Color.WHITE);

        //Dan them
        txt_MaHV_ta.setEditable(false);
        pf_MatKhau_ta.setEditable(false);
        rdo_ChuaLienKet_ta.setEnabled(false);
        rdo_DaLienKet_ta.setEnabled(false);

        txt_MaHV_ta.setText(user.getID_Tai_Khoan() + "");
        txt_HoVaTen_ta.setText(user.getHo_Va_Ten());
        txt_TenTaiKhoan_ta.setText(user.getUsername());
        pf_MatKhau_ta.setText("****************");
        txt_Email_ta.setText(user.getEmail());
        txt_SoDienThoai_ta.setText(user.getSo_Dien_Thoai());

        setImageAuto(lbl_Anh_ta, user.getAvatar());
        Anh = user.getAvatar();

//        boolean check = ServiceThongTinTaiKhoan.CheckKetNoiPhuHuynh(user.getID_Tai_Khoan());
//        if (check == true) {
//            rdo_DaLienKet_ta.setSelected(true);
//        } else {
//            rdo_ChuaLienKet_ta.setSelected(true);
//        }
        lbl_ErorTaiKhoan.setVisible(false);
        lbl_ErorHoTen.setVisible(false);
        //lbl_ErorEmail.setVisible(false);
        lbl_ErorSDT.setVisible(false);
        //lbl_ErorAnh.setVisible(false);

        btn_ChonAnh_ta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("CHON ANH", "jpg", "png", "gif"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                    Anh = imagePath;
                    //JOptionPane.showMessageDialog(null, Anh); // Test đường dẫn
                    // Đặt ảnh vào lbl_Anh_ta (hình chữ nhật)
                    int panelWidth = lbl_Anh_ta.getWidth() > 0 ? lbl_Anh_ta.getWidth() : 100;
                    int panelHeight = lbl_Anh_ta.getHeight() > 0 ? lbl_Anh_ta.getHeight() : 100;
                    ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH));
                    lbl_Anh_ta.setIcon(icon);

                }
            }
        });

        // Tạo hiệu ứng hover (chuyển màu khi di chuột vào)
        btn_ChonAnh_ta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_ChonAnh_ta.setBackground(Color.decode("#064469")); // Chuyển sang màu xanh đậm khi hover
                btn_ChonAnh_ta.setForeground(Color.WHITE); // Chữ trắng khi hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_ChonAnh_ta.setBackground(Color.WHITE); // Trở lại màu trắng khi rời chuột
                btn_ChonAnh_ta.setForeground(Color.BLACK); // Chữ quay về màu đen
            }
        });

        btn_CapNhat_ta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_CapNhat_ta.setBackground(Color.decode("#064469")); // Chuyển sang màu xanh đậm khi hover
                btn_CapNhat_ta.setForeground(Color.WHITE); // Chữ trắng khi hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_CapNhat_ta.setBackground(Color.WHITE); // Trở lại màu trắng khi rời chuột
                btn_CapNhat_ta.setForeground(Color.BLACK); // Chữ quay về màu đen
            }
        });

        txt_HoVaTen_ta.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lbl_ErorHoTen.setVisible(false);

            }

            @Override
            public void focusLost(FocusEvent e) {
                // Không cần làm gì khi mất focus
            }
        });

        txt_TenTaiKhoan_ta.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lbl_ErorTaiKhoan.setVisible(false);

            }

            @Override
            public void focusLost(FocusEvent e) {
                // Không cần làm gì khi mất focus
            }
        });

//        txt_Email_ta.addFocusListener(new FocusListener() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                lbl_ErorEmail.setVisible(false);
//
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                // Không cần làm gì khi mất focus
//            }
//        });
        txt_SoDienThoai_ta.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lbl_ErorSDT.setVisible(false);

            }

            @Override
            public void focusLost(FocusEvent e) {
                // Không cần làm gì khi mất focus
            }
        });

        btn_CapNhat_ta.addActionListener(e -> {

            String hoten = txt_HoVaTen_ta.getText();
            String tentaikhoan = txt_TenTaiKhoan_ta.getText();
            String sdt = txt_SoDienThoai_ta.getText();
            if (hoten.equals(user.getHo_Va_Ten())
                    && tentaikhoan.equals(user.getUsername())
                    && sdt.equals(user.getSo_Dien_Thoai())
                    && Anh.equals(user.getAvatar())) {
                JOptionPane.showMessageDialog(
                        null,
                        "Vui lòng sửa thông tin trước khi cập nhật",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                boolean Check = true;
                if (!tentaikhoan.equals(user.getUsername())) {
                    String CheckTentaikhoan = ServiceValidate.CheckValidateUserName(tentaikhoan);
                    if (!CheckTentaikhoan.equals("")) {
                        lbl_ErorTaiKhoan.setText(CheckTentaikhoan);
                        lbl_ErorTaiKhoan.setVisible(true);
                        Check = false;
                    }
                }

                String CheckHoTen = ServiceValidate.CheckValidateHo_Va_Ten(hoten);
                if (!CheckHoTen.equals("")) {
                    lbl_ErorHoTen.setText(CheckHoTen);
                    lbl_ErorHoTen.setVisible(true);
                    Check = false;
                }

//                if (!email.equals(user.getEmail())) {
//                    String CheckEmail = ServiceValidate.CheckValidateEmail(email);
//                    if (!CheckEmail.equals("")) {
//                        lbl_ErorEmail.setText(CheckEmail);
//                        lbl_ErorEmail.setVisible(true);
//                        Check = false;
//                    }
//                }
                String CheckSDT = ServiceValidate.CheckValidateSo_Dien_Thoai(sdt);
                if (!CheckSDT.equals("")) {
                    lbl_ErorSDT.setText(CheckSDT);
                    lbl_ErorSDT.setVisible(true);
                    Check = false;
                }

                if (Check == true) {
                    int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn cập nhật thông tin?",
                            "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        int ID = user.getID_Tai_Khoan();
                        boolean CheckUpdate = ServiceThongTinTaiKhoan.DoiThongTin(tentaikhoan, hoten, Anh, sdt, ID);
                        if (CheckUpdate == true) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Cập nhật thông tin thành công!",
                                    "Thông báo",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            user = ServiceThongTinTaiKhoan.ThongTinTaiKhoan(ID);
                            Auth.login(user);
                            //JOptionPane.showMessageDialog(null, user.getAvatar());
                            lbl_white_anhTron_ta.setImage(user.getAvatar());
                            txt_white_ten_ta.setText(user.getUsername());
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Cập nhật thông tin thât bại!",
                                    "Thông báo",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    }
                }
            }
        }
        );

        txt_MaHV_ta.setBackground(Color.decode("#D5E9F2"));
        txt_HoVaTen_ta.setBackground(Color.decode("#D5E9F2"));
        txt_TenTaiKhoan_ta.setBackground(Color.decode("#D5E9F2"));
        pf_MatKhau_ta.setBackground(Color.decode("#D5E9F2"));
        txt_Email_ta.setBackground(Color.decode("#D5E9F2"));
        txt_SoDienThoai_ta.setBackground(Color.decode("#D5E9F2"));

        blue_2_Panel.add(blue_2_tieuDe_ta);
        blue_2_Panel.add(lbl_MaHV_ta);
        blue_2_Panel.add(lbl_HoVaTen_ta);
        blue_2_Panel.add(lbl_TenTaiKhoan_ta);
        blue_2_Panel.add(lbl_MatKhau_ta);
        blue_2_Panel.add(lbl_Email_ta);
        blue_2_Panel.add(lbl_SoDienThoai_ta);
        blue_2_Panel.add(lbl_lienKet_ta);
        blue_2_Panel.add(txt_MaHV_ta);
        blue_2_Panel.add(txt_HoVaTen_ta);
        blue_2_Panel.add(txt_TenTaiKhoan_ta);
        blue_2_Panel.add(pf_MatKhau_ta);
        blue_2_Panel.add(txt_Email_ta);
        blue_2_Panel.add(txt_SoDienThoai_ta);
        blue_2_Panel.add(rdo_ChuaLienKet_ta);
        blue_2_Panel.add(rdo_DaLienKet_ta);
        blue_2_Panel.add(lbl_Anh_ta);
        blue_2_Panel.add(btn_ChonAnh_ta);
        blue_2_Panel.add(btn_CapNhat_ta);
        return blue_2_Panel;
    }

    public JPanel giaoDien_3_DoiMatKhau() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#D0EBF8"));
        panel.setBounds(20, 150, 635, 450);
        panel.setLayout(null); // Bỏ layout để tự set vị trí

        JLabel lbl_matKhauCu = new JLabel("Mật khẩu cũ");
        lbl_matKhauCu.setBounds(30, 30, 100, 30);
        lbl_matKhauCu.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl_matKhauCu);

        JLabel lbl_ErorOldPass = new JLabel("Eror");
        lbl_ErorOldPass.setBounds(180, 8, 380, 30);
        lbl_ErorOldPass.setForeground(Color.red);
        panel.add(lbl_ErorOldPass);

        JPasswordField txt_matKhauCu = new JPasswordField();
        txt_matKhauCu.setBounds(180, 30, 380, 30);
        txt_matKhauCu.setEchoChar('•');
        panel.add(txt_matKhauCu);

        JButton btn_Hien = new JButton("Hiện");
        btn_Hien.setBounds(565, 30, 60, 30);
        panel.add(btn_Hien);

        JLabel lbl_matKhauMoi = new JLabel("Mật khẩu mới");
        lbl_matKhauMoi.setBounds(30, 80, 100, 30);
        lbl_matKhauMoi.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl_matKhauMoi);

        JLabel lbl_ErorNewPass = new JLabel("Eror");
        lbl_ErorNewPass.setBounds(180, 58, 380, 30);
        lbl_ErorNewPass.setForeground(Color.red);
        panel.add(lbl_ErorNewPass);

        JPasswordField txt_matKhauMoi = new JPasswordField();
        txt_matKhauMoi.setBounds(180, 80, 380, 30);
        txt_matKhauMoi.setEchoChar('•');
        panel.add(txt_matKhauMoi);

        JLabel lbl_xacNhanMatKhau = new JLabel("Xác nhận mật khẩu");
        lbl_xacNhanMatKhau.setBounds(30, 130, 150, 30);
        lbl_xacNhanMatKhau.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl_xacNhanMatKhau);

        JLabel lbl_ErorCheckPass = new JLabel("Eror");
        lbl_ErorCheckPass.setBounds(180, 108, 380, 30);
        lbl_ErorCheckPass.setForeground(Color.red);
        panel.add(lbl_ErorCheckPass);

        JPasswordField txt_xacNhanMatKhau = new JPasswordField();
        txt_xacNhanMatKhau.setBounds(180, 130, 380, 30);
        txt_xacNhanMatKhau.setEchoChar('•');
        panel.add(txt_xacNhanMatKhau);

        btn_Hien.addActionListener(new ActionListener() {
            private boolean isVisible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                char echoChar = isVisible ? '•' : (char) 0; // Nếu đang hiển thị → Ẩn, ngược lại thì hiển thị

                txt_matKhauCu.setEchoChar(echoChar);
                txt_matKhauMoi.setEchoChar(echoChar);
                txt_xacNhanMatKhau.setEchoChar(echoChar);

                btn_Hien.setText(isVisible ? "Hiện" : "Ẩn"); // Cập nhật text nút

                isVisible = !isVisible; // Đảo trạng thái
            }
        });

        lbl_ErorOldPass.setVisible(false);
        lbl_ErorNewPass.setVisible(false);
        lbl_ErorCheckPass.setVisible(false);

        txt_matKhauCu.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lbl_ErorOldPass.setVisible(false);

            }

            @Override
            public void focusLost(FocusEvent e) {
                // Không cần làm gì khi mất focus
            }
        });

        txt_matKhauMoi.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lbl_ErorNewPass.setVisible(false);

            }

            @Override
            public void focusLost(FocusEvent e) {
                // Không cần làm gì khi mất focus
            }
        });

        txt_xacNhanMatKhau.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lbl_ErorCheckPass.setVisible(false);

            }

            @Override
            public void focusLost(FocusEvent e) {
                // Không cần làm gì khi mất focus
            }
        });

        JButton btn_XacNhan = new JButton("Xác Nhận");
        btn_XacNhan.setBounds(200, 185, 100, 30);
        btn_XacNhan.setBackground(new Color(10, 38, 74));
        btn_XacNhan.setForeground(Color.WHITE);
        btn_XacNhan.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        panel.add(btn_XacNhan);

        JFrame frame = new JFrame("Đổi mật khẩu");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        btn_XacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String OldPass = String.valueOf(txt_matKhauCu.getPassword());
                String NewPass = String.valueOf(txt_matKhauMoi.getPassword());
                String CheckNewPass = String.valueOf(txt_xacNhanMatKhau.getPassword());

                boolean Check = true;

                String CheckValidateOldPass = ServiceValidate.CheckValidatePassword(OldPass);
                if (!CheckValidateOldPass.equals("")) {
                    lbl_ErorOldPass.setText(CheckValidateOldPass);
                    lbl_ErorOldPass.setVisible(true);
                    Check = false;
                } else {
                    if (!OldPass.equals(user.getPassword())) {
                        lbl_ErorOldPass.setText("Mật khẩu không đúng, vui lòng nhập lại");
                        lbl_ErorOldPass.setVisible(true);
                        Check = false;
                    }
                }

                String CheckValidateNewPass = ServiceValidate.CheckValidatePassword(NewPass);
                if (!CheckValidateNewPass.equals("")) {
                    lbl_ErorNewPass.setText(CheckValidateNewPass);
                    lbl_ErorNewPass.setVisible(true);
                    Check = false;
                }

                String CheckValidateCheckNewPass = ServiceValidate.CheckValidatePassword(CheckNewPass);
                if (!CheckValidateCheckNewPass.equals("")) {
                    lbl_ErorCheckPass.setText(CheckValidateCheckNewPass);
                    lbl_ErorCheckPass.setVisible(true);
                    Check = false;
                } else {
                    if (NewPass == null || NewPass.isBlank()) {
                        lbl_ErorCheckPass.setText("Vui lòng nhập mật khẩu mới trước");
                        lbl_ErorCheckPass.setVisible(true);
                        Check = false;
                    } else {
                        if (!CheckNewPass.equals(NewPass)) {
                            lbl_ErorCheckPass.setText("Mật khẩu chưa trùng khớp");
                            lbl_ErorCheckPass.setVisible(true);
                            Check = false;
                        }
                    }
                }
                if (Check == true) {
                    int result = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn thay đổi mật khẩu?",
                            "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        // Kiểm tra xem giaoDien_2_ChinhSuaThongTinCaNhan đã được thêm vào chưa
                        if (blueCardPanel.getComponentCount() == 0) {
                            JPanel blue_4_Panel = giaoDien_3_DoiMatKhau();
                            blueCardPanel.add(blue_4_Panel, "BLUE_4");
                        }

                        // Chuyển sang panel chỉnh sửa thông tin
                        cardLayout.show(blueCardPanel, "BLUE_4");

                        int ID = user.getID_Tai_Khoan();
                        boolean CheckDoiMatKhau = ServiceThongTinTaiKhoan.DoiPass(NewPass, ID);
                        if (CheckDoiMatKhau == true) {
                            user = ServiceThongTinTaiKhoan.ThongTinTaiKhoan(ID);
                            Auth.login(user);
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Đổi mật khẩu thành công!",
                                    "Thông báo",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }

                    }
                }
            }
        });

        JButton btn_Huy = new JButton("Hủy");

        btn_Huy.setEnabled(false); // Khóa nút Hủy ban đầu

// Phương thức kiểm tra xem có dữ liệu nhập vào chưa
        DocumentListener docListener = new DocumentListener() {
            private void checkFields() {
                String oldPass = new String(txt_matKhauCu.getPassword());
                String newPass = new String(txt_matKhauMoi.getPassword());
                String confirmPass = new String(txt_xacNhanMatKhau.getPassword());

                // Nếu ít nhất một ô có dữ liệu, mở khóa nút Hủy
                btn_Huy.setEnabled(!oldPass.isBlank() || !newPass.isBlank() || !confirmPass.isBlank());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFields();
            }
        };

// Gắn DocumentListener vào các trường mật khẩu
        txt_matKhauCu.getDocument().addDocumentListener(docListener);
        txt_matKhauMoi.getDocument().addDocumentListener(docListener);
        txt_xacNhanMatKhau.getDocument().addDocumentListener(docListener);

// Sự kiện nút Hủy
        btn_Huy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_matKhauCu.setText("");
                txt_matKhauMoi.setText("");
                txt_xacNhanMatKhau.setText("");
                btn_Huy.setEnabled(false); // Khóa lại nút Hủy sau khi xóa mật khẩu
            }
        });

        btn_Huy.setBounds(
                320, 185, 100, 30);
        btn_Huy.setBackground(
                new Color(10, 38, 74));
        btn_Huy.setForeground(Color.WHITE);

        btn_Huy.setFont(
                new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        panel.add(btn_Huy);

        return panel;
    }

    private ImageIcon createCircularImageIcon(String imagePath, int diameter) {
        try {
            // Đọc ảnh gốc
            BufferedImage originalImage = ImageIO.read(new File(imagePath));

            // Resize ảnh về kích thước hình tròn mong muốn
            Image resizedImage = originalImage.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
            BufferedImage circleBuffer = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = circleBuffer.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Cắt ảnh thành hình tròn
            Ellipse2D.Double clip = new Ellipse2D.Double(0, 0, diameter, diameter);
            g2.setClip(clip);
            g2.drawImage(resizedImage, 0, 0, null);
            g2.dispose();

            return new ImageIcon(circleBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JLabel createMenuItem(String text, String iconPath) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image img = icon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH); // Chỉnh ảnh nhỏ lại
        icon = new ImageIcon(img);

        JLabel label = new JLabel(text, icon, JLabel.LEFT);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    class CircleLabel extends JLabel {

        private int diameter;
        private BufferedImage image; // Lưu trữ ảnh

        public CircleLabel(int diameter) {
            this.diameter = diameter;
            setPreferredSize(new Dimension(diameter, diameter));
        }

        // Phương thức mới để set ảnh từ đường dẫn
        public void setImage(String imagePath) {
            if (imagePath == null || imagePath.isEmpty()) {
                System.err.println("Đường dẫn ảnh không hợp lệ.");
                return;
            }

            try {
                BufferedImage img = ImageIO.read(new File(imagePath));
                if (img != null) {
                    this.image = createCircularImage(img, diameter);
                    repaint(); // Cập nhật giao diện khi có ảnh mới
                } else {
                    System.err.println("Ảnh không hợp lệ hoặc không thể đọc.");
                }
            } catch (IOException e) {
                System.err.println("Không thể tải ảnh: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Vẽ hình tròn màu xám nếu chưa có ảnh
            if (image == null) {
                g2d.setColor(Color.GRAY);
                g2d.fillOval(0, 0, diameter, diameter);
            } else {
                g2d.drawImage(image, 0, 0, null);
            }
        }

        private BufferedImage createCircularImage(BufferedImage img, int size) {
            // Resize ảnh về kích thước mong muốn
            Image scaledImage = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            BufferedImage output = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = output.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Cắt ảnh thành hình tròn
            Ellipse2D.Double clip = new Ellipse2D.Double(0, 0, size, size);
            g2.setClip(clip);
            g2.drawImage(scaledImage, 0, 0, null);
            g2.dispose();

            return output;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Thông tin cá nhân");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(710, 650);
            frame.setResizable(false);
            ThongTinCaNhanPhuHuynh ttcnPanel = new ThongTinCaNhanPhuHuynh();
            frame.add(ttcnPanel);
            frame.setLocationRelativeTo(null); // Căn giữa màn hình
            frame.setVisible(true);
        });
    }
}
