/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;

/**
 *
 * @author bravee06
 */
@WebServlet(name = "ClubCategoriesServlet", urlPatterns = {"/api/club-categories"})
public class ClubCategoriesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String command = request.getParameter("cmd");
        if (command.equals("list")) {
            String res = Global.clubCategories.getListClubsCategories().toString();
            out.print(res);
            out.flush();
        } 
    }


}
