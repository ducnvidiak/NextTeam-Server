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

    public EventResponse(int id, String name, String description, String bannerUrl, Timestamp startTime, Timestamp endTime, String locationName, String clubSubname, String clubAvatarUrl, boolean isRegistered) {
        super(id, name, description, bannerUrl, startTime, endTime);
        this.locationName = locationName;
        this.clubSubname = clubSubname;
        this.clubAvatarUrl = clubAvatarUrl;
        this.isRegistered = isRegistered;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

}
