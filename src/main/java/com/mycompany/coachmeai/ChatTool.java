package com.mycompany.coachmeai;

import com.mycompany.DAO.DatabaseManager;
import com.mycompany.entity.TaiKhoan;
import com.mycompany.utils.Auth;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.sql.Timestamp;
import javax.swing.text.StyledDocument;
import org.json.JSONArray;

public class ChatTool extends JFrame {

    private JTextPane chatArea;
    private JTextField inputField;
    private JButton sendButton;
    DatabaseManager dbManager = new DatabaseManager();
    TaiKhoan user = Auth.getUser();

    String UserAvatar = user.getAvatar();
    String AIAvatar = "C:\\Users\\Do Tuong Minh\\Desktop\\hihi\\CoachMeAI\\src\\main\\java\\com\\mycompany\\Image\\logo_chatbot.png";
    String regex = ".*schedule (?:a )?(class|lesson|session) (.+) from (\\d{1,2} ?[APap][Mm]) to (\\d{1,2} ?[APap][Mm])(?: on (\\d{1,2}/\\d{1,2}/\\d{4}))? with content ['\"]([^'\"]+)['\"](?: and note ['\"]([^'\"]+)['\"])?";

    public ChatTool() {
        setTitle("Chat Tool");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 💬 Khu vực chat
        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setContentType("text/html");
        chatArea.setBackground(new Color(40, 44, 52)); // Màu nền tối
        chatArea.setForeground(Color.BLUE);
        chatArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Tạo khoảng cách đẹp hơn
        add(scrollPane, BorderLayout.CENTER);

        // ✏️ Khu vực nhập liệu
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(30, 33, 40)); // Màu nền tối hơn

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputField.setBackground(new Color(50, 54, 60));
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // Bo góc đẹp hơn

