/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nextteam.Global;
import nextteam.models.User;
import com.google.gson.Gson;

/**
 *
 * @author baopg
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        System.out.println("email: " + email);
        System.out.println("password: " + password);

        response.setContentType("application/json");
        System.out.println("Yêu cầu đăng nhập");
        PrintWriter out = response.getWriter();

        final User userLogin = Global.user.login(email, password);
        
        System.out.println(userLogin);
        if (userLogin != null) {
            System.out.println("Đăng nhập thành công");
            final HttpSession session = request.getSession();
            userLogin.setPassword("No Data");
            session.setAttribute("userLogin", (Object) userLogin);
            String userJsonString = this.gson.toJson(userLogin);
            System.out.println(userJsonString);
            out.print(userJsonString);
            out.flush();
        } else {
            System.out.println("Đăng nhập thất bại");
            String error = "Thông tin đăng nhập chưa đúng!";
            String errorJsonString = this.gson.toJson(error);
            out.print(errorJsonString);
            out.flush();
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //huỷ bỏ ssession
        session.invalidate();

    }
}
