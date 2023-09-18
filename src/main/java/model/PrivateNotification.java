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
public class PrivateNotification {
    private int id;
    private int clubId;
    private int sendTo;
    private boolean hasSeen;
    private Date markTime;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;

    public PrivateNotification(int id,int clubId, int sendTo, boolean hasSeen, Date markTime, String title, String content, Date createdAt, Date updatedAt) {
        this.clubId = clubId;
        this.sendTo = sendTo;
        this.hasSeen = hasSeen;
        this.markTime = markTime;
        this.title = title;
        this.content = content;
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

    public int getSendTo() {
        return sendTo;
    }

    public void setSendTo(int sendTo) {
        this.sendTo = sendTo;
    }

    public boolean isHasSeen() {
        return hasSeen;
    }

    public void setHasSeen(boolean hasSeen) {
        this.hasSeen = hasSeen;
    }

    public Date getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Date markTime) {
        this.markTime = markTime;
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
