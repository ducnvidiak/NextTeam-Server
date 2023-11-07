/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.User;

/**
 *
 * @author bravee06
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/api/user_manager"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String json = "";
        String cmd = request.getParameter("cmd");
       
        if (cmd.equals("list")) {
            String res = Global.user.getListUsers().toString();
            out.print(res);
            out.flush();
        } else{
            String id = request.getParameter("id");
            String clubId = request.getParameter("clubId");
            if (cmd.equals("block")) {
            
            int res = Global.user.blockUser(id);
            if (res == 1) {
                json = "[{ \"status\": \"success\"}]";
            } else {
                json = "[{ \"status\": \"failed\"}]";
            }
            out.print(json);
            out.flush();
        } else if(cmd.equals("unblock")){
            int res = Global.user.unblockUser(id);
            if (res == 1) {
                json = "[{ \"status\": \"success\"}]";
            } else {
                json = "[{ \"status\": \"failed\"}]";
            }
            out.print(json);
            out.flush();
        } else if(cmd.equals("dct_manager")){
            int res = Global.user.dct_manager(clubId,id);
            if (res == 1) {
                json = "[{ \"status\": \"success\"}]";
            } else {
                json = "[{ \"status\": \"failed\"}]";
            }
            out.print(json);
            out.flush();
        }else if(cmd.equals("dct_member")){
           
            
            int res = Global.user.dct_member(clubId,id);
            if (res == 1) {
                json = "[{ \"status\": \"success\"}]";
            } else {
                json = "[{ \"status\": \"failed\"}]";
            }
            out.print(json);
            out.flush();
        }else if(cmd.equals("user_role")){
            String role = Global.user.getUserRoleById(id, clubId);
             json = "[{ \"role\": \""+role+"\"}]";
            out.print(json);
            out.flush();
        }
            
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
