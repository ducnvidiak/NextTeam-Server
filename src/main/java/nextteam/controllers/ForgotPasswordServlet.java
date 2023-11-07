/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.User;
import nextteam.utils.Gmail;

@WebServlet(name = "ForgotPassword", urlPatterns = {"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {

    private void sendVerificationMail(String email, String subject, String username, String code, int id) {
        try {
            new Gmail(email)
                    .setContentType("text/html; charset=UTF-8")
                    .setSubject(subject)
                    .initMacro()
                    .appendMacro("USERNAME", username)
                    .appendMacro("ID", id + "")
                    .appendMacro("WHEN", new SimpleDateFormat("HH:mm:ss 'ngày' dd 'tháng' MM 'năm' yyyy").format(new Date()))
                    .appendMacro("CODE", code)
                    .sendTemplate(new URL("https://nextteam.azurewebsites.net/gmail_code.jsp"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(ForgotPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        if (command.equals("1")) {
            String email = req.getParameter("email");
            System.out.println(email);
            User user = Global.user.selectByEmail(email);
            System.out.println(user.getEmail());
            if (user != null) {
                String[] code = new String[1];
                String type = Global.otpCode.generateOtp(600, user.getId(), code);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        sendVerificationMail(
                                user.getEmail(),
                                "NextTeam - Confirmation code",
                                user.getUsername(),
                                code[0],
                                user.getId()
                        );
                    }
                });
                t.start();
                out.println("{\"code\": \"0\", \"msg\": \"Success!\", \"result\": {\"type\": \"" + type + "\"}}");

            } else {
                out.println("{\"code\": \"1\", \"msg\": \"Email không khớp với bất kỳ người dùng nào!\"}");
            }
        } else if (command.equals("2")) {
            String code = req.getParameter("code");
            String type = req.getParameter("type");
            int[] chance = {0};
            if (Global.otpCode.verifyOtp(code, type, chance)) {
                out.println("{\"code\": \"0\", \"msg\": \"Success!\"}");
            } else {
                out.println("{\"code\": \"1\", \"msg\": \"Bạn đã nhập sai mã xác minh. Vui lòng nhập lại. Bạn còn __res lần.\", \"res\": \"" + chance[0] + "\"}");
            }
        } else if (command.equals("3")) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$")) {
                out.println("{\"code\": \"3\", \"msg\": \"Mật khẩu phải có độ dài trong khoảng 8 - 30 ký tự và phải chứa ít nhất một ký tự số, một ký tự hoa, một ký tự thường, một ký tự đặc biệt!\"}");
                return;
            }
            User user = Global.user.selectByEmail(email);
            if (user != null) {
                Global.user.changePassword(user.getId(), password);
                out.println("{\"code\": \"0\", \"msg\": \"Success!\"}");
            } else {
                out.println("{\"code\": \"2\", \"msg\": \"Có lỗi xảy ra trong quá trình thay đổi mật khẩu!\"}");
            }
        } else {
            out.println("{\"code\": \"-1\", \"msg\": \"Error!\"}");
        }
    }

}
