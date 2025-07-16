/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import com.mycompany.DAO.Dao;
import com.mycompany.entity.BaiViet;
import com.mycompany.utils.Auth;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class trangchu extends JFrame {

    private JPanel leftMenuPanel;
    public static JPanel mainContentPanel;
    private JButton chatButton;
    Dao dao = new Dao();
    ArrayList<BaiViet> danhSachBaiViet = dao.getAllBaiViet();

    // Kích thước frame
    private static final int FRAME_WIDTH = 850;
    private static final int FRAME_HEIGHT = 650;
    // Kích thước panel menu trái
    private static final int LEFT_MENU_WIDTH = 160;

    private JLabel monthLabel;
    private JPanel daysPanel;
    private LocalDate selectedDate;
    private YearMonth currentMonth;

    // Tham số tùy chỉnh cho kích thước logo
    // (Bạn có thể chỉnh chiều rộng / cao theo thiết kế)
    private static final int LOGO_WIDTH = 140;
    private static final int LOGO_HEIGHT = 140;

    public JPanel getContentpanel() {
        return mainContentPanel;
    }

    public trangchu(String ten) {

        super("CMAI");
        setResizable(false);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo panel bên trái (menu)
        leftMenuPanel = new JPanel();
        leftMenuPanel.setBackground(new Color(3, 39, 87));
        leftMenuPanel.setPreferredSize(new Dimension(LEFT_MENU_WIDTH, FRAME_HEIGHT));
        leftMenuPanel.setLayout(new BorderLayout());
        leftMenuPanel.setBackground(new Color(230, 230, 230)); // ví dụ màu xám nhạt
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new CardLayout());
        mainContentPanel.setBackground(Color.WHITE);

        // ========== PHẦN LOGO Ở TRÊN CÙNG ==========
        // Tạo panel chứa logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(3, 39, 87));
        logoPanel.setLayout(new BorderLayout());

        // Label để chứa hình logo
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Load và scale ảnh
        // Thay đường dẫn "logo.png" bằng file logo thực tế của bạn
        try {
            BufferedImage originalImage = ImageIO.read(new File("D:\\coachmeai\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\COACH ME.png"));
            Image scaledImage = originalImage.getScaledInstance(LOGO_WIDTH, LOGO_HEIGHT, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            logoLabel.setIcon(scaledIcon);
        } catch (IOException e) {
            e.printStackTrace();
            // Nếu lỗi, bạn có thể set text để báo không load được ảnh
            logoLabel.setText("Logo");
        }

        logoPanel.add(logoLabel, BorderLayout.CENTER);
        // Thêm logoPanel vào top của leftMenuPanel
        leftMenuPanel.add(logoPanel, BorderLayout.NORTH);

        // ========== PHẦN MENU CON Ở GIỮA ==========
        // Tạo một panel để chứa các item menu con
        JPanel subMenuPanel = new JPanel();
        subMenuPanel.setBackground(new Color(3, 39, 87));
        // Sử dụng BoxLayout theo trục Y để xếp các menu con từ trên xuống
        subMenuPanel.setLayout(new BoxLayout(subMenuPanel, BoxLayout.Y_AXIS));

        // ========== PANEL CHÍNH BÊN PHẢI ==========
        if (ten.equals("hocvien")) {
            JPanel trangChu = new JPanel(new BorderLayout());
            JPanel lichHocvaNhiemVu = new JPanel(new BorderLayout());

            //JPanel ketQuatest = new JPanel(new BorderLayout());
            JPanel DongHoGD = new JPanel(new BorderLayout());
            JPanel FeedbackPHHV = new JPanel(new BorderLayout());
            JPanel Thongbao = new JPanel(new BorderLayout());
            JPanel ThongtinCanhan = new JPanel(new BorderLayout());
            JPanel howTo = new JPanel(new BorderLayout());

            trangChu.setBackground(Color.WHITE);

            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.TOP);
            try {
                BufferedImage originalImage = ImageIO.read(new File("D:\\coachmeai\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\PIC_CMAI.png"));
                Image scaledImage = originalImage.getScaledInstance(710, 400, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                imageLabel.setIcon(scaledIcon);
            } catch (IOException e) {
                e.printStackTrace();
                // Nếu lỗi, bạn có thể set text để báo không load được ảnh
                imageLabel.setText("Logo");
            }

            trangChu.add(imageLabel, BorderLayout.NORTH);
            mainContentPanel.add(trangChu, "trangChu");
            mainContentPanel.add(lichHocvaNhiemVu, "lichHocvaNhiemvu");
            //mainContentPanel.add(ketQuatest, "ketQuatest");
            mainContentPanel.add(DongHoGD, "DongHoGD");
            mainContentPanel.add(FeedbackPHHV, "FeedbackPHHV");
            mainContentPanel.add(Thongbao, "Thongbao");
            mainContentPanel.add(ThongtinCanhan, "ThongtinCanhan");

            mainContentPanel.add(howTo, "howTo");

            add(leftMenuPanel, BorderLayout.WEST);
            add(mainContentPanel, BorderLayout.CENTER);

            JPanel articleListPanel = new JPanel();
            articleListPanel.setSize(710, 180);
            articleListPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15)); // Cách đều giữa các bài viết
            articleListPanel.setBackground(new Color(240, 240, 240)); // Màu nền tổng thể

            for (BaiViet baiViet : danhSachBaiViet) {
                System.out.println("có chạy vaod dây");
                articleListPanel.add(createArticlePanel(baiViet));
            }

            JScrollPane scrollPane = new JScrollPane(articleListPanel);
            scrollPane.setPreferredSize(new Dimension(710, 210));
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

            trangChu.add(scrollPane, BorderLayout.SOUTH);

            // lichHocvaNhiemVu
            LichHocGD lichHocGD = new LichHocGD(350, 500);
            lichHocvaNhiemVu.add(lichHocGD, BorderLayout.WEST);
            NhiemVuHocVien nv = new NhiemVuHocVien(Auth.getUser().getID_Tai_Khoan());
            lichHocGD.setNgayDuocChonListener(date -> {
                nv.setSelectedDate(date);
                nv.fillDanhSachNhiemVuTheoNgay(date);
            });

            lichHocvaNhiemVu.add(nv, BorderLayout.EAST);
            lichHocvaNhiemVu.setBackground(Color.white);

            //baiTest - bài test
            //baiTest.add(new TestInterface());
            //ketQuatest - kết quả bài test
            //ketQuatest.add(new TestResultPanel());
            //DongHo - đồng hồ
            DongHoGD.add(new DongHo());

            //FeedbackPHHV
            FeedbackPHHV.add(new FeedBackPHHV());

            //ThongBao
            Thongbao.add(new ThongBao(Auth.getUser().getID_Tai_Khoan()));

            //ThongTinCaNhan
            ThongtinCanhan.add(new ThongTinCaNhan());

            howTo.add(new HuongDanPanelQTV());

            // Thêm các nút menu con (ví dụ)
            JButton menuItem1 = new JButton("Trang chủ");
            JButton menuItem2 = new JButton("Lịch học");
            JButton menuItem3 = new JButton("Đồng hồ");
            JButton menuItem4 = new JButton("Tài khoản cá nhân");
            JButton menuItem5 = new JButton("Thông báo");
            JButton menuItem6 = new JButton("Feedback");

            // Tùy biến style, màu sắc, v.v. (nếu muốn)
            menuItem1.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem2.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem3.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem4.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem5.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem6.setAlignmentX(Component.CENTER_ALIGNMENT);
            int buttonHeight = 40; // Đặt chiều cao cố định cho các nút menu

            JButton[] menuItems = {menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6};

            for (JButton menuItem : menuItems) {
                menuItem.setAlignmentX(Component.CENTER_ALIGNMENT);
                menuItem.setPreferredSize(new Dimension(200, buttonHeight)); // Đặt kích thước mặc định
                menuItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonHeight)); // Kéo dài hết bề ngang
                menuItem.setFocusPainted(false); // Bỏ viền khi focus
                menuItem.setBorderPainted(false); // Bỏ viền của button
                menuItem.setBackground(new Color(3, 39, 87)); // Màu nền mặc định
                menuItem.setForeground(Color.WHITE); // Màu chữ
                menuItem.setHorizontalAlignment(SwingConstants.LEFT);

                // Thêm hiệu ứng hover
                menuItem.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        menuItem.setBackground(Color.WHITE); // Màu xanh biển khi hover
                        menuItem.setForeground(Color.BLACK); // Màu chữ
                        menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        menuItem.setBackground(new Color(3, 39, 87)); // Quay về màu gốc khi rời chuột
                        menuItem.setForeground(Color.WHITE); // Màu chữ
                    }
                });
            }

            menuItem1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    danhSachBaiViet = dao.getAllBaiViet();
                    articleListPanel.removeAll();
                    for (BaiViet baiViet : danhSachBaiViet) {
                        articleListPanel.add(createArticlePanel(baiViet));
                    }
                    cardLayout.show(mainContentPanel, "trangChu");
                    System.out.println("hello trangchu");
                }
            });

            menuItem2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "lichHocvaNhiemvu");
                    System.out.println("hello QuanLyTaiKhoan");
                }
            });

            menuItem3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "DongHoGD");
                    System.out.println("hello DongHoGD");
                }
            });

            menuItem4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ThongtinCanhan.removeAll();
                    ThongtinCanhan.add(new ThongTinCaNhan());
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "ThongtinCanhan");
                    System.out.println("hello ThongtinCanhan");
                }
            });

            menuItem5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "Thongbao");
                    System.out.println("hello Thongbao");
                }
            });

            menuItem6.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "FeedbackPHHV");
                    System.out.println("hello FeedbackPHHV");
                }
            });

            // Thêm vào subMenuPanel
            subMenuPanel.add(Box.createVerticalStrut(30)); // tạo khoảng trống phía trên
            subMenuPanel.add(menuItem1);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem2);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem3);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem4);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem5);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem6);
            subMenuPanel.add(Box.createVerticalGlue()); // đẩy các nút lên trên, để chừa không gian dưới

        } else if (ten.equals("phuhuynh")) {

            JPanel trangChu = new JPanel(new BorderLayout());
            JPanel FeedbackPHHV = new JPanel(new BorderLayout());
            JPanel Thongbao = new JPanel(new BorderLayout());
            JPanel ThongtinCanhanPH = new JPanel(new BorderLayout());
            JPanel ThongkeHoctap = new JPanel(new BorderLayout());
            JPanel lichHocPH = new JPanel(new BorderLayout());
            JPanel howTo = new JPanel(new BorderLayout());

            trangChu.setBackground(Color.WHITE);

            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.TOP);
            try {
                BufferedImage originalImage = ImageIO.read(new File("D:\\coachmeai\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\PIC_CMAI.png"));
                Image scaledImage = originalImage.getScaledInstance(710, 400, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                imageLabel.setIcon(scaledIcon);
            } catch (IOException e) {
                e.printStackTrace();
                // Nếu lỗi, bạn có thể set text để báo không load được ảnh
                imageLabel.setText("Logo");
            }

            trangChu.add(imageLabel, BorderLayout.NORTH);
            mainContentPanel.add(trangChu, "trangChu");
            mainContentPanel.add(FeedbackPHHV, "FeedbackPHHV");
            mainContentPanel.add(Thongbao, "Thongbao");
            mainContentPanel.add(ThongtinCanhanPH, "ThongtinCanhanPH");
            mainContentPanel.add(lichHocPH, "lichHocPH");
            mainContentPanel.add(ThongkeHoctap, "ThongkeHoctap");

            mainContentPanel.add(howTo, "howTo");

            add(leftMenuPanel, BorderLayout.WEST);
            add(mainContentPanel, BorderLayout.CENTER);

            JPanel articleListPanel = new JPanel();
            articleListPanel.setSize(710, 180);
            articleListPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15)); // Cách đều giữa các bài viết
            articleListPanel.setBackground(new Color(240, 240, 240)); // Màu nền tổng thể

            for (BaiViet baiViet : danhSachBaiViet) {
                System.out.println("có chạy vaod dây");
                articleListPanel.add(createArticlePanel(baiViet));
            }

            JScrollPane scrollPane = new JScrollPane(articleListPanel);
            scrollPane.setPreferredSize(new Dimension(710, 210));
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

            trangChu.add(scrollPane, BorderLayout.SOUTH);

            //FeedbackPHHV
            FeedbackPHHV.add(new FeedBackPHHV());
            //ThongkeHoctap
            ThongkeHoctap.add(new ThongKeHocTap());

            //ThongTinCanhan
            ThongtinCanhanPH.add(new ThongTinCaNhanPhuHuynh());

            howTo.add(new HuongDanPanelQTV());

            //LichHocPH
            LichHocGD lichGD_PH = new LichHocGD(350, 500);
            NhiemVuPhuHuynh nvp = new NhiemVuPhuHuynh(Auth.getUser().getID_Tai_Khoan());
            lichGD_PH.setNgayDuocChonListener(date -> {
                nvp.setSelectedDate(date);
                nvp.fillDanhSachNhiemVuTheoNgay(date);
            });

            Thongbao.removeAll();
            Thongbao.add(new ThongBao(Auth.getUser().getID_Tai_Khoan()), BorderLayout.CENTER);
            Thongbao.revalidate();
            Thongbao.repaint();

            lichHocPH.add(lichGD_PH, BorderLayout.WEST);
            lichHocPH.add(nvp, BorderLayout.EAST);

            // Thêm các nút menu con (ví dụ)
            JButton menuItem1 = new JButton("Trang chủ");
            JButton menuItem2 = new JButton("Lịch học");
            JButton menuItem3 = new JButton("Tài khoản cá nhân");
            JButton menuItem4 = new JButton("Thống kê học tập");
            JButton menuItem5 = new JButton("Thông báo");
            JButton menuItem6 = new JButton("Feedback");

            // Tùy biến style, màu sắc, v.v. (nếu muốn)
            menuItem1.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem2.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem3.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem4.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem5.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem6.setAlignmentX(Component.CENTER_ALIGNMENT);
            int buttonHeight = 40; // Đặt chiều cao cố định cho các nút menu

            JButton[] menuItems = {menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6};

            for (JButton menuItem : menuItems) {
                menuItem.setAlignmentX(Component.CENTER_ALIGNMENT);
                menuItem.setPreferredSize(new Dimension(200, buttonHeight)); // Đặt kích thước mặc định
                menuItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonHeight)); // Kéo dài hết bề ngang
                menuItem.setFocusPainted(false); // Bỏ viền khi focus
                menuItem.setBorderPainted(false); // Bỏ viền của button
                menuItem.setBackground(new Color(3, 39, 87)); // Màu nền mặc định
                menuItem.setForeground(Color.WHITE); // Màu chữ
                menuItem.setHorizontalAlignment(SwingConstants.LEFT);

                // Thêm hiệu ứng hover
                menuItem.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        menuItem.setBackground(Color.WHITE); // Màu xanh biển khi hover
                        menuItem.setForeground(Color.BLACK); // Màu chữ
                        menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        menuItem.setBackground(new Color(3, 39, 87)); // Quay về màu gốc khi rời chuột
                        menuItem.setForeground(Color.WHITE); // Màu chữ
                    }
                });
            }
            //chucnang

            menuItem1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    danhSachBaiViet = dao.getAllBaiViet();
                    articleListPanel.removeAll();
                    for (BaiViet baiViet : danhSachBaiViet) {
                        articleListPanel.add(createArticlePanel(baiViet));
                    }
                    cardLayout.show(mainContentPanel, "trangChu");
                    System.out.println("hello trangchu");
                }
            });

            menuItem2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "lichHocPH");
                    System.out.println("hello lichhoc phu huynh");
                }
            });

            menuItem3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ThongtinCanhanPH.removeAll();
                    ThongtinCanhanPH.add(new ThongTinCaNhanPhuHuynh());
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "ThongtinCanhanPH");
                    System.out.println("hello thong tin ca nhan");
                }
            });

            menuItem4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ThongkeHoctap.removeAll();
                    ThongkeHoctap.add(new ThongKeHocTap());
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "ThongkeHoctap");
                    System.out.println("hello Thong ke hoc tap");
                }
            });

            menuItem5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "Thongbao");
                    System.out.println("hello Thong bao");
                }
            });

            menuItem6.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "FeedbackPHHV");
                    System.out.println("hello FeedbackPHHV");
                }
            });

            // Thêm vào subMenuPanel
            subMenuPanel.add(Box.createVerticalStrut(30)); // tạo khoảng trống phía trên
            subMenuPanel.add(menuItem1);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem2);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem3);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem4);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem5);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem6);
            subMenuPanel.add(Box.createVerticalGlue()); // đẩy các nút lên trên, để chừa không gian dưới

        } else if (ten.equals("quantrivien")) {

            JPanel trangChu = new JPanel(new BorderLayout());
            JPanel FeedbackGD = new JPanel(new BorderLayout());
            JPanel QuanlyBaiviet = new JPanel(new BorderLayout());
            JPanel QuanlyTaikhoan = new JPanel(new BorderLayout());
            JPanel ThongtinCanhan = new JPanel(new BorderLayout());
            JPanel howTo = new JPanel(new BorderLayout());

            trangChu.setBackground(Color.WHITE);

            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.TOP);
            try {
                BufferedImage originalImage = ImageIO.read(new File("D:\\coachmeai\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\PIC_CMAI.png"));
                Image scaledImage = originalImage.getScaledInstance(710, 400, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                imageLabel.setIcon(scaledIcon);
            } catch (IOException e) {
                e.printStackTrace();
                // Nếu lỗi, bạn có thể set text để báo không load được ảnh
                imageLabel.setText("Logo");
            }

            trangChu.add(imageLabel, BorderLayout.NORTH);
            mainContentPanel.add(trangChu, "trangChu");
            mainContentPanel.add(FeedbackGD, "FeedbackGD");
            mainContentPanel.add(QuanlyBaiviet, "QuanlyBaiviet");
            mainContentPanel.add(QuanlyTaikhoan, "QuanlyTaikhoan");
            mainContentPanel.add(ThongtinCanhan, "ThongtinCanhan");

            mainContentPanel.add(howTo, "howTo");

            add(leftMenuPanel, BorderLayout.WEST);
            add(mainContentPanel, BorderLayout.CENTER);

            JPanel articleListPanel = new JPanel();
            articleListPanel.setSize(710, 180);
            articleListPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15)); // Cách đều giữa các bài viết
            articleListPanel.setBackground(new Color(240, 240, 240)); // Màu nền tổng thể

            for (BaiViet baiViet : danhSachBaiViet) {
                System.out.println("có chạy vaod dây");
                articleListPanel.add(createArticlePanel(baiViet));
            }
            JScrollPane scrollPane = new JScrollPane(articleListPanel);
            scrollPane.setPreferredSize(new Dimension(710, 210));
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

            trangChu.add(scrollPane, BorderLayout.SOUTH);

            //Feedback
            FeedbackGD.add(new FeedBack());

            //QuanLyBaiViet
            QuanlyBaiviet.add(new QuanLyBaiViet());

            //QuanLyTaiKhoan
            QuanlyTaikhoan.add(new QuanLyTaiKhoan());

            //ThongTinCaNhan
            ThongtinCanhan.add(new ThongTinCaNhanAdmin());

            howTo.add(new HuongDanPanelQTV());

            // Thêm các nút menu con (ví dụ)
            JButton menuItem1 = new JButton("Trang chủ");
            JButton menuItem2 = new JButton("Quản lý tài khoản");
            JButton menuItem3 = new JButton("Quản lý bài viết");
            JButton menuItem4 = new JButton("Feedback");
            JButton menuItem5 = new JButton("Tài khoản cá nhân");

            // Tùy biến style, màu sắc, v.v. (nếu muốn)
            menuItem1.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem2.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem3.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem4.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuItem5.setAlignmentX(Component.CENTER_ALIGNMENT);
            int buttonHeight = 40; // Đặt chiều cao cố định cho các nút menu

            JButton[] menuItems = {menuItem1, menuItem2, menuItem3, menuItem4, menuItem5};

            for (JButton menuItem : menuItems) {
                menuItem.setAlignmentX(Component.CENTER_ALIGNMENT);
                menuItem.setPreferredSize(new Dimension(200, buttonHeight)); // Đặt kích thước mặc định
                menuItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonHeight)); // Kéo dài hết bề ngang
                menuItem.setFocusPainted(false); // Bỏ viền khi focus
                menuItem.setBorderPainted(false); // Bỏ viền của button
                menuItem.setBackground(new Color(3, 39, 87)); // Màu nền mặc định
                menuItem.setForeground(Color.WHITE); // Màu chữ
                menuItem.setHorizontalAlignment(SwingConstants.LEFT);

                // Thêm hiệu ứng hover
                menuItem.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        menuItem.setBackground(Color.WHITE); // Màu xanh biển khi hover
                        menuItem.setForeground(Color.BLACK); // Màu chữ
                        menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        menuItem.setBackground(new Color(3, 39, 87)); // Quay về màu gốc khi rời chuột
                        menuItem.setForeground(Color.WHITE); // Màu chữ
                    }
                });
            }

            menuItem1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    danhSachBaiViet = dao.getAllBaiViet();
                    articleListPanel.removeAll();
                    for (BaiViet baiViet : danhSachBaiViet) {
                        System.out.println("hello");
                        articleListPanel.add(createArticlePanel(baiViet));
                    }
                    cardLayout.show(mainContentPanel, "trangChu");
                    System.out.println("hello trangchu");
                }
            });

            menuItem2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "QuanlyTaikhoan");
                    System.out.println("hello QuanLyTaiKhoan");
                }
            });

            menuItem3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "QuanlyBaiviet");
                    System.out.println("hello QuanlyBaiviet");
                }
            });

            menuItem4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "FeedbackGD");
                    System.out.println("hello FeedbackGD");
                }
            });

            menuItem5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
                    cardLayout.show(mainContentPanel, "ThongtinCanhan");
                    System.out.println("hello ThongtinCanhan");
                }
            });

            subMenuPanel.add(Box.createVerticalStrut(30)); // tạo khoảng trống phía trên
            subMenuPanel.add(menuItem1);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem2);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem3);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem4);
            subMenuPanel.add(Box.createVerticalStrut(15));
            subMenuPanel.add(menuItem5);
            subMenuPanel.add(Box.createVerticalGlue()); // đẩy các nút lên trên, để chừa không gian dưới

        }
        // Thêm subMenuPanel vào chính giữa của leftMenuPanel
        leftMenuPanel.add(subMenuPanel, BorderLayout.CENTER);

        // ========== PHẦN NÚT MỞ CHAT Ở CUỐI ==========
        chatButton = new RoundButton("D:\\coachmeai\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\COACH ME2.png", 60); // Viền ngoài 80px, hình tròn bên trong 60px
        chatButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                chatButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Auth.getUser().getRole().equals("Học viên")) {
                    ChatTool t = new ChatTool();
                }else {
                    howTopage();
                }
            }
        });

        // Tạo panel chứa nút chat ở dưới cùng
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10)); // Căn giữa
        bottomPanel.setBackground(new Color(3, 39, 87));
        bottomPanel.add(chatButton);

        leftMenuPanel.add(bottomPanel, BorderLayout.SOUTH);

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
                updateCalendar();
            });

            daysPanel.add(dayButton);
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private JPanel createArticlePanel(BaiViet baiViet) {
        System.out.println("bài viết đây bài viết đây");
        RoundedPanel panel = new RoundedPanel(30); // Bo góc 30px
        panel.setPreferredSize(new Dimension(210, 150));
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Hình ảnh
        JLabel imageLabels = new JLabel();
        imageLabels.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabels.setVerticalAlignment(SwingConstants.TOP);
        try {
            ImageIcon imageIcon = new ImageIcon(baiViet.getHinhAnh());
            Image originalImage = imageIcon.getImage(); // Lấy Image từ ImageIcon
            Image scaledImage = originalImage.getScaledInstance(190, 80, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            imageLabels.setIcon(scaledIcon);
            ;
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu lỗi, bạn có thể set text để báo không load được ảnh
            imageLabels.setText("Không có ảnh");
        }
        // Tiêu đề bài viết
        JLabel titleLabel = new JLabel(baiViet.getTieuDe());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Nút "Xem thêm"
        JButton readMoreButton = new JButton("Xem thêm");
        readMoreButton.setFocusPainted(false);
        readMoreButton.setBackground(Color.BLUE);
        readMoreButton.setForeground(Color.WHITE);
        readMoreButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        readMoreButton.addActionListener(e -> {
            // Hiển thị bài viết chi tiết
            new BaiVietGD(baiViet.getTieuDe(), baiViet.getNoiDung(), baiViet.getHinhAnh());
        });

        // Thêm các thành phần vào panel
        panel.add(imageLabels, BorderLayout.NORTH);
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(readMoreButton, BorderLayout.SOUTH);

        return panel;
    }

    public static void addPanel(JPanel panell, String panelName) {
        if (mainContentPanel != null) {
            mainContentPanel.add(panell, panelName);
            System.out.println("Đã thêm panel: " + panelName);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
            CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
            cardLayout.show(mainContentPanel, panelName);
        } else {
            System.out.println("Lỗi: mainContentPanel chưa được khởi tạo!");
        }
    }

    public static void backing() {
        if (mainContentPanel != null) {
            CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
            cardLayout.show(mainContentPanel, "trangChu");
        } else {
            System.out.println("Lỗi: mainContentPanel chưa được khởi tạo!");
        }
    }

    public static void howTopage() {
        if (mainContentPanel != null) {
            CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
            cardLayout.show(mainContentPanel, "howTo");
        } else {
            System.out.println("Lỗi: mainContentPanel chưa được khởi tạo!");
        }
    }

    public static void watchLichhoc() {
        if (mainContentPanel != null) {
            CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
            cardLayout.show(mainContentPanel, "lichHocvaNhiemvu");
        } else {
            System.out.println("Lỗi: mainContentPanel chưa được khởi tạo!");
        }
    }

    public static void watchDongho() {
        if (mainContentPanel != null) {
            CardLayout cardLayout = (CardLayout) mainContentPanel.getLayout();
            cardLayout.show(mainContentPanel, "DongHoGD");
        } else {
            System.out.println("Lỗi: mainContentPanel chưa được khởi tạo!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                trangchu frame = new trangchu("hocvien");
                frame.setVisible(true);
            }
        });
    }
}
