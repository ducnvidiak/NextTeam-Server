/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.Major;
import nextteam.utils.database.MajorDAO;

/**
 *
 * @author admin
 */
public class MajorInfoServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Major> majorList = new MajorDAO(Global.generateConnection()).getAllMajors();
        String majorListJson = gson.toJson(majorList);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println("logged!");
        PrintWriter out = response.getWriter();
        out.print(majorListJson);
        out.flush();
    }

}
