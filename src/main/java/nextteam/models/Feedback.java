/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;

/**
 *
 * @author vnitd
 */
public class Feedback {

    private int id;
    private int userId;
    private int eventId;
    private int point;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String firstname;
    private String lastname;
    private String email;
    private String username;

    public Feedback() {
    }

    public Feedback(int userId, int eventId, int point, String content) {
        this.userId = userId;
        this.eventId = eventId;
        this.point = point;
        this.content = content;
    }

    public Feedback(String firstname, String lastname, String username, String email, int point, String content) {
        this.point = point;
        this.content = content;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

}
