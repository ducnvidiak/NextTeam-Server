/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextteam.models.HomeTown;
import nextteam.models.PrivateNotification;
import nextteam.models.PublicNotification;
import nextteam.models.User;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class NotificationDAO extends SQLDatabase {

    public NotificationDAO(Connection connection) {
        super(connection);
    }

    public class Notification {

        private int id;
        private String title;
        private String content;
        private int clubId;
        private Date createdAt;
        private Date updatedAt;
        private boolean hasSeen;
        private String type;

        public Notification(int id, String title, String content, int clubId, Date createdAt, Date updatedAt, boolean hasSeen, String type) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.clubId = clubId;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.hasSeen = hasSeen;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isHasSeen() {
            return hasSeen;
        }

        public void setHasSeen(boolean hasSeen) {
            this.hasSeen = hasSeen;
        }

    }

    public List<Notification> getListNotification(String clubId, String userId) {
        List<Notification> notifications = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT publicNotifications.id, title, content, clubId, createdAt, updatedAt, \n"
                + "CASE WHEN publicNotificationViews.hasSeenBy IS NOT NULL THEN 1 ELSE 0\n"
                + "END AS hasSeen, 'public' AS type\n"
                + "FROM publicNotifications \n"
                + "LEFT JOIN publicNotificationViews\n"
                + "ON publicNotifications.id = publicNotificationViews.publicNotificationId AND publicNotificationViews.hasSeenBy = ?\n"
                + "WHERE publicNotifications.clubId =?\n"
                + "UNION ALL\n"
                + "SELECT  id, title, content, clubId, createdAt, updatedAt, hasSeen, 'private' AS type\n"
                + "FROM privateNotifications\n"
                + "WHERE privateNotifications.sendTo = ?\n"
                + "ORDER BY updatedAt DESC;", userId,clubId, userId);
        try {
            while (rs.next()) {
                Notification pn = new Notification(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getTimestamp(5), rs.getTimestamp(6), rs.getBoolean(7), rs.getString(8));
                notifications.add(pn);
            }
        } catch (SQLException ex) {
            System.out.println(rs);
            Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notifications;

    }

    public List<Notification> getList10Notification(String clubId, String userId) {
        List<Notification> notifications = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT TOP 10 publicNotifications.id, title, content, clubId, createdAt, updatedAt, \n"
                + "CASE WHEN publicNotificationViews.hasSeenBy IS NOT NULL THEN 1 ELSE 0\n"
                + "END AS hasSeen, 'public' AS type\n"
                + "FROM publicNotifications \n"
                + "LEFT JOIN publicNotificationViews\n"
                + "ON publicNotifications.id = publicNotificationViews.publicNotificationId AND publicNotificationViews.hasSeenBy = ?\n"
                + "WHERE publicNotifications.clubId =?\n"
                + "UNION ALL\n"
                + "SELECT TOP 10 id, title, content, clubId, createdAt, updatedAt, hasSeen, 'private' AS type\n"
                + "FROM privateNotifications\n"
                + "WHERE privateNotifications.sendTo = ?\n"
                + "ORDER BY updatedAt DESC;", userId,clubId, userId);
        try {
            while (rs.next()) {
                Notification pn = new Notification(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getTimestamp(5), rs.getTimestamp(6), rs.getBoolean(7), rs.getString(8));
                notifications.add(pn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notifications;

    }

    public List<Notification> searchNotification(String clubId, String userId, String search) {
        List<Notification> notifications = new ArrayList<>();
        System.out.println("Tìm kiếm: " + search);
        ResultSet rs = executeQueryPreparedStatement("SELECT TOP 10 id, title, content, clubId, createdAt, updatedAt,'1' as hasSeen, 'public' AS type\n"
                + "FROM publicNotifications\n"
                + "WHERE publicNotifications.clubId =? AND title LIKE ?\n"
                + "UNION ALL\n"
                + "SELECT TOP 10 id, title, content, clubId, createdAt, updatedAt, hasSeen, 'private' AS type\n"
                + "FROM privateNotifications\n"
                + "WHERE privateNotifications.sendTo = ? AND title LIKE ?\n"
                + "ORDER BY createdAt DESC;", clubId, "%" + search + "%", userId, "%" + search + "%");
        try {
            while (rs.next()) {
                Notification pn = new Notification(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getTimestamp(5), rs.getTimestamp(6), rs.getBoolean(7), rs.getString(8));
                notifications.add(pn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notifications;

    }

}
