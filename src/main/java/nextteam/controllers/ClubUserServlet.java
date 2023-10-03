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
import nextteam.models.Club;
import nextteam.models.PublicNotification;

/**
 *
 * @author baopg
 */
@WebServlet(name = "ClubUserServlet", urlPatterns = {"/club-user"})
public class ClubUserServlet extends HttpServlet {
private final Gson gson = new Gson();
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("view-my-list")){
            viewMyList(request, response);
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

}
