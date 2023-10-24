/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.PointsHistory;
import nextteam.models.User;

/**
 *
 * @author vnitd
 */
@WebServlet(name = "ActivityServlet", urlPatterns = {"/activity"})
public class ActivityServlet extends HttpServlet {

    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if (cmd.equals("add")) {
            String jsonArray = req.getParameter("name");
            int point = Integer.parseInt(req.getParameter("point"));
            String note = req.getParameter("note");
            int sendBy = Integer.parseInt(req.getParameter("sendBy"));
            int clubId = Integer.parseInt(req.getParameter("clubId"));

            JsonArray ja = gson.fromJson(jsonArray, JsonArray.class);

            Type type = new TypeToken<List<User>>() {
                // TODO:
            }.getType();

            List<User> users = gson.fromJson(ja, type);
            for (User user : users) {
                PointsHistory ph = new PointsHistory(sendBy, user.getId(), clubId, point, note);
                Global.pointHistory.add(ph);

            }

            resp.getWriter().println("""
                                 {
                                    "res": "0"
                                 }""");
        } else if (cmd.equals("view")) {
            int clubId = Integer.parseInt(req.getParameter("clubId"));
            resp.getWriter().println("""
                                     {"code": "0", "result": %s}""".formatted(Global.pointHistory.getAll(clubId)));

        } else if (cmd.equals("edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            int point = Integer.parseInt(req.getParameter("point"));
            String note = req.getParameter("note");
            Global.pointHistory.edit(id, point, note);
            resp.getWriter().println("{\"code\": \"0\"}");
        } else if (cmd.equals("del")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Global.pointHistory.delete(id);
            resp.getWriter().println("{\"code\": \"0\"}");
        }
    }

}
