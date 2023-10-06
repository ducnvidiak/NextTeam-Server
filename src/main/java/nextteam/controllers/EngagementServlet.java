/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.Department;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.http.Part;
import nextteam.models.Engagement;
import nextteam.utils.database.EngagementDAO;

/**
 *
 * @author baopg
 */
public class EngagementServlet extends HttpServlet {

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
        String action = request.getParameter("action");
        if (action.equals("add-engagement")) {
            addEngagement(request, response);
        } else if (action.equals("application-list-of-user")) {
            applicationListOfUser(request, response);
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
        processRequest(request, response);
    }

    protected void addEngagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int userId = Integer.parseInt(request.getParameter("userId"));
        int clubId = Integer.parseInt(request.getParameter("clubId"));
        int departmentId = Integer.parseInt(request.getParameter("departmentId"));
        // Nhận file từ yêu cầu
        //xử lý upload file
        String folderName = "/cv";
        String uploadPath = "/Users/baopg/NetBeansProjects/NextTeam-Server/src/main/webapp/cv";//for netbeans use this code
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Part filePart = request.getPart("cvUrl");
        String fileName ="ApplicationRegister-"+ System.currentTimeMillis() +"-UserId"+ userId + "-" + filePart.getSubmittedFileName().replaceAll(" ", "");
        String path = folderName + File.separator + fileName;
        System.out.println("Path: " + uploadPath);
        InputStream is = filePart.getInputStream();
        Files.copy(is, Paths.get(uploadPath + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);        //Add 

        System.out.println("test4");
        Engagement pn = new Engagement(userId, departmentId, clubId, path);
        response.setContentType("application/json");
        System.out.println("Yêu cầu tham gia CLB");
        PrintWriter out = response.getWriter();
        int status = Global.engagement.addEngagement(pn);
        System.out.println("Yêu cầu tham gia thành công");
        String userJsonString = this.gson.toJson(pn);
        out.print(userJsonString);
        out.flush();

        // Phản hồi với thông báo thành công (hoặc thông tin khác nếu cần)
        response.getWriter().print("Upload successful: " + fileName);

    }

    protected void applicationListOfUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String userId = request.getParameter("userId");

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<Engagement> engagements = Global.engagement.getListOfMe(userId);
        List<EngagementDAO.EngagementModelInfo> emis = Global.engagement.getEngagementModelList(engagements);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(emis);
        System.out.println(json);

        // Gửi JSON response về client
        out.print(json);
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
