/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.util.Date;

/**
 *
 * @author admin
 */
public class PlanDetail extends Plan{
    private String clubname;
    
    public PlanDetail() {
    }

    public PlanDetail(int clubId, String title, String content) {
        super(clubId, title, content);
    }

    public PlanDetail(int id, String title, String content, String isApproved, Date updatedAt) {
        super(id, title, content, isApproved, updatedAt);
    }

    public PlanDetail(String title, String content, String response, String isApproved, Date updatedAt) {
        super(title, content, response, isApproved, updatedAt);
    }

    public PlanDetail(int id, int clubId, String title, String content, String response, String isApproved, Date createdAt, Date updatedAt) {
        super(id, clubId, title, content, response, isApproved, createdAt, updatedAt);
    }
    
    public PlanDetail(int id, int clubId, String title, String content, String response, String isApproved, Date createdAt, Date updatedAt, String name) {
        super(id, clubId, title, content, response, isApproved, createdAt, updatedAt);
        this.clubname = name;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }
    
    
}
