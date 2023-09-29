/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
public class UserAvatarImg extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int userId = Integer.parseInt(request.getParameter("id"));

        Part imagePart = request.getPart("image");

        String fileName = "userAvatar_" + userId + ".jpg";
        InputStream inputStream = imagePart.getInputStream();
        byte[] imageBytes = inputStream.readAllBytes();

        byte[] decodeBytes = Base64.getDecoder().decode(imageBytes);
        String savePath = "E:/Fall23/project/NextTeam-Server/src/main/webapp/images";
        String filePath = savePath + File.separator + fileName;
        try ( OutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(decodeBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User(userId, "http://localhost:8080/images/" + fileName);

        int status = new UserDAO(Global.generateConnection()).updateAvatar(user);
        
        String resJsonString = this.gson.toJson(status == 1? new Success("success"): new Success("failure"));
        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();
    }

}
