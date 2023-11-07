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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;
import nextteam.models.Engagement;
import nextteam.models.EntranceInterview;
import nextteam.models.Location;
import nextteam.utils.Gmail;
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
        } else if (action.equals("approve-application")) {
            approveApplication(request, response);
        } else if (action.equals("reject-application")) {
            rejectApplication(request, response);
        } else if (action.equals("drop-out-application")) {
            dropOutApplication(request, response);
        } else if (action.equals("set-interview")) {
            setInterview(request, response);
        } else if (action.equals("interview")) {
            interview(request, response);
        } else if (action.equals("application-list-of-club")) {
            applicationListOfClub(request, response);
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
        String uploadPath = "/Users/mac/Documents/SWP301/NextTeam-Server/src/main/webapp/cv";//for netbeans use this code
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Part filePart = request.getPart("cvUrl");
        String fileName = "ApplicationRegister-" + System.currentTimeMillis() + "-UserId" + userId + "-" + filePart.getSubmittedFileName().replaceAll(" ", "");

        String path = folderName + File.separator + fileName;
        System.out.println("Path: " + uploadPath);
        InputStream is = filePart.getInputStream();
        Files.copy(is, Paths.get(uploadPath + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);        //Add 

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

        List<Engagement> engagements = Global.engagement.getListOfMe(userId);
        List<EngagementDAO.EngagementModelInfo> emis = Global.engagement.getEngagementModelList(engagements);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(emis);
        System.out.println(json);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }

    protected void applicationListOfClub(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String clubId = request.getParameter("clubId");

        List<Engagement> engagements = Global.engagement.getListOfClub(clubId);
        List<EngagementDAO.EngagementModelInfo> emis = Global.engagement.getEngagementModelList(engagements);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(emis);
        System.out.println(json);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }

    protected void approveApplication(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        PrintWriter out = response.getWriter();

        int status = Global.engagement.ApproveApplication(id);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson("Đã cập nhật phê duyệt đơn đăng ký");

        // Gửi JSON response về client
        out.print(json);
        out.flush();
    }

    protected void rejectApplication(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        PrintWriter out = response.getWriter();

        int status = Global.engagement.RejectApplication(id);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson("Đã cập nhật từ chối đơn đăng ký");

        // Gửi JSON response về client
        out.print(json);
        out.flush();
    }

    protected void dropOutApplication(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        PrintWriter out = response.getWriter();

        int status = Global.engagement.DropoutApplication(id);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson("Đã cập nhật đơn đăng ký xin ra khỏi CLB");

        // Gửi JSON response về client
        out.print(json);
        out.flush();
    }

    private void sendContentMail(String subject, String club, String name, Date startTime, Date endTime, String location, String note, String... email) {
        try {
            new Gmail(email)
                    .setContentType("text/html; charset=UTF-8")
                    .setSubject(subject)
                    .initMacro()
                    .appendMacro("NAME", name)
                    .appendMacro("CLUB", club)
                    .appendMacro("STARTTIME", startTime + "")
                    .appendMacro("ENDTIME", endTime + "")
                    .appendMacro("LOCATION", location)
                    .appendMacro("NOTE", note)
                    .sendTemplate(new URL("https://nextteam.azurewebsites.net/gmail_interview.jsp"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(NotificationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void setInterview(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String club = request.getParameter("club");
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String note = request.getParameter("note");
        String email = request.getParameter("email");

        Location location1 = Global.location.getLocationById(location);

        BufferedReader reader = request.getReader();
        EntranceInterview pn = this.gson.fromJson(reader, EntranceInterview.class);
        int status = Global.engagement.InterviewApplication(pn.getEngagementId() + "");
        response.setContentType("application/json");
        System.out.println("Yêu cầu tạo lịch phỏng vấn");
        PrintWriter out = response.getWriter();
        int interview = Global.entranceInterview.addInterview(pn);
        System.out.println("Tạo lịch phỏng vấn thành công");
        String userJsonString = this.gson.toJson(pn);
        out.print(userJsonString);
        out.flush();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                sendContentMail(
                        club + " - THƯ MỜI THAM GIA PHỎNG VẤN",
                        club,
                        name,
                        pn.getStartTime(),
                        pn.getEndTime(),
                        location1.getName(),
                        note,
                        email
                );
            }
        });
        t.start();
        System.out.println("Tạo thông báo email thành công");
    }

    protected void interview(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        EntranceInterview pn = this.gson.fromJson(reader, EntranceInterview.class);
        int update = Global.entranceInterview.Update(pn);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson("Đã cập nhật thông tin phỏng vấn");
        if (pn.isIsApproved()) {
            int status = Global.engagement.ApproveApplication(pn.getEngagementId() + "");
        } else {
            int status = Global.engagement.InterviewApplication(pn.getEngagementId() + "");
        }

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String clubId = request.getParameter("clubId");
        String userId = request.getParameter("userId");
        int status = Integer.parseInt(request.getParameter("status"));
        System.out.println("Received request change user status: " + clubId + " " + userId + " " + status);
        int result = 0;
        
        result = Global.engagement.updateUserStatus(clubId, userId, status);
        
        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty("status", (result == 1 ? "success" : "failure"));

        PrintWriter out = response.getWriter();
        String resJsonString = this.gson.toJson(jsonRes);
        out.print(resJsonString);
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
