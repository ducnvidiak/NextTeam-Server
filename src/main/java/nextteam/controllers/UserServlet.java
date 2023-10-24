/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;

/**
 *
 * @author bravee06
 */
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
        } else if(cmd.equals("dct_admin")){
            
            
            int res = Global.user.dct_admin(id);
            if (res == 1) {
                json = "[{ \"status\": \"success\"}]";
            } else {
                json = "[{ \"status\": \"failed\"}]";
            }
            out.print(json);
            out.flush();
        }else if(cmd.equals("dct_user")){
           
            
            int res = Global.user.dct_user(id);
            if (res == 1) {
                json = "[{ \"status\": \"success\"}]";
            } else {
                json = "[{ \"status\": \"failed\"}]";
            }
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
