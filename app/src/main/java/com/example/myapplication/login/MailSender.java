package com.example.myapplication.login;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    private static String username;
    private static String password;

    static {
        // 환경 변수에서 이메일과 비밀번호 불러오기
        username = System.getenv("EMAIL_USERNAME");
        password = System.getenv("EMAIL_PASSWORD");

        // 환경 변수에 값이 없는 경우 config.properties 파일에서 불러오기
        if (username == null || password == null) {
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream("config.properties")) {
                props.load(fis);
                username = props.getProperty("EMAIL_USERNAME");
                password = props.getProperty("EMAIL_PASSWORD");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 이메일 또는 비밀번호가 설정되지 않은 경우 오류 메시지 출력
        if (username == null || password == null) {
            System.err.println("이메일 전송을 위한 사용자 이름과 비밀번호가 설정되지 않았습니다.");
        }
    }

    public static boolean sendEmail(String to, String subject, String messageBody) {
        if (username == null || password == null) {
            System.err.println("이메일 전송에 필요한 인증 정보가 없습니다.");
            return false;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
