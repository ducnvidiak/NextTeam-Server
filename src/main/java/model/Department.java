/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author bravee06
 */
public class Department implements Serializable {
    private int id;
    private int clubId;
    private String name;
    

    public Department() {
    }

    
    public Department(int id, int clubId, String name) {
        this.id = id;
        this.clubId = clubId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" + "id=" + id + ", clubId=" + clubId + ", name=" + name + '}';
    }
    
    
    
    
    
    
}
