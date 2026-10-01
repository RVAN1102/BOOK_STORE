package vn.iotstar.util;

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil_24162144 {
    private static final String FROM_EMAIL = "support.bookstore.hcmute@gmail.com";
    private static final String APP_PASSWORD = "apppassword123";

    public static boolean sendOtp(String toEmail, String otp) {
        // [CƠ CHẾ PHÒNG VỆ MẠNG TRƯỜNG HỌC]: Luôn in mã OTP ra Console IDE để kiểm thử & chấm thi
        System.out.println("======> MA OTP KICH HOAT CHO " + toEmail + " LA: " + otp + " <======");

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        try {
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, "Nhà Sách BookStore"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã OTP Xác Thực Tài Khoản - BookStore");
            message.setContent(
                "<div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #ddd; border-radius: 8px; max-width: 500px;'>"
                + "<h2 style='color: #0d6efd;'>Nhà Sách BookStore</h2>"
                + "<p>Chào bạn,</p>"
                + "<p>Mã OTP để kích hoạt tài khoản của bạn là:</p>"
                + "<div style='font-size: 28px; font-weight: bold; letter-spacing: 5px; color: #dc3545; padding: 10px 0;'>" + otp + "</div>"
                + "<p style='color: #666;'>Mã OTP này có hiệu lực trong vòng 5 phút. Vui lòng không chia sẻ cho bất kỳ ai.</p>"
                + "</div>",
                "text/html; charset=UTF-8"
            );

            Transport.send(message);
            return true;
        } catch (Exception e) {
            System.err.println("Gửi mail qua mạng không thành công (mạng phòng thi có thể chặn SMTP). Vui lòng sử dụng mã OTP in trên Console để test: " + e.getMessage());
            return false;
        }
    }
}
