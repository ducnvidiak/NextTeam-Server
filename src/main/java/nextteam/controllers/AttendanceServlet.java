/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.Attendance;

/**
 *
 * @author vnitd
 */
@WebServlet(name = "AttendanceServlet", urlPatterns = {"/attendance"})
public class AttendanceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if (cmd.equals("set")) {
            Gson gson = new Gson();
            String data = req.getParameter("data");

            JsonArray ja = gson.fromJson(data, JsonArray.class);
            Type type = new TypeToken<List<Attendance>>() {
                // TODO:
            }.getType();

            System.out.println(data);
            List<Attendance> attendances = gson.fromJson(ja, type);
            System.out.println(attendances.get(0).isAtten());
            Global.eventRegistration.takeAttendances(attendances);
            resp.getWriter().println("""
                                 {
                                    "res": "0"
                                 }""");
        } else if (cmd.equals("get")) {

        } else if (cmd.equals("take")) {
            String mid = req.getParameter("member");
            String eid = req.getParameter("event");
            Global.eventRegistration.take(Integer.parseInt(mid), Integer.parseInt(eid));
            resp.getWriter().println("""
                                 {
                                    "res": "0"
                                 }""");
        } else {
            resp.getWriter().println("""
                                 {
                                    "res": "1"
                                 }""");
        }
    }

}
