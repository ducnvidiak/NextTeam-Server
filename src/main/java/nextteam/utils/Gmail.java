/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import nextteam.Global;

/**
 *
 * @author vnitd
 */
public class Gmail {

    private final String smtpHost = "smtp.gmail.com";
    private final String smtpPort = "587";
    private final String username = "nextteam.fpt@gmail.com";
    private final String password = "haoaqlqowjwhxjqf";

    private final String fromEmail = "nextteam.fpt@gmail.com";
    private final String toEmail;
    private String contentType;
    private String subject;
    private String content;

    private Map<String, String> macrosMap;

    public Gmail(String toEmail) {
        this.toEmail = toEmail;

    }

    public Gmail setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Gmail setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Gmail initContent(String content) {
        this.content = content;
        return this;
    }

    public Gmail appendContent(String content) {
        this.content += content;
        return this;
    }

    public Gmail initMacro() {
        macrosMap = new HashMap<>();
        return this;
    }

    public Gmail appendMacro(String macro, String value) {
        macrosMap.put(macro, value);
        return this;
    }

    public void send() {
        if (contentType == null) {
            contentType = "text/plain";
        }
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            message.setSubject(subject);
            message.setContent(content, contentType);

            Transport.send(message);
        } catch (MessagingException ex) {
            System.out.println(ex);
        }
    }

    private String standardize(String str) {
        for (String macro : macrosMap.keySet()) {
            str = str.replaceAll("\\[" + macro + "\\]", macrosMap.get(macro));
        }
        return str;
    }

    public void sendTemplate(String filePath) {
        if (content != null || macrosMap == null) {
            return;
        } else {
            filePath = Global.workingPath.concat(filePath);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                String line;
                Gmail g = this.initContent("");
                while ((line = br.readLine()) != null) {
                    g = g.appendContent(standardize(line)).appendContent("\n");
                }
                g.send();
            } catch (IOException ex) {
                Logger.getLogger(Gmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void sendTemplate(URL filePath) {
        if (content != null || macrosMap == null) {
            return;
        } else {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(filePath.openStream(), StandardCharsets.UTF_8));
                String line;
                Gmail g = this.initContent("");
                while ((line = br.readLine()) != null) {
                    g = g.appendContent(standardize(line)).appendContent("\n");
                }
//                System.out.println(content);
                g.send();
            } catch (IOException ex) {
                Logger.getLogger(Gmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
//        try {
//            new Gmail("tranvietdangquang@gmail.com")
//                    .setContentType("text/html; charset=UTF-8")
//                    .setSubject("Đây là Tiếng Việt!")
//                    .initMacro()
//                    .appendMacro("USERNAME", "quangtvd")
//                    .appendMacro("ID", "DE170014")
//                    .appendMacro("WHEN", new SimpleDateFormat("HH:mm:ss 'ngày' dd 'tháng' MM 'năm' yyyy").format(new Date()))
//                    .appendMacro("CODE", "123456")
//                    .sendTemplate(new URL("http://127.0.0.1:8080/gmail_code.jsp"));
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(Gmail.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("Đây là Tiếng Việt");

    }
}
