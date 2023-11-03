/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models.response;

import java.sql.Timestamp;

/**
 *
 * @author vnitd
 */
public class EventAttendanceResponse {

    int id;
    String name;
    Timestamp startTime;
    Timestamp endTime;

    public EventAttendanceResponse(int id, String name, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "{"
                + "    \"id\": \"" + getId() + "\","
                + "    \"name\":\"" + getName() + "\","
                + "    \"startTime\":\"" + getStartTime() + "\","
                + "    \"endTime\":\"" + getEndTime() + "\""
                + "}";
    }

}
