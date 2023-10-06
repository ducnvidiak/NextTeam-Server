/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.util.Date;
import nextteam.Global;
import nextteam.utils.database.ClubCategoriesDAO;

/**
 *
 * @author vnitd
 */
public class Club {

    private int id;
    private String name;
    private String subname;
    private int categoryId;
    private String description;
    private String avatarUrl;
    private String bannerUrl;
    private int movementPoint;
    private double balance;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;

    public Club() {
    }

    public Club(int id, String name, String subname, int categoryId, String description, String avatarUrl, String bannerUrl, int movementPoint, double balance, Date createdAt, Date updatedAt, boolean isActive) {
        this.id = id;
        this.name = name;
        this.subname = subname;
        this.categoryId = categoryId;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.bannerUrl = bannerUrl;
        this.movementPoint = movementPoint;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }

   

    public Club(String name, String subname, int categoryId, String description, String avatarUrl,
            String bannerUrl, int movementPoint, double balance) {
        this.id = id;
        this.name = name;
        this.subname = subname;
        this.categoryId = categoryId;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.bannerUrl = bannerUrl;
        this.movementPoint = movementPoint;
        this.balance = balance;
    }

    public Club(int id, String name, String subname) {
        this.id = id;
        this.name = name;
        this.subname = subname;
    }
    public Club(String name, String subname, int categoryId, String description, String avatarUrl,
            String bannerUrl, int movementPoint, double balance, boolean isActive) {
        this.id = id;
        this.name = name;
        this.subname = subname;
        this.categoryId = categoryId;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.bannerUrl = bannerUrl;
        this.movementPoint = movementPoint;
        this.balance = balance;
        this.isActive = isActive;
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

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public String getCategoryName(){
         ClubCategories c = new ClubCategoriesDAO(Global.generateConnection()).getClubsCategoriesByID(this.categoryId);
         return c.getName();
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\": \"" + id + "\","
                + "\"name\":\"" + name + "\","
                + "\"subname\":\"" + subname + "\","
                + "\"categoryId\":\"" + categoryId + "\","
                + "\"categoryName\":\"" + this.getCategoryName() + "\","
                + "\"description\":\"" + description + "\","
                + "\"avatarUrl\":\"" + avatarUrl + "\","
                + "\"bannerUrl\":\"" + bannerUrl + "\","
                + "\"movementPoint\":\"" + movementPoint + "\","
                + "\"balance\":\"" + balance + "\","
                + "\"createdAt\":\"" + createdAt + "\","
                + "\"updatedAt\":\"" + updatedAt + "\","
                + "\"isActive\":\"" + isActive + "\""
                + "}";

    }

}
