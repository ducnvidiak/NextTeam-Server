/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.User;

/**
 *
 * @author vnitd
 */
@WebServlet(name = "ForgotPassword", urlPatterns = {"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {

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
            if (user != null) {
                String type = Global.otpCode.generateOtp(120, user.getId());
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
        } else {
            out.println("{\"code\": \"-1\", \"msg\": \"Error!\"}");
        }
    }

}
