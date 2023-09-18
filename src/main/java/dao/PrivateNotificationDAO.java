/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.PrivateNotification;

public class PrivateNotificationDAO extends DBContext {

    public List<PrivateNotification> getAllPrivateNotifications() {
        List<PrivateNotification> privateNotifications = new ArrayList<>();
        PrivateNotification privateNotification = null;

        String sql = "SELECT * FROM privateNotifications";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                privateNotification = new PrivateNotification(
                    rs.getInt("id"),
                    rs.getInt("clubId"),
                    rs.getInt("sendTo"),
                    rs.getBoolean("hasSeen"),
                    rs.getTimestamp("markTime"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getTimestamp("createdAt"),
                    rs.getTimestamp("updatedAt")
                );

                privateNotifications.add(privateNotification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return privateNotifications;
    }

    public PrivateNotification getPrivateNotificationById(int id) {
        PrivateNotification privateNotification = null;
        String sql = "SELECT * FROM privateNotifications WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                privateNotification = new PrivateNotification(
                    rs.getInt("id"),
                    rs.getInt("clubId"),
                    rs.getInt("sendTo"),
                    rs.getBoolean("hasSeen"),
                    rs.getTimestamp("markTime"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getTimestamp("createdAt"),
                    rs.getTimestamp("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return privateNotification;
    }

    public boolean addPrivateNotification(PrivateNotification privateNotification) {
        String sql = "INSERT INTO privateNotifications (clubId, sendTo, hasSeen, markTime, title, content) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, privateNotification.getClubId());
            st.setInt(2, privateNotification.getSendTo());
            st.setBoolean(3, privateNotification.isHasSeen());
            st.setTimestamp(4, (Timestamp) privateNotification.getMarkTime());
            st.setString(5, privateNotification.getTitle());
            st.setString(6, privateNotification.getContent());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePrivateNotification(PrivateNotification privateNotification) {
        String sql = "UPDATE privateNotifications SET clubId = ?, sendTo = ?, hasSeen = ?, markTime = ?, title = ?, content = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, privateNotification.getClubId());
            st.setInt(2, privateNotification.getSendTo());
            st.setBoolean(3, privateNotification.isHasSeen());
            st.setTimestamp(4, (Timestamp) privateNotification.getMarkTime());
            st.setString(5, privateNotification.getTitle());
            st.setString(6, privateNotification.getContent());
            st.setInt(7, privateNotification.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePrivateNotification(int privateNotificationId) {
        String sql = "DELETE FROM privateNotifications WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, privateNotificationId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