        // 🔘 Nút gửi
        sendButton = new JButton("➤");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        // Hiệu ứng hover cho nút gửi
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(new Color(100, 149, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(new Color(70, 130, 180));
            }
        });

        sendButton.addActionListener(e -> {
            String userMessage = inputField.getText();
            if (!userMessage.trim().isEmpty()) {
                String userHtml = "<div style='text-align: left;'>"
                        + "<img src='" + UserAvatar + "' width='30' height='30' style='vertical-align: middle; margin-right: 5px;'> "
                        + userMessage
                        + "</div><br>";
                try {
                    StyledDocument doc = chatArea.getStyledDocument();
                    doc.insertString(doc.getLength(), userMessage + "\n", null); // Thêm tin nhắn thuần túy (có thể tùy chỉnh)
                    // Nếu bạn muốn sử dụng HTML phức tạp hơn, bạn có thể thử cách sau (cần xử lý ngoại lệ):
                    // chatArea.getDocument().insertString(chatArea.getDocument().getLength(), userHtml, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                inputField.setText(""); // Xóa nội dung trường nhập liệu sau khi gửi
            }
        });

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessageToAI());
        inputField.addActionListener(e -> sendMessageToAI());

        // 🖼️ Lời chào đầu tiên của AI với avatar
        appendMessage("", "Xin chào !👋🏿 Hôm nay tôi có thể giúp gì nào? 😀", Color.LIGHT_GRAY, AIAvatar);

        setVisible(true);
    }

    private void sendMessageToAI() {
        String userMessage = inputField.getText().trim();
        if (userMessage.isEmpty()) {
            return;
        }

        appendMessage("", userMessage, Color.GREEN, UserAvatar);
        inputField.setText("");

        new Thread(() -> {
            try {
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(userMessage);
                if (matcher.matches()) {
                    System.out.println("rieu rieu rieu");
                    ScheduleResponse scheduleResponse = processScheduleRequest(userMessage);

                    System.out.println("AI trả về (schedule): " + scheduleResponse.getMessage());

                    if (scheduleResponse.getJsonObject() != null) {
                        JSONObject taskData = scheduleResponse.getJsonObject();
                        SwingUtilities.invokeLater(() -> showConfirmationDialog(taskData));
                    }
                    if (scheduleResponse.getMessage() != null && !scheduleResponse.getMessage().trim().isEmpty()) {
                        SwingUtilities.invokeLater(() -> {
                            appendMessage("", scheduleResponse.getMessage(), Color.BLUE, AIAvatar);
                            chatArea.revalidate();
                            chatArea.repaint();
                        });
                    }
                } else {
                    System.out.println("xì ke");
                    String normalResponse = processNormalChat(userMessage);
                    System.out.println("AI trả về (chat thường): " + normalResponse);
                    SwingUtilities.invokeLater(() -> {
                        appendMessage("", normalResponse, Color.BLUE, AIAvatar);
                        chatArea.revalidate();
                        chatArea.repaint();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi:\n" + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    private String processNormalChat(String userMessage) {
        // Không cần regex hay parsing phức tạp nữa
        // Gọi trực tiếp đến AI với tin nhắn của người dùng
        String prompt = userMessage;
        return deepSeekApiCall(prompt, 0); // Gọi API và trả về phản hồi
    }

    private ScheduleResponse processScheduleRequest(String prompt) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(prompt);

        if (matcher.matches()) {
            try {
                String subjectName = matcher.group(2).trim();
                if (subjectName.equalsIgnoreCase("toán") || subjectName.equalsIgnoreCase("văn") || subjectName.equalsIgnoreCase("anh")) {
                    // Môn học hợp lệ
                } else {
                    subjectName = "Khác";
                }

                String startTimeRaw = matcher.group(3).trim();
                String endTimeRaw = matcher.group(4).trim();
                String dateRaw = matcher.group(5);
                String content = matcher.group(6).trim();
                String note = matcher.group(7) != null ? matcher.group(7).trim() : "No notes";

                LocalDate date = (dateRaw != null)
                        ? LocalDate.parse(dateRaw, DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH))
                        : LocalDate.now();

                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h a", Locale.ENGLISH);
                LocalTime startTime = LocalTime.parse(startTimeRaw.toUpperCase(), timeFormatter);
                LocalTime endTime = LocalTime.parse(endTimeRaw.toUpperCase(), timeFormatter);

                if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
                    return new ScheduleResponse(null, "❌ Thời gian bắt đầu phải trước thời gian kết thúc.");
                }

                LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
                LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

                DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String startDateTimeSQL = startDateTime.format(sqlFormatter);
                String endDateTimeSQL = endDateTime.format(sqlFormatter);

                JSONObject scheduleJson = new JSONObject();
                scheduleJson.put("title", "Class Schedule: " + subjectName);
                scheduleJson.put("subject_name", subjectName);
                scheduleJson.put("content", content);
                scheduleJson.put("note", note);
                scheduleJson.put("start_time", startDateTimeSQL);
                scheduleJson.put("end_time", endDateTimeSQL);

                String confirmationMessage = "✅ Đã nhận yêu cầu lên lịch học " + subjectName
                        + " từ " + startTime.format(timeFormatter)
                        + " đến " + endTime.format(timeFormatter)
                        + " vào ngày " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + " với nội dung: '" + content + "' và ghi chú: '" + note + "'";

                return new ScheduleResponse(scheduleJson, confirmationMessage);
            } catch (java.time.format.DateTimeParseException e) {
                return new ScheduleResponse(null, "❌ Định dạng thời gian hoặc ngày không hợp lệ. Vui lòng kiểm tra lại.");
            } catch (Exception e) {
                e.printStackTrace();
                return new ScheduleResponse(null, "❌ Đã xảy ra lỗi khi xử lý yêu cầu lên lịch.");
            }
        } else {
            return new ScheduleResponse(null, "❌ Không thể nhận diện yêu cầu. Vui lòng sử dụng định dạng hợp lệ: schedule a class <môn học> from <giờ bắt đầu> to <giờ kết thúc> [on <ngày>] with content '<nội dung>' [and note '<ghi chú>']");
        }
    }

    class ScheduleResponse {

        JSONObject jsonObject;
        String message;

        public ScheduleResponse(JSONObject jsonObject, String message) {
            this.jsonObject = jsonObject;
            this.message = message;
        }

        public JSONObject getJsonObject() {
            return jsonObject;
        }

        public String getMessage() {
            return message;
        }
    }

    private void showConfirmationDialog(JSONObject taskData) {
        JDialog dialog = new JDialog(this, "Xác nhận thông tin", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(7, 2));

        JLabel lblTitle = new JLabel("Tiêu đề:");
        JTextField txtTitle = new JTextField(taskData.getString("title"));

        JLabel lblSubject = new JLabel("Môn học:");
        JTextField txtSubject = new JTextField(taskData.getString("subject_name"));
        txtSubject.setEditable(false);

        JLabel lblContent = new JLabel("Nội dung:");
        JTextField txtContent = new JTextField(taskData.getString("content"));

        JLabel lblNote = new JLabel("Ghi chú:");
        JTextField txtNote = new JTextField(taskData.optString("note", ""));

        JLabel lblStartTime = new JLabel("Bắt đầu:");
        JTextField txtStartTime = new JTextField(taskData.getString("start_time"));

        JLabel lblEndTime = new JLabel("Kết thúc:");
        JTextField txtEndTime = new JTextField(taskData.getString("end_time"));

        JButton btnConfirm = new JButton("Xác nhận");
        JButton btnCancel = new JButton("Hủy");

        dialog.add(lblTitle);
        dialog.add(txtTitle);
        dialog.add(lblSubject);
        dialog.add(txtSubject);
        dialog.add(lblContent);
        dialog.add(txtContent);
        dialog.add(lblNote);
        dialog.add(txtNote);
        dialog.add(lblStartTime);
        dialog.add(txtStartTime);
        dialog.add(lblEndTime);
        dialog.add(txtEndTime);
        dialog.add(btnConfirm);
        dialog.add(btnCancel);

        btnConfirm.addActionListener(e -> {
            try {
                // Chuyển đổi start time và end time từ String sang LocalDateTime
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  // Định dạng thời gian
                LocalDateTime startDateTime = LocalDateTime.parse(txtStartTime.getText(), formatter);
                LocalDateTime endDateTime = LocalDateTime.parse(txtEndTime.getText(), formatter);

                // Kiểm tra thời gian bắt đầu và kết thúc
                if (!isValidDateTime(txtStartTime.getText()) || !isValidDateTime(txtEndTime.getText())) {
                    JOptionPane.showMessageDialog(this, "Định dạng thời gian không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;  // Nếu định dạng không hợp lệ, dừng lại
                }

                // Kiểm tra xem thời gian bắt đầu có phải là quá khứ không
                if (!isFutureDateTime(startDateTime)) {
                    JOptionPane.showMessageDialog(this, "Không thể thêm nhiệm vụ trong quá khứ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Chuyển LocalDateTime thành Timestamp
                Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
                Timestamp endTimestamp = Timestamp.valueOf(endDateTime);

                if (startDateTime.isAfter(endDateTime)) {
                    JOptionPane.showMessageDialog(this, "Thời gian bắt đầu phải sớm hơn thời gian kết thúc", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
                    JOptionPane.showMessageDialog(this, "Nhiệm vụ chỉ được kéo dài trong 1 ngày", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Gọi phương thức addTask với các tham số cần thiết
                dbManager.addTask(
                        txtTitle.getText(),
                        txtSubject.getText(),
                        txtContent.getText(),
                        txtNote.getText(),
                        startTimestamp,
                        endTimestamp
                );

                // Hiển thị thông báo thành công
                JOptionPane.showMessageDialog(this, "Lưu thành công!");
                dialog.dispose();  // Đóng dialog sau khi lưu
                askForTestCreation(taskData);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public void appendMessage(String sender, String message, Color color, String avatarUrl) {
        StyledDocument doc = chatArea.getStyledDocument();

        // Tạo panel chứa avatar + tin nhắn
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        messagePanel.setOpaque(false);

        try {
            System.out.println("Avatar URL: " + avatarUrl);

            // Load ảnh avatar
            ImageIcon avatar = new ImageIcon(avatarUrl);
            if (avatar.getIconWidth() == -1) {
                System.out.println("⚠️ Lỗi: Không thể tải ảnh từ " + avatarUrl);
                return;
            }

            // Resize avatar
            Image image = avatar.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon resizedAvatar = new ImageIcon(image);
            JLabel avatarLabel = new JLabel(resizedAvatar);

            // Tạo label cho tin nhắn
            JLabel textLabel = new JLabel("<html><div style='width: 250px; color: " + toHex(color) + ";'>" + sender
                    + " " + message + "</div></html>");
            textLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            // Thêm avatar và tin nhắn vào panel
            messagePanel.add(avatarLabel);
            messagePanel.add(textLabel);

            // Thêm vào JTextPane
            chatArea.setCaretPosition(doc.getLength());
            chatArea.insertComponent(messagePanel);
            doc.insertString(doc.getLength(), "\n", null);

            // Refresh lại giao diện
            chatArea.revalidate();
            chatArea.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public boolean isValidDateTime(String dateTime) {
        String regex = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateTime);
        return matcher.matches();
    }

// Hàm kiểm tra thời gian có phải là trong tương lai hay không
    public boolean isFutureDateTime(LocalDateTime dateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return dateTime.isAfter(currentDateTime);
    }

    private void askForTestCreation(JSONObject taskData) {
        int response = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn tạo bài test trắc nghiệm cho môn " + taskData.getString("subject_name") + " không?",
                "Tạo bài test", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            // Nhận số lượng câu hỏi mong muốn từ người dùng (có thể cho phép nhập hoặc chọn)
            String numberOfQuestionsStr = JOptionPane.showInputDialog(this, "Nhập số câu hỏi (tối đa 15):");
            int numberOfQuestions = 5; // Mặc định là 5 câu nếu không có nhập

            try {
                int parsedNumberOfQuestions = Integer.parseInt(numberOfQuestionsStr);
                if (parsedNumberOfQuestions > 15) {
                    numberOfQuestions = 15;
                    JOptionPane.showMessageDialog(this, "Số câu hỏi đã được điều chỉnh xuống 15.");
                } else if (parsedNumberOfQuestions > 0) {
                    numberOfQuestions = parsedNumberOfQuestions;
                } else {
                    JOptionPane.showMessageDialog(this, "Số câu hỏi không hợp lệ, sử dụng mặc định 5 câu.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số câu hỏi không hợp lệ, sử dụng mặc định 5 câu.");
            }

            final int finalNumberOfQuestions = numberOfQuestions;
            final String subject = taskData.getString("subject_name");

            // Thêm tin nhắn xác nhận vào chatArea (hiển thị ngay lập tức)
            String confirmationMessage = "✅ Đã nhận yêu cầu tạo " + finalNumberOfQuestions + " câu hỏi.";
            SwingUtilities.invokeLater(() -> appendMessage("", confirmationMessage, Color.BLUE, AIAvatar));

            // Tạo và chạy một luồng mới để gọi generateTestContent
            new Thread(() -> generateTestContent(subject, finalNumberOfQuestions)).start();

        } else {
            showConfirmationDialog(taskData);
        }
    }

    public void generateTestContent(String subject, int numberOfQuestions) {
        String prompt = String.format(
                "Please generate %d multiple-choice questions for the subject '%s'. Each question should have exactly one correct answer and three incorrect answers.\n"
                + "\n"
                + "Format each question as follows:\n"
                + "Question X: \n[Question Text]\n"
                + "A) [Option A]\n"
                + "B) [Option B]\n"
                + "C) [Option C]\n"
                + "D) [Option D]\n"
                + "Correct Answer: [A, B, C, or D]\n"
                + "\n"
                + "Do not provide any explanations or extra text. Only output the questions in the required format. Ensure that the number of questions is %d",
                numberOfQuestions, subject, numberOfQuestions
        );

        String response = deepSeekApiCall(prompt, numberOfQuestions);

        // In phản hồi để debug
        System.out.println("Phản hồi từ API:\n" + response);

        int questionsCreated = 0; // Biến để theo dõi số lượng câu hỏi đã tạo thành công

        try {
            // Dùng regex để chia các câu hỏi thành từng khối
            String[] questions = response.split("\\n\\s*\\n"); // Tách bằng một hoặc nhiều dòng trống

            for (String questionBlock : questions) {
                // Xóa khoảng trắng thừa
                questionBlock = questionBlock.trim();

                // Tách từng dòng của câu hỏi
                String[] lines = questionBlock.split("\\n");
                System.out.println(lines);
                // Kiểm tra nếu khối có đủ 6 dòng (Question + 4 đáp án + Correct Answer)
                if (lines.length >= 6) {
                    String question = lines[1].trim();

                    String answerA = extractAnswer(lines[2], "A)");
                    String answerB = extractAnswer(lines[3], "B)");
                    String answerC = extractAnswer(lines[4], "C)");
                    String answerD = extractAnswer(lines[5], "D)");
                    String correctAnswer = extractAnswer(lines[6], "Correct Answer:");
                    System.out.println("ques" + question + "A." + answerA + "B." + answerB + "C. " + "D." + answerC + answerD + correctAnswer);
                    dbManager.addQuesTest(question, answerA, answerB, answerC, answerD, correctAnswer);
                    // Kiểm tra nếu bất kỳ dòng nào bị thiếu thì bỏ qua câu hỏi này
                    if (answerA == null || answerB == null || answerC == null || answerD == null || correctAnswer == null) {
                        System.out.println("⚠️ Câu hỏi không đúng định dạng, bỏ qua.");
                        continue;
                    }

                    // In ra câu hỏi và đáp án
                    System.out.println("Câu hỏi: " + question);
                    System.out.println("A. " + answerA);
                    System.out.println("B. " + answerB);
                    System.out.println("C. " + answerC);
                    System.out.println("D. " + answerD);
                    System.out.println("Đáp án đúng: " + correctAnswer);
                    System.out.println(); // In một dòng trống sau mỗi câu hỏi
                    questionsCreated++;
                } else {
                    System.out.println("⚠️ Câu hỏi không đủ dòng, bỏ qua: " + questionBlock);
                }
            }

            // Hiển thị thông báo tạo thành công sau khi xử lý tất cả các câu hỏi
            final int finalQuestionsCreated = questionsCreated;
            SwingUtilities.invokeLater(() -> {
                String successMessage = "✅ Đã tạo thành công " + finalQuestionsCreated + " câu hỏi.";
                appendMessage("", successMessage, Color.BLUE, AIAvatar);
            });

        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> {
                appendMessage("", "⚠️ Lỗi trong quá trình tạo câu hỏi hoặc quá trình tạo đã bị gián đoạn.", Color.BLUE, AIAvatar);
            });
        }
    }

    private String extractAnswer(String line, String prefix) {
        int index = line.indexOf(prefix);
        if (index != -1) {
            return line.substring(index + prefix.length()).trim();
        }
        return null; // Trả về null nếu không tìm thấy đúng định dạng
    }

    private String deepSeekApiCall(String prompt, int numberOfQuestions) {
        String model = "mistral:7b";
        String fullResponse = "Xin lỗi, tôi không thể trả lời ngay bây giờ.";

        try {
            // Địa chỉ API
            URL url = new URL("http://localhost:11434/api/generate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Tạo đối tượng JSON request
            JSONObject requestJson = new JSONObject();
            requestJson.put("model", model);
            requestJson.put("prompt", prompt);
            requestJson.put("stream", false);
            requestJson.put("num_questions", numberOfQuestions);  // Thêm tham số để yêu cầu số câu hỏi

            // Gửi request đến API
            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestJson.toString().getBytes());
            }

            // Nhận phản hồi từ API
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String responseLine = br.readLine();
                JSONObject responseJson = new JSONObject(responseLine);
                fullResponse = responseJson.getString("response");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullResponse;
    }

    private void saveTestToDatabase(String question, String answerA, String answerB, String answerC, String answerD, String correctAnswer) {
        // Code lưu dữ liệu vào cơ sở dữ liệu
        System.out.println("Saving question to database: " + question);
        // Giả sử bạn có một phương thức trong database manager để lưu câu hỏi
        // DatabaseManager dbManager = new DatabaseManager();
        // dbManager.addTestQuestion(question, answerA, answerB, answerC, answerD, correctAnswer);
    }

    public static void main() {
        SwingUtilities.invokeLater(ChatTool::new);
    }
}
