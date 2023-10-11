/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.User;
import nextteam.utils.encryption.AES;

/**
 *
 * @author vnitd
 */
@WebServlet(name = "GetInfoServlet", urlPatterns = {"/info-utils"})
public class GetInfoServlet extends HttpServlet {

    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        String data = req.getParameter("data");

        String res = "{\"code\": \"-1\"}";

        if (cmd.equals("user")) {
            if (!data.equals("undefined")) {
                res = AES.decryptString(data);
            }
        } else if (cmd.equals("user.role")) {
            String json = AES.decryptString(data);

            int clubId = Integer.parseInt(req.getParameter("clubId"));
            int userId = gson.fromJson(json, User.class).getId();

            String[] role = Global.engagement.getRoleByUserIdAndClubId(userId, clubId);

            res = "{\"roleId\": \"" + role[0] + "\", \"roleName\": \"" + role[1] + "\"}";
        } else if (cmd.equals("club.users")) {
            res = Global.clubDAO.getAllUserOfClub(Integer.parseInt(data)).toString();
        } else if (cmd.equals("user.points")) {
            int clubId = Integer.parseInt(req.getParameter("clubId"));
            int userId = Integer.parseInt(req.getParameter("userId"));

            res = """
                  {
                    "code": "0",
                    "result": %s
                  }""".formatted(Global.engagement.getEngagementsPointsRanking(userId, clubId));
        } else if (cmd.equals("user.points.history")) {
            int clubId = Integer.parseInt(req.getParameter("clubId"));
            int userId = Integer.parseInt(req.getParameter("userId"));

            res = """
                  {
                    "code": "0",
                    "result": %s
                  }""".formatted(Global.pointHistory.listOfSpecifiedUser(clubId, userId));
        }

        resp.getWriter().print(res);
    }

}
