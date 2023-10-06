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
import nextteam.utils.encryption.AES;

/**
 *
 * @author vnitd
 */
@WebServlet(name = "GetInfoServlet", urlPatterns = {"/info-utils"})
public class GetInfoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        String data = req.getParameter("data");

        String res = "{}";

        if (cmd.equals("user")) {
            System.out.println(data);
            if (!data.equals("undefined")) {
                res = AES.decryptString(data);
            }
        }

        resp.getWriter().print(res);
    }

}
