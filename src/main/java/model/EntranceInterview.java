/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author admin
 */
public class EntranceInterview {
    private int id;
    private Date startTime;
    private Date endTime;
    private int engagementId;
    private String comment;
    private boolean isApproved;
    private Date createdAt;
    private Date updatedAt;

    public EntranceInterview(int id,Date startTime, Date endTime, int engagementId, String comment, boolean isApproved, Date createdAt, Date updatedAt) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.engagementId = engagementId;
        this.comment = comment;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(int engagementId) {
        this.engagementId = engagementId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
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

    public boolean isApproved() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
