/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author admin
 */
public class Plan {
    private int id;
    private int clubId;
    private String title;
    private String content;
    private String attachmentUrl;
    private String response;
    private boolean isApprovedByAdmin;
    private Date createdAt;
    private Date updatedAt;

    public Plan(int clubId, String title, String content, String attachmentUrl, String response, boolean isApprovedByAdmin, Date createdAt, Date updatedAt) {
        this.clubId = clubId;
        this.title = title;
        this.content = content;
        this.attachmentUrl = attachmentUrl;
        this.response = response;
        this.isApprovedByAdmin = isApprovedByAdmin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isIsApprovedByAdmin() {
        return isApprovedByAdmin;
    }

    public void setIsApprovedByAdmin(boolean isApprovedByAdmin) {
        this.isApprovedByAdmin = isApprovedByAdmin;
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
