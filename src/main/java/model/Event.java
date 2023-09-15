/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class Event {
    private String id;
    private String name;
    private String description;
    private String hostBy;
    private String location;
    private String checkInCode;
    private String createdAt;
    private String updatedAt;

    public Event(String name, String description, String hostBy, String location, String checkInCode, String createdAt, String updatedAt) {
        this.name = name;
        this.description = description;
        this.hostBy = hostBy;
        this.location = location;
        this.checkInCode = checkInCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getHostBy() {
        return hostBy;
    }

    public void setHostBy(String hostBy) {
        this.hostBy = hostBy;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCheckInCode() {
        return checkInCode;
    }

    public void setCheckInCode(String checkInCode) {
        this.checkInCode = checkInCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    
}
