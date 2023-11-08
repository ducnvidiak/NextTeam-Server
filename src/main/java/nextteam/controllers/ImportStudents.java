/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.Student;

/**
 *
 * @author vnitd
 */
@WebServlet(name = "ImportStudents", urlPatterns = {"/import-student"})
public class ImportStudents extends HttpServlet {

    final String[] ERROR = {
        "Success",
        "MSSV không đúng định dạng",
        "Ngày sinh không đúng định dạng",
        "Giới tính không đúng định dạng"
    };

    private Date checkDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    private int checkInfo(String username, String birthDate, String gender) {
        if (!username.matches("^[HhDdSsQqCc][AaEeSs][0-9]{6}$")) {
            return 1;
        } else if (checkDate(birthDate) == null) {
            return 2;
        } else if (!gender.equals("Nam") && !gender.equals("Nữ") && !gender.equals("Khác")) {
            return 3;
        }
        return 0;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        String cmd = req.getParameter("cmd");

        if (cmd.equals("import")) {

            String username = req.getParameter("username");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String birthDate = req.getParameter("birthDate");
            String homeTown = req.getParameter("homeTown");
            String gender = req.getParameter("gender");
            int check = checkInfo(username, birthDate, gender);
            if (check != 0) {
                out.println("{\"status\": \"" + check + "\", \"msg\": \"" + ERROR[check] + "\"}");
            } else {
                int i = Global.student.add(new Student(username, firstName, lastName, new java.sql.Date(checkDate(birthDate).getTime()), homeTown, gender));
                if (i >= 0) {
                    out.println("{\"status\": \"" + check + "\", \"msg\": \"" + ERROR[check] + "\"}");
                } else {
                    out.println("{\"status\": \"" + (-1) + "\", \"msg\": \"Trùng mã số sinh viên!\"}");
                }

            }
        } else if (cmd.equals("view")) {
            int filter = Integer.parseInt(req.getParameter("filter"));
            out.println("{\"status\": 0, \"data\": " + Global.student.filterAll(filter) + "}");
        } else if (cmd.equals("del")) {
            String id = req.getParameter("id");
            Global.student.delete(id);
            out.println("{\"status\": 0}");
        }
    }
}
