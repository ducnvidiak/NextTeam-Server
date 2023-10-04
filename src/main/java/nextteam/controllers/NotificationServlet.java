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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.PrivateNotification;
import nextteam.models.PublicNotification;
import nextteam.models.User;
import nextteam.utils.ConvertPassword;
import nextteam.utils.Gmail;
import nextteam.utils.database.NotificationDAO.Notification;
import nextteam.utils.database.PrivateNotificationDAO;
import nextteam.utils.database.PublicNotificationDAO;
import nextteam.utils.database.UserDAO;

/**
 *
 * @author baopg
 */
public class NotificationServlet extends HttpServlet {

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
        if (action.equals("add-noti")) {
            addNoti(request, response);
        } else if (action.equals("list-noti")) { //trộn cả 2
            listNoti(request, response);
        } else if (action.equals("search-noti")) { //trộn cả 2
            searchNoti(request, response);
        } else if (action.equals("list-10-noti")) { // trộn cả 2
            list10Noti(request, response);
        } else if (action.equals("send-public-email")) {
            sendEmail(request, response);
        } else if (action.equals("add-private-noti")) {
            addPrivateNoti(request, response);
        } else if (action.equals("send-private-email")) {
            sendPrivateEmail(request, response);
        } else if (action.equals("update-view-private-email")) {
            updateViewPrivateEmail(request, response);
        } else if (action.equals("update-view-public-email")) {
            updateViewPublicEmail(request, response);
        } else if (action.equals("delete-public-noti")) {
            deletePublicNoti(request, response);
        } else if (action.equals("update-public-noti")) {
            updatePublicNoti(request, response);
        } else if (action.equals("update-private-noti")) {
            updatePrivateNoti(request, response);
        } else if (action.equals("list-private-noti")) {
            listPrivateNoti(request, response);
        } else if (action.equals("delete-private-noti")) {
            deletePrivateNoti(request, response);
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

    private void sendContentMail(String subject, String title, String content, String... email) {
        try {
            new Gmail(email)
                    .setContentType("text/html; charset=UTF-8")
                    .setSubject(subject)
                    .initMacro()
                    .appendMacro("TITLE", title)
                    .appendMacro("CONTENT", content)
                    .sendTemplate(new URL("http://127.0.0.1:8080/gmail_notification.jsp"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(NotificationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void addNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        PublicNotification pn = this.gson.fromJson(reader, PublicNotification.class);
        response.setContentType("application/json");
        System.out.println("Yêu cầu tạo thông báo");
        PrintWriter out = response.getWriter();
        int status = new PublicNotificationDAO(Global.generateConnection()).addNotification(pn);
        System.out.println("Tạo thông báo thành công");
        String userJsonString = this.gson.toJson(pn);
        out.print(userJsonString);
        out.flush();

    }

    //Test
    private String[] getEmails(List<User> users) {
        List<String> strs = new ArrayList<>();
        for (User user : users) {
            strs.add(user.getEmail());
        }
        return strs.toArray(new String[0]);
    }

    protected void sendEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        PublicNotification pn = this.gson.fromJson(reader, PublicNotification.class);
        List<User> users = Global.user.getListMember(pn.getClubId() + "");

        response.setContentType("application/json");
        System.out.println("Yêu cầu tạo thông báo qua email");
        PrintWriter out = response.getWriter();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                sendContentMail(
                        "NextTeam - " + pn.getTitle(),
                        pn.getTitle(),
                        pn.getContent(),
                        getEmails(users)
                );
            }
        });
        t.start();
        System.out.println("Tạo thông báo email thành công");

    }

    protected void sendPrivateEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        PrivateNotification pn = this.gson.fromJson(reader, PrivateNotification.class);
        String userId = pn.getSendTo() + "";
        User user = Global.user.getListUserByIdString(userId);

        response.setContentType("application/json");
        System.out.println("Yêu cầu tạo thông báo qua email");
        PrintWriter out = response.getWriter();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                sendContentMail(
                        "NextTeam - " + pn.getTitle(),
                        pn.getTitle(),
                        pn.getContent(),
                        user.getEmail()
                );
            }
        });
        t.start();
        System.out.println("Tạo thông báo email thành công");

    }

    protected void listNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String clubId = request.getParameter("clubId");
        String userId = request.getParameter("userId");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<Notification> notifications = Global.notification.getListNotification(clubId, userId);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(notifications);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }
    
    protected void listPrivateNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String clubId = request.getParameter("clubId");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<PrivateNotificationDAO.PrivateNotificationDetail> notifications = Global.privateNotification.getAllPrivateNotificationDetails(clubId);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(notifications);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }

    protected void searchNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String search = request.getParameter("search");
        String clubId = request.getParameter("clubId");
        String userId = request.getParameter("userId");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<Notification> notifications = Global.notification.searchNotification(clubId, userId, search);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(notifications);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }

    protected void list10Noti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String clubId = request.getParameter("clubId");
        String userId = request.getParameter("userId");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<Notification> notifications = Global.notification.getList10Notification(clubId, userId);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(notifications);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }

    protected void addPrivateNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        PrivateNotification pn = this.gson.fromJson(reader, PrivateNotification.class);
        response.setContentType("application/json");
        System.out.println("Yêu cầu tạo thông báo riêng tư");
        PrintWriter out = response.getWriter();
        int status = new PrivateNotificationDAO(Global.generateConnection()).addNotification(pn);
        System.out.println("Tạo thông báo thành công");
        String userJsonString = this.gson.toJson(pn);
        out.print(userJsonString);
        out.flush();

    }

    protected void updateViewPrivateEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        int status = Global.privateNotification.updateSeen(id);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson("Đã cập nhật tình trạng xem");

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }
    protected void updateViewPublicEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String userId = request.getParameter("userId");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        int status = Global.publicNotification.updateView(id,userId);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson("Đã cập nhật tình trạng xem");

        // Gửi JSON response về client
        out.print(json);
        out.flush();
    }


    protected void deletePublicNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        int status = Global.publicNotification.deletePublicNotification(id);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson("Đã xoá thành công!");

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }
    protected void deletePrivateNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        int status = Global.privateNotification.deletePrivateNotification(id);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson("Đã xoá thành công!");

        // Gửi JSON response về client
        out.print(json);
        out.flush();

    }
    

    protected void updatePublicNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        BufferedReader reader = request.getReader();
        PublicNotification pn = this.gson.fromJson(reader, PublicNotification.class);
        response.setContentType("application/json");
        System.out.println("Yêu cầu thay đổi thông báo");
        PrintWriter out = response.getWriter();
        int status = new PublicNotificationDAO(Global.generateConnection()).update(pn);
        System.out.println("Tạo thông báo thành công");
        String userJsonString = this.gson.toJson(pn);
        out.print(userJsonString);
        out.flush();

    }

     protected void updatePrivateNoti(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        BufferedReader reader = request.getReader();
        PrivateNotification pn = this.gson.fromJson(reader, PrivateNotification.class);
        response.setContentType("application/json");
        System.out.println("Yêu cầu thay đổi thông báo");
        PrintWriter out = response.getWriter();
        int status = Global.privateNotification.update(pn);
        System.out.println("Thay đổi thông báo thành công");
        String userJsonString = this.gson.toJson(pn);
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
