/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.PublicNotification;

public class PublicNotificationDAO extends DBContext {

    public List<PublicNotification> getAllPublicNotifications() {
        List<PublicNotification> publicNotifications = new ArrayList<>();
        PublicNotification publicNotification = null;

        String sql = "SELECT * FROM publicNotifications";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                publicNotification = new PublicNotification(
                    rs.getInt("id"),
                    rs.getInt("clubId"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getTimestamp("createdAt"),
                    rs.getTimestamp("updatedAt")
                );

                publicNotifications.add(publicNotification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publicNotifications;
    }

    public PublicNotification getPublicNotificationById(int id) {
        PublicNotification publicNotification = null;
        String sql = "SELECT * FROM publicNotifications WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                publicNotification = new PublicNotification(
                    rs.getInt("id"),
                    rs.getInt("clubId"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getTimestamp("createdAt"),
                    rs.getTimestamp("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publicNotification;
    }

    public boolean addPublicNotification(PublicNotification publicNotification) {
        String sql = "INSERT INTO publicNotifications (clubId, title, content) VALUES (?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, publicNotification.getClubId());
            st.setString(2, publicNotification.getTitle());
            st.setString(3, publicNotification.getContent());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePublicNotification(PublicNotification publicNotification) {
        String sql = "UPDATE publicNotifications SET clubId = ?, title = ?, content = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, publicNotification.getClubId());
            st.setString(2, publicNotification.getTitle());
            st.setString(3, publicNotification.getContent());
            st.setInt(4, publicNotification.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePublicNotification(int publicNotificationId) {
        String sql = "DELETE FROM publicNotifications WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, publicNotificationId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
