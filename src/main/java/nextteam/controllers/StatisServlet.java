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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.Club;
import nextteam.models.ClubCounter;

/**
 *
 * @author bravee06
 */
@WebServlet(name = "StatisServlet", urlPatterns = {"/api/statis"})
public class StatisServlet extends HttpServlet {

    private static class UpcomingEvents {

        public UpcomingEvents() {
        }
    }

    class Statis {

        private int total_mem;
        private int total_mem_out;
        private int total_mem_ban;
        private int total_event;
        private int[] total_event_months;
        private int[] total_enga_months;
        private int balance;
        private int activity_point;
        private int total_report;
        private int total_post;
        private String avartar_club;
        private String banner_club;
        private String name_club;
        private int category_club;
        private String create_date_club;
        private String manager_club_name;
        private String manager_club_ava;
        private String manager_club_username;
        private String subname;
        private List<ClubCounter> clubCounter;
        private int totalReports;
        private int manager_club_id;
        

        public Statis(int total_mem, int total_mem_out, int total_mem_ban, int total_event, int[] total_event_months, int[] total_enga_months, int balance, int activity_point, int total_report, int total_post, String avartar_club, String banner_club, String name_club, int category_club, String create_date_club, String manager_club_name, String manager_club_ava, String manager_club_username,String subname, List<ClubCounter> clubCounter, int totalReports, int manager_club_id) {
            this.total_mem = total_mem;
            this.total_mem_out = total_mem_out;
            this.total_mem_ban = total_mem_ban;
            this.total_event = total_event;
            this.total_event_months = total_event_months;
            this.total_enga_months = total_enga_months;
            this.balance = balance;
            this.activity_point = activity_point;
            this.total_report = total_report;
            this.total_post = total_post;
            this.avartar_club = avartar_club;
            this.banner_club = banner_club;
            this.name_club = name_club;
            this.category_club = category_club;
            this.create_date_club = create_date_club;
            this.manager_club_name = manager_club_name;
            this.manager_club_ava = manager_club_ava;
            this.manager_club_username = manager_club_username;
            this.subname = subname;
            this.clubCounter = clubCounter;
            this.totalReports = totalReports;
            this.manager_club_id = manager_club_id;
            
        }

        public Statis() {
        }

        public String toString() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        String clubID = request.getParameter("clubId");

        int total_mem = Global.statis.getNumberMemberInClub(clubID);
        System.out.println(total_mem);
        System.out.println(total_mem);
        int total_mem_out = Global.statis.getNumberMemberOutClub(clubID);
        int total_mem_ban = Global.statis.getNumbeMemberBannedInClub(clubID);
        int total_event = Global.statis.getNumberEvent(clubID);
        int[] total_event_months = Global.statis.getNumberEventEachMonth(clubID);
        int[] total_enga_months = Global.statis.getNumberEngaEachMonth(clubID);
        int balance = Global.statis.getBalanceClub(clubID);
        int activity_point = Global.statis.getActivityPoint(clubID);
        int total_report = Global.statis.getTotalReport(clubID);
        int total_post = Global.statis.getTotalPost(clubID);
        Club c = Global.clubDAO.getClubById(clubID);
        String avartar_club = c.getAvatarUrl();
        String banner_club = c.getBannerUrl();
        String name_club = c.getName();
        int category_club = c.getCategoryId();
        String create_date_club = c.getCreatedAt().toString();
//        String manager_club_name = Global.statis.getManager(clubID).getFirstname() + " " + Global.statis.getManager(clubID).getLastname();
        String manager_club_name = Global.statis.getManager(clubID).getFirstname() + " " + Global.statis.getManager(clubID).getLastname();

        String manager_club_ava = Global.statis.getManager(clubID).getAvatarURL();
        String manager_club_username = Global.statis.getManager(clubID).getUsername();
        String subname = c.getSubname();
        List<ClubCounter> club_counter = Global.statis.getClubData();
        int total_reports = Global.statis.getTotalReports();
        int manager_club_id = Global.statis.getManager(clubID).getId();
        Statis s = new Statis(total_mem, total_mem_out, total_mem_ban, total_event, total_event_months, total_enga_months, balance, activity_point, total_report, total_post, avartar_club, banner_club, name_club, category_club, create_date_club, manager_club_name, manager_club_ava, manager_club_username,subname, club_counter, total_reports,manager_club_id);
        out.print(s.toString());
        out.flush();

    }

}
