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
public class EventVotation {

    private int id;
    private int eventId;
    private int userId;
    private boolean isAgreed;
    private Date createdAt;
    private Date updatedAt;

    public EventVotation(int id, int eventId, int userId, boolean isAgreed, Date createdAt, Date updatedAt) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.isAgreed = isAgreed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isIsAgreed() {
        return isAgreed;
    }

    public void setIsAgreed(boolean isAgreed) {
        this.isAgreed = isAgreed;
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
