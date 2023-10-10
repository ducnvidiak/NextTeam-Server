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
import nextteam.models.PrivateNotification;
import nextteam.models.PublicNotification;
import nextteam.models.User;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class PrivateNotificationDAO extends SQLDatabase {

    public PrivateNotificationDAO(Connection connection) {
        super(connection);
    }

    public class PrivateNotificationDetail {

        private int id;
        private int clubId;
        private int sendTo;
        private boolean hasSeen;
        private Date seenTime;
        private String title;
        private String content;
        private Date createdAt;
        private Date updatedAt;
        private String firstname;
        private String lastname;

        public PrivateNotificationDetail(int id, int clubId, int sendTo, boolean hasSeen, Date seenTime, String title, String content, Date createdAt, Date updatedAt, String firstname, String lastname) {
            this.id = id;
            this.clubId = clubId;
            this.sendTo = sendTo;
            this.hasSeen = hasSeen;
            this.seenTime = seenTime;
            this.title = title;
            this.content = content;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.firstname = firstname;
            this.lastname = lastname;
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

        public int getSendTo() {
            return sendTo;
        }

        public void setSendTo(int sendTo) {
            this.sendTo = sendTo;
        }

        public boolean isHasSeen() {
            return hasSeen;
        }

        public void setHasSeen(boolean hasSeen) {
            this.hasSeen = hasSeen;
        }

        public Date getSeenTime() {
            return seenTime;
        }

        public void setSeenTime(Date seenTime) {
            this.seenTime = seenTime;
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

        
        

    }

    public List<PrivateNotification> getAllPrivateNotifications(String t) {
        List<PrivateNotification> privateNotification = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM privateNotifications WHERE sendTo = ? ORDER BY updatedAt DESC", t);
        try {
            while (rs.next()) {
                PrivateNotification pn = new PrivateNotification(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4), rs.getTimestamp(5), rs.getNString(6), rs.getNString(7), rs.getTimestamp(8), rs.getTimestamp(9));
                privateNotification.add(pn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrivateNotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return privateNotification;
    }
    
    public List<PrivateNotificationDetail> getAllPrivateNotificationDetails(String t) {
        List<PrivateNotificationDetail> privateNotificationDetails = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT privateNotifications.* , users.firstname, users.lastname FROM privateNotifications INNER JOIN users ON privateNotifications.sendTo = users.id WHERE privateNotifications.clubId = ?  ORDER BY privateNotifications.updatedAt DESC", t);
        try {
            while (rs.next()) {
                PrivateNotificationDetail pn = new PrivateNotificationDetail(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4), rs.getTimestamp(5), rs.getString(6), rs.getString(7), rs.getTimestamp(8), rs.getTimestamp(9), rs.getString(10),rs.getString(11));
                privateNotificationDetails.add(pn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrivateNotificationDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return privateNotificationDetails;
    }

    public List<PrivateNotification> get10PrivateNotifications(String t) {
        List<PrivateNotification> privateNotifications = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT TOP 10 * FROM privateNotifications WHERE sendTo = ? \n"
                + "ORDER BY updatedAt DESC;", t);
        try {
            while (rs.next()) {
                PrivateNotification pn = new PrivateNotification(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4), rs.getTimestamp(5), rs.getNString(6), rs.getNString(7), rs.getTimestamp(8), rs.getTimestamp(9));
                privateNotifications.add(pn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrivateNotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return privateNotifications;
    }

    public PrivateNotification getNotificationByIdString(String t) {
        PrivateNotification ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM privateNotifications WHERE id=?", t);
            if (rs.next()) {
                ketQua = new PrivateNotification(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4), rs.getTimestamp(5), rs.getNString(6), rs.getNString(7), rs.getTimestamp(8), rs.getTimestamp(9));
            }
        } catch (Exception e) {
        }
        return ketQua;
    }

//    public List<PublicNotification> getNotificationByNameString(String t, String clubId) {
//        List<PublicNotification> publicNotifications = new ArrayList<>();
//        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM publicNotifications WHERE title LIKE ? AND clubId=?", "%" + t + "%", clubId);
//        try {
//            while (rs.next()) {
//                PublicNotification pn = new PublicNotification(rs.getInt(1), rs.getInt(2), rs.getNString(3), rs.getNString(4), rs.getTimestamp(5), rs.getTimestamp(6));
//                publicNotifications.add(pn);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(PrivateNotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return publicNotifications;
//    }
    public int addNotification(final PrivateNotification t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "INSERT INTO privateNotifications (clubId, sendTo, title, content)  VALUES (?,?,?,?)",
                t.getClubId() == 0 ? null : t.getClubId(),
                t.getSendTo(),
                t.getTitle(),
                t.getContent()
        );
        return ketQua;
    }

    public int updateSeen(String t) {
        int ketQua = 0;
        Date seenTime = new Date();
        ketQua = executeUpdatePreparedStatement(
                "UPDATE privateNotifications  SET  hasSeen=1, seenTime=? WHERE id=?",
                seenTime,
                t
        );
        return ketQua;

    }
    
    public int deletePrivateNotification(String id) {
        int rs = 0;
        rs = executeUpdatePreparedStatement("DELETE from privateNotifications  WHERE id=?", id);
        return rs;
    }
    
    public int update(PrivateNotification t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE privateNotifications SET  title=?, content=?, sendTo=? WHERE id=?",
                t.getTitle(),
                t.getContent(),
                t.getSendTo(),
                t.getId()
        );
        return ketQua;

    }
    
    public List<PrivateNotificationDetail> getAllPrivateNotificationsFromAdmin() {
        List<PrivateNotificationDetail> privateNotificationDetails = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT privateNotifications.* , users.firstname, users.lastname FROM privateNotifications INNER JOIN users ON privateNotifications.sendTo = users.id WHERE privateNotifications.clubId IS NULL ORDER BY privateNotifications.updatedAt DESC");
        try {
            while (rs.next()) {
               PrivateNotificationDetail pn = new PrivateNotificationDetail(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4), rs.getTimestamp(5), rs.getString(6), rs.getString(7), rs.getTimestamp(8), rs.getTimestamp(9), rs.getString(10),rs.getString(11));
                privateNotificationDetails.add(pn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublicNotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return privateNotificationDetails;
    }

}
