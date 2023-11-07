/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.Student;
import nextteam.models.User;
import nextteam.utils.Gmail;
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
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void sendContentMail(String email, String subject, String firstName, String lastName, String studentId, String phone) {
        try {
            new Gmail(email)
                    .setContentType("text/html; charset=UTF-8")
                    .setSubject(subject)
                    .initMacro()
                    .appendMacro("FIRSTNAME", firstName)
                    .appendMacro("LASTNAME", lastName)
                    .appendMacro("EMAIL", email)
                    .appendMacro("STUDENTID", studentId)
                    .appendMacro("PHONE", phone)
                    .sendTemplate(new URL("https://nextteam.azurewebsites.net/gmail_register.jsp"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(UserRegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String standardizeName(String fullName) {
        if (fullName == null) {
            return null;
        }

        // Split the full name into parts based on white spaces
        String[] nameParts = fullName.trim().split("\\s+");

        if (nameParts.length == 0) {
            return fullName; // Unable to split, return original name
        }

        // Capitalize the first letter of each part (e.g., John Doe -> John Doe)
        StringBuilder standardizedName = new StringBuilder();
        for (String namePart : nameParts) {
            if (namePart.length() > 0) {
                standardizedName.append(namePart.substring(0, 1).toUpperCase());
                if (namePart.length() > 1) {
                    standardizedName.append(namePart.substring(1).toLowerCase());
                }
                standardizedName.append(" ");
            }
        }

        // Remove trailing space and return the standardized name
        return standardizedName.toString().trim();
    }

    private String[] MESSAGE = {
        "Success!",
        "Email không đúng định dạng!",
        "Mật khẩu phải có độ dài trong khoảng 8 - 30 ký tự và phải chứa ít nhất một ký tự số, một ký tự hoa, một ký tự thường, một ký tự đặc biệt!",
        "Mã sinh viên không đúng định dạng!",
        "Số điện thoại không đúng định dạng!",
        "Mã sinh viên đã tồn tại! Vui lòng thử mã sinh viên khác!",
        "Email đã tồn tại! Vui lòng thử email khác",
        "Mã số sinh viên không tồn tại hoặc đã được đăng ký.",
        "Thông tin họ tên không khớp với mã số sinh viên"
    };

    private int checkUser(User user) {
        UserDAO userDb = Global.user;
        Student check = Global.student.check(user.getUsername());
        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return 1;

        } else if (!user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*/.]).{8,30}$")) {

            return 2;
        } else if (!user.getUsername().matches("^[HhDdSsQqCc][AaEeSs][0-9]{6}$")) {
            return 3;
        } else if (!user.getPhoneNumber().matches("^(\\+84|0)\\d{9,10}$")) {
            return 4;
        } else if (userDb.StudentCodeCheck(user.getUsername())) {
            return 5;
        } else if (userDb.selectByEmail(user.getEmail()) != null) {
            return 6;
        } else if (check == null) {
            return 7;
        } else if (!check.getFirstname().equals(user.getFirstname()) || !check.getLastname().equals(user.getLastname())) {
            return 8;
        }
        return 0;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);

//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//        String firstname = request.getParameter("firstname");
//        String lastname = request.getParameter("lastname");
//        String username = request.getParameter("studentCode").toUpperCase();
//        String phoneNumber = request.getParameter("phoneNumber");
//        String gender = request.getParameter("gender");
        user.setFirstname(standardizeName(user.getFirstname()));
        user.setLastname(standardizeName(user.getLastname()));
        System.out.println("email: " + user.getEmail());
        System.out.println("password: " + user.getPassword());
        System.out.println("firstname: " + standardizeName(user.getFirstname()));
        System.out.println("lastname: " + standardizeName(user.getLastname()));
        System.out.println("student code: " + user.getUsername());
        System.out.println("phoneNumber: " + user.getPhoneNumber());
        //public User(String email, String username, String password, String studentCode, String phoneNumber, String gender)
//        User user = new User(email, username, password, phoneNumber, gender);
//        user.setFirstname(firstname);
//        user.setLastname(lastname);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println("Yêu cầu đăng ký");
        PrintWriter out = response.getWriter();
        ////////////////////////
        int res = checkUser(user);
        user.setPassword(Global.getHashedPassword(user.getPassword()));

        if (res == 0) {
            Global.user.register(user);
            Global.student.register(user.getUsername());
        }
        out.print("{\"code\": \"" + res + "\", \"msg\": \"" + MESSAGE[res] + "\"}");
        out.flush();
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
