/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.util.Date;

/**
 *
 * @author vnitd
 */
public class Plan {

    private int id;
    private int clubId;
    private String title;
    private String content;
    private String response;
    private String isApproved;
    private Date createdAt;
    private Date updatedAt;

    public Plan() {
    }

    public Plan(int clubId, String title, String content) {
        this.clubId = clubId;
        this.title = title;
        this.content = content;
    }

    public Plan(String title, String content, String response, String isApproved, Date updatedAt) {
        this.title = title;
        this.content = content;
        this.response = response;
        this.isApproved = isApproved;
        this.updatedAt = updatedAt;
    }
    
    public Plan(int id, String title, String content,String isApproved, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isApproved = isApproved;
        this.updatedAt = updatedAt;
    }
    
    public Plan(int id, int clubId, String title, String content, String response, String isApproved, Date createdAt, Date updatedAt) {
        this.clubId = clubId;
        this.title = title;
        this.content = content;
        this.response = response;
        this.isApproved = isApproved;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
