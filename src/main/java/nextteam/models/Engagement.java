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
public class Engagement {

    private int id;
    private int userId;
    private int departmentId;
    private int clubId;
    private int roleId;
    private String cvUrl;
    private Date createdAt;
    private Date updatedAt;

    public Engagement(int id, int userId, int departmentId, int clubId, int roleId, String cvUrl, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userId = userId;
        this.departmentId = departmentId;
        this.clubId = clubId;
        this.roleId = roleId;
        this.cvUrl = cvUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Engagement(int userId, int departmentId, int clubId, String cvUrl) {
        this.userId = userId;
        this.departmentId = departmentId;
        this.clubId = clubId;
        this.cvUrl = cvUrl;
    }
    
    

    public Engagement() {
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

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
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
