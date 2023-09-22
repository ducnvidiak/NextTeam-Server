/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextteam.models.HomeTown;
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

    public List<PublicNotification> getAllPublicNotifications() {
        List<PublicNotification> publicNotifications = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM publicNotifications");
        try {
            while (rs.next()) {
                PublicNotification pn = new PublicNotification(rs.getInt(1), rs.getInt(2), rs.getNString(3), rs.getNString(4), rs.getDate(5), rs.getDate(6));
                publicNotifications.add(pn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublicNotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return publicNotifications;
    }

    public List<PublicNotification> get10PublicNotifications() {
        List<PublicNotification> publicNotifications = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT TOP 10 * FROM publicNotifications\n"
                + "ORDER BY updatedAt DESC;");
        try {
            while (rs.next()) {
                PublicNotification pn = new PublicNotification(rs.getInt(1), rs.getInt(2), rs.getNString(3), rs.getNString(4), rs.getDate(5), rs.getDate(6));
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
                ketQua = new PublicNotification(rs.getInt(1), rs.getInt(2), rs.getNString(3), rs.getNString(4), rs.getDate(5), rs.getDate(6));
            }
        } catch (Exception e) {
        }
        return ketQua;
    }

}
