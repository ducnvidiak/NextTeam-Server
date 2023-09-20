/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.User;
import nextteam.utils.database.UserDAO;

/**
 *
 * @author baopg
 */
@WebServlet(name = "UserRegisterServlet", urlPatterns = {"/user-register"})
public class UserRegisterServlet extends HttpServlet {

    private final Gson gson = new Gson();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserRegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserRegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
        BufferedReader reader = request.getReader();
        User user = this.gson.fromJson(reader, User.class);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        System.out.println("Yêu cầu đăng ký");
        PrintWriter out = response.getWriter();
        // Sử dụng phương thức indexOf để tìm vị trí của ký tự "@"
        String email = user.getEmail();
        int atIndex = email.indexOf('@');
        String username="";
        // Kiểm tra xem có ký tự "@" trong chuỗi email hay không
        if (atIndex != -1) {
            // Sử dụng phương thức substring để lấy chuỗi trước ký tự "@"
            username = email.substring(0, atIndex);
        } 
        user.setUsername(username);
        int status = new UserDAO(Global.generateConnection()).register(user);
        User addedUser = new UserDAO(Global.generateConnection()).selectByEmailAndPassword(user);
        if (addedUser != null) {
            System.out.println("Đăng ký thành công");
            String userJsonString = this.gson.toJson(addedUser);
            out.print(userJsonString);
            out.flush();
        }else{
            System.out.println("Đăng ký thất bại");
            String error = "Bạn chưa đăng ký thành công!";
            String errorJsonString = this.gson.toJson(error);
            out.print(errorJsonString);
            out.flush();
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
