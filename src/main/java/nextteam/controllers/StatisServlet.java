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
import nextteam.utils.database.StatisDAO;

/**
 *
 * @author bravee06
 */
@WebServlet(name = "StatisServlet", urlPatterns = {"/api/statis"})
public class StatisServlet extends HttpServlet {
    class Statis{
        private int total_mem;
        private int total_mem_out;
        private int total_mem_ban;
        private int total_event;
        private int[] total_event_months;
        private int balance;
        private int activity_point;
        private int total_report;
        private int total_post;

        public Statis(int total_mem, int total_mem_out, int total_mem_ban, int total_event, int[] total_event_months, int balance, int activity_point, int total_report, int total_post) {
            this.total_mem = total_mem;
            this.total_mem_out = total_mem_out;
            this.total_mem_ban = total_mem_ban;
            this.total_event = total_event;
            this.total_event_months = total_event_months;
            this.balance = balance;
            this.activity_point = activity_point;
            this.total_report = total_report;
            this.total_post = total_post;
        }

        public Statis() {
        }

        @Override
        public String toString() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\"total_mem\":").append(total_mem).append(",");
        jsonBuilder.append("\"total_mem_out\":").append(total_mem_out).append(",");
        jsonBuilder.append("\"total_mem_ban\":").append(total_mem_ban).append(",");
        jsonBuilder.append("\"total_mem_active\":").append(total_mem - total_mem_out - total_mem_ban).append(",");
        jsonBuilder.append("\"total_event\":").append(total_event).append(",");
         jsonBuilder.append("\"total_event_months\":[");
        
        // Lặp qua mảng và thêm từng phần tử vào chuỗi JSON
        for (int i = 0; i < total_event_months.length; i++) {
            jsonBuilder.append(total_event_months[i]);
            if (i < total_event_months.length - 1) {
                jsonBuilder.append(",");
            }
        }
//        
        jsonBuilder.append("],");
        jsonBuilder.append("\"balance\":").append(balance).append(",");
        jsonBuilder.append("\"activity_point\":").append(activity_point).append(",");
        jsonBuilder.append("\"total_report\":").append(total_report).append(",");
        jsonBuilder.append("\"total_post\":").append(total_post);
        jsonBuilder.append("}");
        
        return jsonBuilder.toString();        }

        
        
    }
    
  

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String clubID = request.getParameter("clubId");
        System.out.println("ab");
        System.out.println(clubID);
        int total_mem =Global.statis.getNumberMemberInClub(clubID);
        System.out.println(total_mem);
        System.out.println(total_mem);
        int total_mem_out = Global.statis.getNumberMemberOutClub(clubID);
        int total_mem_ban = Global.statis.getNumbeMemberBannedInClub(clubID);
        int total_event =  Global.statis.getNumberEvent(clubID);
        int[] total_event_months = Global.statis.getNumberEventEachMonth(clubID);
        int balance =  Global.statis.getBalanceClub(clubID);
        int activity_point = Global.statis.getActivityPoint(clubID);
        int total_reports =  Global.statis.getTotalReport(clubID);
        int total_post = Global.statis.getTotalPost(clubID);
        
        Statis s = new Statis(total_mem, total_mem_out, total_mem_ban, total_event, total_event_months, balance, activity_point, total_reports, total_post);
        out.print(s.toString());
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
