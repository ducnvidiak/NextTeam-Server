/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextteam.models.PublicNotification;
import nextteam.models.User;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class PublicNotificationDAO extends SQLDatabase {

    public PublicNotificationDAO(Connection connection) {
        super(connection);
    }

    

    public List<PublicNotification> getAllPublicNotifications(String t) {
        List<PublicNotification> publicNotifications = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM publicNotifications WHERE clubId = ? ORDER BY createdAt DESC", t);
        try {
            while (rs.next()) {
                PublicNotification pn = new PublicNotification(rs.getInt(1), rs.getInt(2), rs.getNString(3), rs.getNString(4), rs.getTimestamp(5), rs.getTimestamp(6));
                publicNotifications.add(pn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublicNotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return publicNotifications;
    }

    public List<PublicNotification> get10PublicNotifications(String t) {
        List<PublicNotification> publicNotifications = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT TOP 10 * FROM publicNotifications WHERE clubId = ?\n"
                + "ORDER BY createdAt DESC;", t);
        try {
            while (rs.next()) {
                PublicNotification pn = new PublicNotification(rs.getInt(1), rs.getInt(2), rs.getNString(3), rs.getNString(4), rs.getTimestamp(5), rs.getTimestamp(6));
                publicNotifications.add(pn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublicNotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return publicNotifications;
    }

    public PublicNotification getNotificationByIdString(String t) {
        PublicNotification ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM publicNotifications WHERE id=?", t);
            if (rs.next()) {
                ketQua = new PublicNotification(rs.getInt(1), rs.getInt(2), rs.getNString(3), rs.getNString(4), rs.getTimestamp(5), rs.getTimestamp(6));
            }
        } catch (Exception e) {
        }
        return ketQua;
    }

    public List<PublicNotification> getNotificationByNameString(String t, String clubId) {
        List<PublicNotification> publicNotifications = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM publicNotifications WHERE title LIKE ? AND clubId=? ORDER BY updatedAt DESC", "%" + t + "%", clubId);
        try {
            while (rs.next()) {
                PublicNotification pn = new PublicNotification(rs.getInt(1), rs.getInt(2), rs.getNString(3), rs.getNString(4), rs.getTimestamp(5), rs.getTimestamp(6));
                publicNotifications.add(pn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublicNotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return publicNotifications;
    }

    public int addNotification(final PublicNotification t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "INSERT INTO publicNotifications (clubId, title, content)  VALUES (?,?,?)",
                t.getClubId() == 0 ? null : t.getClubId(), 
                t.getTitle(),
                t.getContent()
        );
        return ketQua;
    }

    public int deletePublicNotification(String id) {
        int rs = 0;
        rs = executeUpdatePreparedStatement("DELETE from publicNotifications  WHERE id=?", id);
        return rs;
    }

    public int update(PublicNotification t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE publicNotifications  SET  title=?, content=? WHERE id=?",
                t.getTitle(),
                t.getContent(),
                t.getId()
        );
        return ketQua;

    }
    
     public int updateView( String id, String userId) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "INSERT INTO publicNotificationViews (publicNotificationId, hasSeenBy)  VALUES (?,?)",
                id,
                userId
        );
        return ketQua;
    }
     
     public List<PublicNotification> getAllWideNotifications() {
        List<PublicNotification> publicNotifications = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM publicNotifications WHERE clubId IS NULL ORDER BY createdAt DESC");
        try {
            while (rs.next()) {
                PublicNotification pn = new PublicNotification(rs.getInt(1), rs.getInt(2), rs.getNString(3), rs.getNString(4), rs.getTimestamp(5), rs.getTimestamp(6));
                publicNotifications.add(pn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublicNotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return publicNotifications;
    }

    

}
