/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.PointsHistory;

public class PointsHistoryDAO extends DBContext {

    public List<PointsHistory> getAllPointsHistories() {
        List<PointsHistory> pointsHistories = new ArrayList<>();
        PointsHistory pointsHistory = null;

        String sql = "SELECT * FROM pointsHistories";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                pointsHistory = new PointsHistory(
                    rs.getInt("id"),
                    rs.getInt("createdBy"),
                    rs.getInt("receivedBy"),
                    rs.getInt("clubId"),
                    rs.getInt("amount"),
                    rs.getString("reason"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );

                pointsHistories.add(pointsHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pointsHistories;
    }

    public PointsHistory getPointsHistoryById(int id) {
        PointsHistory pointsHistory = null;
        String sql = "SELECT * FROM pointsHistories WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                pointsHistory = new PointsHistory(
                    rs.getInt("id"),
                    rs.getInt("createdBy"),
                    rs.getInt("receivedBy"),
                    rs.getInt("clubId"),
                    rs.getInt("amount"),
                    rs.getString("reason"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pointsHistory;
    }

    public boolean addPointsHistory(PointsHistory pointsHistory) {
        String sql = "INSERT INTO pointsHistories (createdBy, receivedBy, clubId, amount, reason, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, pointsHistory.getCreatedBy());
            st.setInt(2, pointsHistory.getReceivedBy());
            st.setInt(3, pointsHistory.getClubId());
            st.setInt(4, pointsHistory.getAmount());
            st.setString(5, pointsHistory.getReason());
            st.setDate(6, new java.sql.Date(pointsHistory.getCreatedAt().getTime()));
            st.setDate(7, new java.sql.Date(pointsHistory.getUpdatedAt().getTime()));

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePointsHistory(PointsHistory pointsHistory) {
        String sql = "UPDATE pointsHistories SET createdBy = ?, receivedBy = ?, clubId = ?, amount = ?, reason = ?, createdAt = ?, updatedAt = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, pointsHistory.getCreatedBy());
            st.setInt(2, pointsHistory.getReceivedBy());
            st.setInt(3, pointsHistory.getClubId());
            st.setInt(4, pointsHistory.getAmount());
            st.setString(5, pointsHistory.getReason());
            st.setDate(6, new java.sql.Date(pointsHistory.getCreatedAt().getTime()));
            st.setDate(7, new java.sql.Date(pointsHistory.getUpdatedAt().getTime()));
            st.setInt(8, pointsHistory.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePointsHistory(int pointsHistoryId) {
        String sql = "DELETE FROM pointsHistories WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, pointsHistoryId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
