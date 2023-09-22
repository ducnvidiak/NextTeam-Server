/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import nextteam.Global;
import nextteam.models.User;
import nextteam.utils.database.UserDAO;

/**
 *
 * @author admin
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // Kích thước tệp tạm thời trước khi lưu vào đĩa (1MB)
        maxFileSize = 1024 * 1024 * 10, // Kích thước tối đa cho mỗi tệp (10MB)
        maxRequestSize = 1024 * 1024 * 50 // Kích thước tối đa cho một yêu cầu (50MB)
)
public class UserServlet extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
        User user = new UserDAO(Global.generateConnection()).getListUserByIdString(request.getParameter("id"));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String userJsonString = this.gson.toJson(user);

        PrintWriter out = response.getWriter();
        out.print(userJsonString);
        out.flush();
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
//        processRequest(request, response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        BufferedReader reader = request.getReader();
        User user = this.gson.fromJson(reader, User.class);

        int status = new UserDAO(Global.generateConnection()).insert(user);

        User addedUser = new UserDAO(Global.generateConnection()).selectByEmail(user);

        String userJsonString = this.gson.toJson(addedUser);

        PrintWriter out = response.getWriter();
        out.print(userJsonString);
        out.flush();

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Part jsonPart = request.getPart("data");
        InputStream jsonStream = jsonPart.getInputStream();
        BufferedReader jsonReader = new BufferedReader(new InputStreamReader(jsonStream));

        User user = this.gson.fromJson(jsonReader, User.class);

        Part imagePart = request.getPart("image");

        if (imagePart != null) {
            String fileName = "userAvatar_" + user.getId() + ".jpg";
            InputStream inputStream = imagePart.getInputStream();
            byte[] imageBytes = inputStream.readAllBytes();

            byte[] decodeBytes = Base64.getDecoder().decode(imageBytes);
            String savePath = "E:/Fall23/project/NextTeam-Server/src/main/webapp/images";
            String filePath = savePath + File.separator + fileName;
            try ( OutputStream outputStream = new FileOutputStream(filePath)) {
                outputStream.write(decodeBytes);

            } catch (IOException e) {
                e.printStackTrace();
            }

            user.setAvatarURL("http://localhost:8080/NextTeam/images/" + fileName);
        }
        
        int status = new UserDAO(Global.generateConnection()).update(user);
        User updatedUser = new UserDAO(Global.generateConnection()).getListUserByIdString("" + user.getId());
        String userJsonString = this.gson.toJson(updatedUser);
        PrintWriter out = response.getWriter();
        out.print(userJsonString);
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
