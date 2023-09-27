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
import nextteam.Global;
import nextteam.models.Club;

/**
 *
 * @author bravee06
 */
public class ClubServlet extends HttpServlet {

  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Access-Control-Allow-Origin", "*");
    PrintWriter out = response.getWriter();
    String command = request.getParameter("cmd");
    if (command.equals("list")) {
      String res = Global.clubDAO.getListClubs().toString();
      out.print(res);
      out.flush();
    } else if (command.equals("add")) {
      String name = request.getParameter("name");
      String subname = request.getParameter("subname");
      int categoryId;
      String description = request.getParameter("description");

      String avatarUrl = request.getParameter("avatarUrl");

      String bannerUrl = request.getParameter("bannerUrl");

      int movementPoint;
      double balance;

      try {
        categoryId = Integer.parseInt(request.getParameter("categoryId"));
        movementPoint = Integer.parseInt(request.getParameter("movementPoint"));
        balance = Double.parseDouble(request.getParameter("balance"));
      } catch (NumberFormatException e) {
        movementPoint = 0;
        balance = 0;
        categoryId = 1;
      }
      Club c = new Club(
        name,
        subname,
        categoryId,
        description,
        avatarUrl,
        bannerUrl,
        movementPoint,
        balance
      );
      int added = Global.clubDAO.addClub(c);
      String json = "";
      if (added == 1) {
        json = "[{ \"status\": \"success\"}]";
      } else {
        json = "[{ \"status\": \"failed\"}]";
      }
      out.print(json);
      out.flush();
    } else if (command.equals("update")) {
      int id = Integer.parseInt(request.getParameter("id"));

      String name = request.getParameter("name");
      String subname = request.getParameter("subname");
      int categoryId;
      String description = request.getParameter("description");

      String avatarUrl = request.getParameter("avatarUrl");

      String bannerUrl = request.getParameter("bannerUrl");

      int movementPoint;
      double balance;

      try {
        categoryId = Integer.parseInt(request.getParameter("categoryId"));
        movementPoint = Integer.parseInt(request.getParameter("movementPoint"));
        balance = Double.parseDouble(request.getParameter("balance"));
      } catch (NumberFormatException e) {
        movementPoint = 0;
        balance = 0;
        categoryId = 1;
      }
      Club c = new Club(
        name,
        subname,
        categoryId,
        description,
        avatarUrl,
        bannerUrl,
        movementPoint,
        balance
      );
      int updated = Global.clubDAO.updateClub(c, id);
      String json = "";
      if (updated == 1) {
        json = "[{ \"status\": \"success\"}]";
        out.print(json);
      } else {
        json = "[{ \"status\": \"failed\"}]";
        out.print(json);
      }
      out.print(json);
      out.flush();
    } else {
      int id = Integer.parseInt(request.getParameter("id"));

      int deleted = Global.clubDAO.deleteClub(id);
      String json = "";
      if (deleted == 1) {
        json = "[{ \"status\": \"success\"}]";
      } else {
        json = "[{ \"status\": \"failed\"}]";
      }
      out.print(json);
      out.flush();
    }
  }
}
