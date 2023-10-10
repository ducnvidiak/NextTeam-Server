/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import static nextteam.Global.user;
import nextteam.models.Club;
import nextteam.models.PublicNotification;
import nextteam.models.User;

/**
 *
 * @author baopg
 */
public class ClubUserServlet extends HttpServlet {
private final Gson gson = new Gson();
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("view-my-list")){
            viewMyList(request, response);
        }
        else if(action.equals("view-club-member")){
            viewClubMember(request,response);
        }else if(action.equals("view-list-user")){
            viewListUser(request,response);
        }
    }

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void viewMyList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<Club> clubs = Global.clubDAO.getListClubs();
//                Global.clubDAO.getListClubsOfMe(userId);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(clubs);

        // Gửi JSON response về client
        out.print(json);
        out.flush();
    }
    protected void viewClubMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String clubId = request.getParameter("clubId");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<User> users = Global.user.getListMember(clubId);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(users);

        // Gửi JSON response về client
        out.print(json);
        out.flush();
    }
    
     protected void viewListUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

         List<User> users = Global.user.getListUsers();

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(users);

        // Gửi JSON response về client
        out.print(json);
        out.flush();
    }

}
