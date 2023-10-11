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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.Authentication;
import nextteam.models.Success;
import nextteam.models.User;
import nextteam.utils.encryption.BCrypt;

/**
 *
 * @author admin
 */
public class UserPasswordServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("id");
        System.out.println("id: " + userId);
        BufferedReader reader = request.getReader();
        Authentication reAuth = gson.fromJson(reader, Authentication.class);

        String oldPassword = reAuth.getOldPassword();
        String newPassword = reAuth.getNewPassword();
        String email = reAuth.getEmail();
        int status = 0;
      
        if (!oldPassword.equals(newPassword)) {
            String hash = Global.getHashedPassword(newPassword);
            User user = Global.user.getListUserByIdString(userId);

            if (BCrypt.checkpw(oldPassword, user.getPassword())) {
                user.setPassword(Global.getHashedPassword(newPassword));
                status = Global.user.updatePassword(user);
                System.out.println("Success change password!" + userId);
            } else {
                System.out.println("Fail to change password!");
            }
        }
        String resJsonString = this.gson.toJson(status == 1 ? new Success("success") : new Success("Failure! Old password is the same as new password or not corrent!"));
        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();
    }
}
