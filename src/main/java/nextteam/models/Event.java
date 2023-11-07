/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author vnitd
 */
public class Event {

    private int id;
    private String name;
    private String description;
    private int registeredBy;
    private int locationId;
    private String checkInCode;
    private Timestamp startTime;
    private Timestamp endTime;
    private String type;
    private String planUrl;
    private String bannerUrl;
    private String isApproved;
    private String response;
    private int clubId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Event(int id, String name, String description, int registeredBy, int locationId, String checkInCode, Timestamp startTime, Timestamp endTime, String type, String planUrl, String bannerUrl, String isApproved, String response, int clubId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.registeredBy = registeredBy;
        this.locationId = locationId;
        this.checkInCode = checkInCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.planUrl = planUrl;
        this.bannerUrl = bannerUrl;
        this.isApproved = isApproved;
        this.response = response;
        this.clubId = clubId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Event(int id, String name, String type, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String isApproved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bannerUrl = bannerUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isApproved = isApproved;
        this.type = type;
    }
    
    public Event(int id, String name, String type, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String isApproved, String planUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bannerUrl = bannerUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isApproved = isApproved;
        this.type = type;
        this.planUrl = planUrl;
    }
    
    public Event(int id, String name, String description, String bannerUrl, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bannerUrl = bannerUrl;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public Event(int id, String name, String type, String description, String bannerUrl, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bannerUrl = bannerUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }

    public Event(int id, String name, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String planUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.planUrl = planUrl;
        this.bannerUrl = bannerUrl;
    }
    public Event(int id, String name, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String isApproved, String planUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.planUrl = planUrl;
        this.isApproved = isApproved;
        this.bannerUrl = bannerUrl;
    }
    
    public Event(int id, String name, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String isApproved, boolean a) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isApproved = isApproved;
        this.bannerUrl = bannerUrl;
    }
    
    
//    public Event(int id, String name, String type String description, String bannerUrl, Timestamp startTime, Timestamp endTime) {
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.description = description;
//        this.bannerUrl = bannerUrl;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }

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

    public int getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(int registeredBy) {
        this.registeredBy = registeredBy;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getCheckInCode() {
        return checkInCode;
    }

    public void setCheckInCode(String checkInCode) {
        this.checkInCode = checkInCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlanUrl() {
        return planUrl;
    }

    public void setPlanUrl(String planUrl) {
        this.planUrl = planUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    

}
