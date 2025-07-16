/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coachmeai;

import com.mycompany.entity.TaiKhoan;
import com.mycompany.utils.Auth;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HuongDanPanelQTV extends JPanel {

    private JPanel contentPanel;
    private JPanel noiDungPanel; // Panel chứa nội dung cuộn
    TaiKhoan user = Auth.getUser(); // Lấy tài khoản đã đăng nhập
    String vaiTro = user.getRole(); // ví dụ: "admin", "hocvien", "phuhuynh"

    public HuongDanPanelQTV() {
        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(710, 650));
        initUI();
    }

    private void initUI() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(224, 240, 255));
        contentPanel.setBounds(30, 30, 610, 550);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(224, 240, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnBack = new JButton("←");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trangchu.backing();
            }
        });
        btnBack.setFont(new Font("Arial", Font.PLAIN, 30));
        btnBack.setBackground(new Color(224, 240, 255));
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setOpaque(true);

        JLabel titleLabel = new JLabel("HƯỚNG DẪN SỬ DỤNG            ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);

        topPanel.add(btnBack, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        contentPanel.add(topPanel, BorderLayout.NORTH);

        noiDungPanel = new JPanel();
        noiDungPanel.setLayout(new BoxLayout(noiDungPanel, BoxLayout.Y_AXIS));
        noiDungPanel.setBackground(new Color(224, 240, 255));

        JScrollPane scrollPane = new JScrollPane(noiDungPanel);
        scrollPane.setPreferredSize(new Dimension(620, 450));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(9, Integer.MAX_VALUE));

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(contentPanel);

        addHuongDanContent();
    }

    private void addHuongDanContent() {
        String[] huongDan;

        if ("Quản trị viên".equals(vaiTro)) {
            huongDan = new String[]{
                "#Đối với Quản trị viên",
                "Quản trị viên là người có quyền cao nhất trong hệ thống, chịu trách nhiệm quản lý người dùng",
                "khóa học, và đảm bảo hệ thống hoạt động ổn định.",
                "",
                "1. Đăng nhập",
                "Sử dụng tài khoản quản trị viên để truy cập vào hệ thống.",
                "Nhập đúng tên đăng nhập và mật khẩu được cấp.",
                "Sau khi đăng nhập, giao diện quản trị sẽ hiển thị với đầy đủ các chức năng quản lý.",
                "",
                "2. Quản lý người dùng",
                "Vào mục 'Tài khoản' để thực hiện các thao tác:",
                "- Thêm mới người dùng",
                "- Chỉnh sửa thông tin người dùng",
                "- Khóa hoặc mở khóa tài khoản",
                "- Xóa tài khoản không còn sử dụng",
                "",
                "3. Quản lý khóa học",
                "Truy cập mục 'Môn học' để:",
                "- Thêm mới khóa học",
                "- Sửa thông tin khóa học",
                "- Xóa các khóa học không còn hiệu lực",
                "",
                "4. Quản lý thông báo",
                "Gửi thông báo đến tất cả người dùng hệ thống qua mục 'Thông báo'.",
                "",
                "5. Xem phản hồi",
                "Kiểm tra và phản hồi các góp ý của người dùng thông qua mục 'Feedback'."
            };
        } else if ("Phụ huynh".equalsIgnoreCase(vaiTro)) {
            huongDan = new String[]{
                "#Đối với Phụ huynh",
                "Phụ huynh có vai trò hỗ trợ, giám sát và đồng hành cùng học sinh trong quá trình học tập qua hệ thống.",
                "",
                "1. Đăng nhập",
                "Các thông tin cần nhập bao gồm tên đăng nhập (username) và mật khẩu.",
                "Sau khi đăng nhập, giao diện dành riêng cho phụ huynh sẽ hiển thị.",
                "",
                "2. Theo dõi quá trình học tập của học sinh",
                "Phụ huynh có thể xem thông tin chi tiết về các môn học con đang tham gia:",
                "- Tên môn học, giáo viên phụ trách.",
                "- Danh sách các bài kiểm tra, nhiệm vụ đã giao.",
                "- Kết quả điểm số của từng bài làm, trạng thái hoàn thành.",
                "- Ngày nộp bài, đánh giá và nhận xét của giáo viên (nếu có).",
                "",
                "3. Gửi phản hồi cho giáo viên",
                "Phụ huynh có thể truy cập mục 'Feedback' để gửi phản hồi hoặc câu hỏi đến giáo viên:",
                "- Đặt câu hỏi về nội dung học tập.",
                "- Góp ý phương pháp giảng dạy.",
                "- Thảo luận về tiến độ học tập hoặc các vấn đề gặp phải.",
                "Mỗi phản hồi đều có thể đính kèm nội dung mô tả cụ thể để giáo viên dễ tiếp nhận.",
                "",
                "4. Nhận và xem thông báo",
                "Tại mục 'Thông báo', phụ huynh sẽ nhận được:",
                "- Thông báo từ nhà trường về lịch học, sự kiện, kỳ thi,...",
                "- Thông báo riêng từ giáo viên liên quan đến tình hình học tập của học sinh.",
                "- Cập nhật về các thay đổi trong môn học hoặc nội quy.",
                "",
                "5. Hỗ trợ học sinh tại nhà",
                "Dựa trên thông tin từ hệ thống, phụ huynh có thể:",
                "- Nhắc nhở học sinh hoàn thành bài tập, nhiệm vụ đúng hạn.",
                "- Thảo luận cùng con để nắm rõ nội dung học.",
                "- Động viên và phối hợp với giáo viên khi cần thiết.",
            };
        } else if ("Học viên".equalsIgnoreCase(vaiTro)) {
            huongDan = new String[]{
                "#Đối với Học viên",
                "Học viên là người trực tiếp tham gia vào các khóa học, làm bài kiểm tra, nhận nhiệm vụ và phản hồi với giáo viên/phụ huynh. Các chức năng chính:",
                "                    ",
                "1. Đăng nhập",
                "Truy cập vào trang chủ hệ thống.",
                "Nhập tên đăng nhập và mật khẩu đã được cấp.",
                "Nhấn \"Đăng nhập\" để vào hệ thống.",
                "                         ",
                "2. Xem khóa học và môn học",
                "Sau khi đăng nhập, học sinh sẽ thấy danh sách các môn học mà mình đang tham gia.",
                "Nhấn vào tên môn để xem chi tiết nội dung, bài tập, bài test liên quan.",
                "                          ",
                "3. Làm bài kiểm tra",
                "Truy cập mục \"Bài kiểm tra\" từ giao diện môn học.",
                "Chọn bài kiểm tra cần làm.",
                "Trả lời từng câu hỏi, sau đó nhấn \"Nộp bài\".",
                "Hệ thống sẽ lưu lại kết quả, có thể chấm điểm tự động hoặc chờ giáo viên đánh giá.",
                "                     ",
                "4. Nhận nhiệm vụ học tập",
                "Trong từng môn học, học viên có thể nhận nhiệm vụ (bài tập, yêu cầu học tập).",
                "Mỗi nhiệm vụ sẽ có mô tả, thời hạn và trạng thái hoàn thành.",
                "                       ",
                "5. Gửi phản hồi",
                "Vào mục Feedback, học viên có thể gửi:",
                "- Câu hỏi cho giáo viên.",
                "- Phản ánh, góp ý cho quản trị viên/phụ huynh.",
                "Có thể đính kèm nội dung mô tả và chờ phản hồi.",
                "                          ",
                "6. Xem thông báo",
                "Trong mục Thông báo, học viên xem được:",
                "- Thông báo chung từ nhà trường/quản trị viên.",
                "- Thông báo cá nhân từ giáo viên hoặc phụ huynh."
            };
        } else {
            huongDan = new String[]{"Không tìm thấy hướng dẫn phù hợp với vai trò."};
        }

        for (String line : huongDan) {
            JLabel label = new JLabel(line);
            if (line.startsWith("#")) {
                label.setFont(new Font("Arial", Font.BOLD, 20));
                label.setForeground(new Color(0, 51, 102));
            } else if (line.matches("^\\d+\\..*")) {
                label.setFont(new Font("Arial", Font.BOLD, 16));
            } else {
                label.setFont(new Font("Arial", Font.PLAIN, 14));
            }
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            noiDungPanel.add(label);
            noiDungPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hướng dẫn");
            frame.setContentPane(new HuongDanPanelQTV());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

