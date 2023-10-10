/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import static nextteam.Global.eventRegistration;
import nextteam.models.Club;
import nextteam.models.EventRegistration;
import nextteam.models.PublicNotification;
import nextteam.models.User;
import nextteam.models.response.EventRegistrationResponse;
import nextteam.models.response.EventResponse;
import nextteam.utils.database.EventDAO;

/**
 *
 * @author baopg
 */
public class EventRegistrationServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final EventDAO eventDAO;

    public EventRegistrationServlet() {
        // Khởi tạo EventDAO với kết nối cơ sở dữ liệu
        this.eventDAO = Global.eventDao;
    }

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
        System.out.println("!!!");
        String action = request.getParameter("action");
//        if (action.equals("view-my-list")) {
//            viewMyList(request, response);
//        }
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
        String eventId = request.getParameter("eventId");
        List<EventRegistrationResponse> eventRegistrations = Global.eventRegistration.getAllEventRegistrationByEventId(eventId);
        String eventsJsonString = gson.toJson(eventRegistrations);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(eventsJsonString);
        out.flush();
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
        BufferedReader reader = request.getReader();
        EventRegistration eventRegistration = this.gson.fromJson(reader, EventRegistration.class);
        final int registration;
        PrintWriter out = response.getWriter();

        try {
            registration = Global.eventRegistration.addNewRegistration(eventRegistration);
            if (registration == -1) {
                ErrorResponse errorResponse = new ErrorResponse("Không tìm thấy người với ID ");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(errorResponse));
//                out.print(gson.toJson(errorResponse));
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                List<EventResponse> events = eventDAO.getAllEventsDetail(String.valueOf(eventRegistration.getRegisteredBy()));
//                List<EventResponse> events = eventDAO.getAllEventsDetail(userId);
                System.out.println("events" + events);
                System.out.println("nextteam.controllers.EventServlet.doGet()" + events);
                // Chuyển danh sách sự kiện thành JSON
//                String eventsJsonString = gson.toJson(events);
                response.getWriter().write(gson.toJson(events));
            }
//            out.print(registration);
        } catch (Exception ex) {
//            out.print(ex);
            Logger.getLogger(EventRegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
//        if (registration == 1) {
////             json = "[{ \"status\": \"success\"}]";
////            String error = "Đăng ký tham gia sự kiện thành công";
////            String errorJsonString = this.gson.toJson(error);
//            out.print("[{ \"status\": \"success\"}]");
//            out.flush();
//        } else {
//            String error = "Gặp lỗi khi đăng ký, vui lòng thử lại";
//            String errorJsonString = this.gson.toJson(error);
//           
////            out.print(errorJsonString);
//            out.flush();
//        }
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
