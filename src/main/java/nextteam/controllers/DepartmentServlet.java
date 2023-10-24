/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.Department;
import nextteam.models.PublicNotification;

/**
 *
 * @author baopg
 */
public class DepartmentServlet extends HttpServlet {

    private final Gson gson = new Gson();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("list-dept")) {
            listDept(request, response);
        }else if(action.equals("add-dept")){
            addDept(request, response);
        }else if(action.equals("edit-dept")){
            editDept(request,response);
        }else{
            deleteDept(request,response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void listDept(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String clubId = request.getParameter("clubId");
        PrintWriter out = response.getWriter();
        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<Department> departments = Global.department.getAllDepartments(clubId);
        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(departments);
        // Gửi JSON response về client
        out.print(json);
        out.flush();
    }

    protected void addDept(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String clubId_raw = request.getParameter("clubId");
        try {
            int clubId = Integer.parseInt(clubId_raw);
            String name = request.getParameter("name");
            int result = Global.department.addDepartment(new Department(clubId, name));
            String json = "";
            if (result == 1) {
                json = "[{ \"status\": \"success\"}]";
            } else {
                json = "[{ \"status\": \"failed\"}]";
            }
            out.print(json);
            out.flush();
        } catch (NumberFormatException e) {
            String json = "[{ \"status\": \"failed\"}]";
            out.print(json);
            out.flush();
        }

    }
    
   
    protected void editDept(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String clubId_raw = request.getParameter("clubId");
        String depId_Raw = request.getParameter("depId");
        try {
            int depId = Integer.parseInt(depId_Raw);
            int clubId = Integer.parseInt(clubId_raw);
            String name = request.getParameter("name");
            int result = Global.department.updateClub(new Department(clubId, name),depId);
            String json = "";
            if (result == 1) {
                json = "[{ \"status\": \"success\"}]";
            } else {
                json = "[{ \"status\": \"failed\"}]";
            }
            out.print(json);
            out.flush();
        } catch (NumberFormatException e) {
            String json = "[{ \"status\": \"failed\"}]";
            out.print(json);
            out.flush();
        }
    }

    protected void deleteDept(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String depId_Raw = request.getParameter("depId");
        try {

            int depId = Integer.parseInt(depId_Raw);
            int result = Global.department.deleteClub(depId);
      
            String json = "";
            if (result == 1) {
                json = "[{ \"status\": \"success\"}]";
            } else {
                json = "[{ \"status\": \"failed\"}]";
            }
            out.print(json);
            out.flush();
        } catch (NumberFormatException e) {
            String json = "[{ \"status\": \"failed\"}]";
            out.print(json);
            out.flush();
        }
    }

}
