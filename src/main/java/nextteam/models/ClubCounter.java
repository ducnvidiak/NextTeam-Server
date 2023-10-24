/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

/**
 *
 * @author bravee06
 */
public class ClubCounter {
    private String name;
    private String upcomingEventName;
    private String upcomingEventTime;
    private int eventCount;
    private int memberCount;
    private double balance;
    private int activiyPoint;
    private int planCount;
    
    

    public ClubCounter(String name, int eventCount, int memberCount, double balance, int activiyPoint, String upcomingEventName, String upcomingEventTime,int planCount) {
        
        this.name = name;
        this.eventCount = eventCount;
        this.memberCount = memberCount;
        this.balance = balance;
        this.activiyPoint = activiyPoint;
        this.upcomingEventName = upcomingEventName;
        this.upcomingEventTime = upcomingEventTime;
        this.planCount = planCount;
    }

    public ClubCounter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getActiviyPoint() {
        return activiyPoint;
    }

    public void setActiviyPoint(int activiyPoint) {
        this.activiyPoint = activiyPoint;
    }

    public String getUpcomingEventName() {
        return upcomingEventName;
    }

    public void setUpcomingEventName(String upcomingEventName) {
        this.upcomingEventName = upcomingEventName;
    }

    public String getUpcomingEventTime() {
        return upcomingEventTime;
    }

    public void setUpcomingEventTime(String upcomingEventTime) {
        this.upcomingEventTime = upcomingEventTime;
    }

    public int getPlanCount() {
        return planCount;
    }

    public void setPlanCount(int planCount) {
        this.planCount = planCount;
    }

    
    
    
    
    
}
