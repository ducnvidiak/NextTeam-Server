/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.util.Date;

/**
 *
 * @author admin
 */
public class ProposalDetail extends Proposal{
    
    private String username;
    private String firstname;
    private String lastname;
    private String clubsubname;

    public ProposalDetail() {
    }

    public ProposalDetail(int id, String title, String content, String isApproved, Date updatedAt) {
        super(id, title, content, isApproved, updatedAt);
    }

    public ProposalDetail(int clubId, String title, String content, int sendBy, String isApproved) {
        super(clubId, title, content, sendBy, isApproved);
    }

    public ProposalDetail(int id, int clubId, String title, String content, int sendBy, String isApproved, Date createdAt, Date updatedAt) {
        super(id, clubId, title, content, sendBy, isApproved, createdAt, updatedAt);
    }
    
    public ProposalDetail(int id, int clubId, String title, String content, int sendBy, String isApproved, Date createdAt, Date updatedAt, String clubsubname) {
        super(id, clubId, title, content, sendBy, isApproved, createdAt, updatedAt);
        this.clubsubname = clubsubname;
    }
    
    public ProposalDetail(int id, int clubId, String title, String content, int sendBy, String isApproved, Date createdAt, Date updatedAt, String username, String firstname, String lastname, String clubsubname) {
        super(id, clubId, title, content, sendBy, isApproved, createdAt, updatedAt);
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.clubsubname = clubsubname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getClubsubname() {
        return clubsubname;
    }

    public void setClubsubname(String clubsubname) {
        this.clubsubname = clubsubname;
    }
 
}
