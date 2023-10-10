/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.sql.Date;
import java.sql.Timestamp;
import nextteam.Global;

/**
 *
 * @author vnitd
 */
public class PointsHistory {

    private int id;
    private int createdBy;
    private int receivedBy;
    private int clubId;
    private int amount;
    private String reason;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public PointsHistory(int createdBy, int receivedBy, int clubId, int amount, String reason) {
        this.createdBy = createdBy;
        this.receivedBy = receivedBy;
        this.clubId = clubId;
        this.amount = amount;
        this.reason = reason;
    }

    public PointsHistory(int id, int createdBy, int receivedBy, int clubId, int amount, String reason, Timestamp createdAt, Timestamp updatedAt) {
        this.createdBy = createdBy;
        this.receivedBy = receivedBy;
        this.clubId = clubId;
        this.amount = amount;
        this.reason = reason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(int receivedBy) {
        this.receivedBy = receivedBy;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return """
               {
                    "id": "%d",
                    "createdBy": "%s",
                    "receivedBy": "%d",
                    "receivedByName": "%s",
                    "clubId": "%d",
                    "amount": "%d",
                    "reason": "%s",
                    "createdAt": "%s",
                    "updatedAt": "%s"
               }""".formatted(
                getId(),
                Global.user.getUserById(getCreatedBy()).getFullname(),
                getReceivedBy(),
                Global.user.getUserById(getReceivedBy()).getFullname(),
                getClubId(),
                getAmount(),
                getReason(),
                getCreatedAt(),
                getUpdatedAt()
        );
    }

}
