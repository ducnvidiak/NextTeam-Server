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
public class Club {

    private int id;
    private String name;
    private String description;
    private String avatarUrl;
    private String bannerUrl;
    private int movementPoint;
    private double balance;
    private Date createdAt;
    private Date updatedAt;

    public Club(int id, String name, String description, String avatarUrl, String bannerUrl, int movementPoint, Date createdAt, Date updatedAt, int balance) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.bannerUrl = bannerUrl;
        this.movementPoint = movementPoint;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public int getMovementPoint() {
        return movementPoint;
    }

    public void setMovementPoint(int movementPoint) {
        this.movementPoint = movementPoint;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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