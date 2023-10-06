/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models.response;

import nextteam.models.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author vnitd
 */
public class ClubResponse extends Club{
    private int numberOfMembers;
    private boolean isJoined;

    public ClubResponse() {
    }
    // all club information
    public ClubResponse(int id, String name, String subname, int categoryId, String description, String avatarUrl, String bannerUrl, int movementPoint, double balance, Date createdAt, Date updatedAt, boolean isActive, int numberOfMembers, boolean isJoined) {
        super(id, name, subname, categoryId, description, avatarUrl, bannerUrl, movementPoint, balance, createdAt, updatedAt, isActive);
        this.numberOfMembers = numberOfMembers;
        this.isJoined = isJoined;
    }
    
//    public ClubResponse(int id, String name, String subname, String avatarUrl, int movementPoint) {
////        super(id, name, subname, avatarUrl);
//        this.movementPoint = movementPoint;
//    }

//    public ClubResponse(String name, String subname, int categoryId, String description, String avatarUrl,
//            String bannerUrl, int movementPoint, double balance) {
//        this.id = id;
//        this.name = name;
//        this.subname = subname;
//        this.categoryId = categoryId;
//        this.description = description;
//        this.avatarUrl = avatarUrl;
//        this.bannerUrl = bannerUrl;
//        this.movementPoint = movementPoint;
//        this.balance = balance;
//    }
//
//    public ClubResponse(int id, String name, String subname, String avatarUrl, int movementPoint) {
//        this.id = id;
//        this.name = name;
//        this.subname = subname;
//        this.avatarUrl = avatarUrl;
//        this.movementPoint = movementPoint;
//    }
//    
//    
//
//    public ClubResponse(int id, String name, String subname) {
//        super(id, name, subname);
//    }
//
//    public ClubResponse(int aInt, String string, String string0, String string1, String string2, int aInt0, int aInt1, String string3, Timestamp timestamp, boolean aBoolean) {
//        this.id = aInt;
//        this.name = string;
//        this.subname = string0;
//        this.avatarUrl = string1;
//        this.bannerUrl = string2;
//        this.categoryId = aInt0;
//        this.numberOfMembers = aInt1;
//        this.description = string3;
//        this.createdAt = timestamp;
//        this.isJoined = aBoolean;
//    }
//
//    public int getNumberOfMembers() {
//        return numberOfMembers;
//    }
//
//    public boolean isIsJoined() {
//        return isJoined;
//    }
//
//    public void setNumberOfMembers(int numberOfMembers) {
//        this.numberOfMembers = numberOfMembers;
//    }
//
//    public void setIsJoined(boolean isJoined) {
//        this.isJoined = isJoined;
//    }

    

    
    

    
}
