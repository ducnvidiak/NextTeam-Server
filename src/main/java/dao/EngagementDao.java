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
import model.Engagement;

public class EngagementDao extends DBContext {

    public List<Engagement> getAllEngagements() {
        List<Engagement> engagementList = new ArrayList<>();
        Engagement engagement = null;

        String sql = "SELECT * FROM [engagements]";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                engagement = new Engagement(
                    rs.getInt("id"),
                    rs.getInt("userId"),
                    rs.getInt("departmentId"),
                    rs.getInt("clubId"),
                    rs.getInt("roleId"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );

                engagementList.add(engagement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return engagementList;
    }

    public Engagement getEngagementById(int id) {
        Engagement engagement = null;
        String sql = "SELECT * FROM [engagements] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                engagement = new Engagement(
                    rs.getInt("id"),
                    rs.getInt("userId"),
                    rs.getInt("departmentId"),
                    rs.getInt("clubId"),
                    rs.getInt("roleId"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return engagement;
    }

    public boolean addEngagement(Engagement engagement) {
        String sql = "INSERT INTO [engagements] (userId, departmentId, clubId, roleId, createdAt, updatedAt) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, engagement.getUserId());
            st.setInt(2, engagement.getDepartmentId());
            st.setInt(3, engagement.getClubId());
            st.setInt(4, engagement.getRoleId());
            st.setDate(5, (java.sql.Date) engagement.getCreatedAt());
            st.setDate(6, (java.sql.Date) engagement.getUpdatedAt());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEngagement(Engagement engagement) {
        String sql = "UPDATE [engagements] SET " +
                     "userId = ?, departmentId = ?, clubId = ?, roleId = ?, updatedAt = ? " +
                     "WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, engagement.getUserId());
            st.setInt(2, engagement.getDepartmentId());
            st.setInt(3, engagement.getClubId());
            st.setInt(4, engagement.getRoleId());
            st.setDate(5, (java.sql.Date) engagement.getUpdatedAt());
            st.setInt(6, engagement.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEngagement(int engagementId) {
        String sql = "DELETE FROM [engagements] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, engagementId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
