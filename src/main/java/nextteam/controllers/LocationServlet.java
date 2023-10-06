/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.Global;
import nextteam.models.Club;
import nextteam.models.Location;
import nextteam.models.response.ClubResponse;

/**
 *
 * @author bravee06
 */
@WebServlet(name = "LocationServlet", urlPatterns = {"/location"})
public class LocationServlet extends HttpServlet {

    private final Gson gson = new Gson();

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        try {
            ArrayList<Location> list = Global.location.getLocationList();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(list));
        } catch (Exception e) {
        }
    }
}
