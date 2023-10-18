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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.PaymentCategory;
import nextteam.models.PaymentExpense;
import nextteam.utils.database.PaymentDAO;
import nextteam.utils.database.PaymentDAO.Payment;

/**
 *
 * @author baopg
 */
@WebServlet(name = "PaymentServlet", urlPatterns = {"/payment"})
public class PaymentServlet extends HttpServlet {

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
        if (action.equals("add-category")) {
            addCategory(request, response);
        }else if (action.equals("add-expense")) {
            addExpense(request, response);
        }else if (action.equals("list-payments")) {
            listPayments(request, response);
        }else if (action.equals("sum-balance")) {
            sumBalance(request, response);
        }else if (action.equals("list-of-me")) {
            listOfMe(request, response);
        }else if (action.equals("pay-by-cash")) {
            payByCash(request, response);
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

    protected void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        PaymentCategory pn = this.gson.fromJson(reader, PaymentCategory.class);
        response.setContentType("application/json");
        System.out.println("Yêu cầu tạo khoản nộp");
        PrintWriter out = response.getWriter();
        int status = Global.payment.addCategory(pn);
        System.out.println("Tạo khoản nộp thành công");
        String userJsonString = this.gson.toJson(pn);
        out.print(userJsonString);
        out.flush();

    }
    
    protected void addExpense(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        PaymentExpense pn = this.gson.fromJson(reader, PaymentExpense.class);
        response.setContentType("application/json");
        System.out.println("Yêu cầu tạo khoản chi");
        PrintWriter out = response.getWriter();
        int status = Global.payment.addExpense(pn);
        System.out.println("Tạo khoản chi thành công");
        String userJsonString = this.gson.toJson(pn);
        out.print(userJsonString);
        out.flush();

    }
    
     protected void listPayments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String clubId = request.getParameter("clubId");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<Payment> payments = Global.payment.getAllPayment(clubId);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(payments);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

     }
     
     protected void sumBalance(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String clubId = request.getParameter("clubId");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        PaymentDAO.Balance balances = Global.payment.SumBalance(clubId);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(balances);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

     }
     
      protected void listOfMe(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        List<Payment> payments = Global.payment.getAllPaymentOfMe(userId);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson(payments);

        // Gửi JSON response về client
        out.print(json);
        out.flush();

     }
      
        protected void payByCash(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        PrintWriter out = response.getWriter();

        // Gọi publicNotificationsDAO để lấy danh sách publicNotifications
        int status = Global.payment.payByCash(id);

        // Chuyển danh sách thành dạng JSON
        String json = gson.toJson("Đã cập nhật thanh toán bằng tiền mặt");

        // Gửi JSON response về client
        out.print(json);
        out.flush();
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
