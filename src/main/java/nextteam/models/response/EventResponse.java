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
public class EventResponse extends Event {

    private String locationName;
    private String clubSubname;
    private String clubAvatarUrl;
    private boolean isRegistered;
    private float avgRating;
    private boolean isFeedback;

    public EventResponse(int id, String name, String type, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String isApproved, String locationName, String clubSubname, String clubAvatarUrl, boolean isRegistered) {  // 12 params
        super(id, name, type, description, bannerUrl, startTime, endTime, isApproved);
        this.locationName = locationName;
        this.clubSubname = clubSubname;
        this.clubAvatarUrl = clubAvatarUrl;
        this.isRegistered = isRegistered;
    }

    public EventResponse(int id, String name, String type, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String isApproved, String locationName, String clubSubname, String clubAvatarUrl) {   // 11 params
        super(id, name, type, description, bannerUrl, startTime, endTime, isApproved);
        this.locationName = locationName;
        this.clubSubname = clubSubname;
        this.clubAvatarUrl = clubAvatarUrl;
    }

    public EventResponse(int id, String name, String type, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String isApproved, String planUrl, String locationName, String clubSubname, String clubAvatarUrl) { // 12 params
        super(id, name, type, description, bannerUrl, startTime, endTime, isApproved, planUrl);
        this.locationName = locationName;
        this.clubSubname = clubSubname;
        this.clubAvatarUrl = clubAvatarUrl;
    }

    public EventResponse(int id, String name, String type, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String isApproved, String locationName, String clubSubname, String clubAvatarUrl, boolean isRegistered, float avgRating, boolean isFeedback) {  //14 params
        super(id, name, type, description, bannerUrl, startTime, endTime, isApproved);
        this.locationName = locationName;
        this.clubSubname = clubSubname;
        this.clubAvatarUrl = clubAvatarUrl;
        this.isRegistered = isRegistered;
        this.avgRating = avgRating;
        this.isFeedback = isFeedback;
    }

    public EventResponse(int id, String name, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String locationName, String clubSubname, String clubAvatarUrl, boolean isRegistered, boolean isFeedback, float avgRating) { // 12 params
        super(id, name, description, bannerUrl, startTime, endTime);
        this.locationName = locationName;
        this.clubSubname = clubSubname;
        this.clubAvatarUrl = clubAvatarUrl;
        this.isRegistered = isRegistered;
        this.avgRating = avgRating;
        this.isFeedback = isFeedback;
    }

    public EventResponse(int id, String name, String type, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String locationName, String clubSubname, String clubAvatarUrl, boolean isRegistered, boolean isFeedback, float avgRating) { // 13 params
        super(id, name, type, description, bannerUrl, startTime, endTime);
        this.locationName = locationName;
        this.clubSubname = clubSubname;
        this.clubAvatarUrl = clubAvatarUrl;
        this.isRegistered = isRegistered;
        this.avgRating = avgRating;
        this.isFeedback = isFeedback;
    }

    public EventResponse(int id, String name, String type, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String locationName) {  // 8 params
        super(id, name, type, description, bannerUrl, startTime, endTime);
        this.locationName = locationName;

    }
    
    public EventResponse(int id, String name, String type, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String isApproved, String locationName, String subname) { // 10 params
        super(id, name, type, description, bannerUrl, startTime, endTime, isApproved);
        this.locationName = locationName;
        this.clubSubname = subname;
    }
    
    public EventResponse(int id, String name, String type, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String isApproved, String locationName) { // 9 params
        super(id, name, type, description, bannerUrl, startTime, endTime, isApproved);
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public boolean isIsFeedback() {
        return isFeedback;
    }

    public void setIsFeedback(boolean isFeedback) {
        this.isFeedback = isFeedback;
    }
    
    public String getClubSubname() {
        return clubSubname;
    }

    public void setClubSubname(String clubSubname) {
        this.clubSubname = clubSubname;
    }

    public String getClubAvatarUrl() {
        return clubAvatarUrl;
    }

    public void setClubAvatarUrl(String clubAvatarUrl) {
        this.clubAvatarUrl = clubAvatarUrl;
    }

    public boolean isIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    @Override
    public String toString() {
        return "{"
                + "    \"id\": \"" + getId() + "\","
                + "    \"name\":\"" + getName() + "\","
                + "    \"type\":\"" + getType() + "\","
                + "    \"description\":\"" + getDescription() + "\","
                + "    \"bannerUrl\":\"" + getBannerUrl() + "\","
                + "    \"startTime\":\"" + getStartTime() + "\","
                + "    \"endTime\":\"" + getEndTime() + "\","
                + "    \"isApproved\":\"" + getIsApproved() + "\","
                + "    \"locationName\":\"" + locationName + "\","
                + "    \"clubSubname\":\"" + clubSubname + "\","
                + "    \"clubAvatarUrl\":\"" + clubAvatarUrl + "\","
                + "    \"isRegistered\":\"" + isRegistered + "\""
                + "}";
    }
}
