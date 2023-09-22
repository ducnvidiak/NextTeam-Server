/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nextteam.Global;
import nextteam.models.Success;
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
public class UserInfoServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = new UserDAO(Global.generateConnection()).getListUserByIdString(request.getParameter("id"));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String userJsonString = this.gson.toJson(user);

        PrintWriter out = response.getWriter();
        out.print(userJsonString);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);
        
        System.out.println("data update: " + user.getUsername());

        int status = new UserDAO(Global.generateConnection()).update(user);
        String resJsonString = this.gson.toJson(status == 1? new Success("success"): new Success("failure"));
        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();
    }

}
