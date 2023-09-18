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
public class PublicNotificationsView {
    private int id;
    private int publicNotificationId;
    private int hasSeenBy;
    private Date markTime;

    public PublicNotificationsView(int id,int publicNotificationId, int hasSeenBy, Date markTime) {
        this.publicNotificationId = publicNotificationId;
        this.hasSeenBy = hasSeenBy;
        this.markTime = markTime;
         this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicNotificationId() {
        return publicNotificationId;
    }

    public void setPublicNotificationId(int publicNotificationId) {
        this.publicNotificationId = publicNotificationId;
    }

    public int getHasSeenBy() {
        return hasSeenBy;
    }

    public void setHasSeenBy(int hasSeenBy) {
        this.hasSeenBy = hasSeenBy;
    }

    public Date getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Date markTime) {
        this.markTime = markTime;
    }
    
    
}
