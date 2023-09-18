/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.util.Date;

/**
 *
 * @author vnitd
 */
public class PublicNotificationView {

    private int publicNotificationId;
    private int hasSeenBy;
    private Date seenTime;

    public PublicNotificationView(int publicNotificationId, int hasSeenBy, Date seenTime) {
        this.publicNotificationId = publicNotificationId;
        this.hasSeenBy = hasSeenBy;
        this.seenTime = seenTime;
    }

    public int getPublicNotificationId() {
        return publicNotificationId;
    }

    public void setPublicNotificationId(int publicNotificationId) {
        this.publicNotificationId = publicNotificationId;
    }

    public int getHasSeenBy() {
        return hasSeenBy;
    }

    public void setHasSeenBy(int hasSeenBy) {
        this.hasSeenBy = hasSeenBy;
    }

    public Date getSeenTime() {
        return seenTime;
    }

    public void setMarkTime(Date seenTime) {
        this.seenTime = seenTime;
    }
}
