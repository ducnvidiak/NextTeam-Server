///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.EntranceInterview;
//
public class EntranceInterviewDAO extends DBContext {
//
//    public List<EntranceInterview> getAllEntranceInterviews() {
//        List<EntranceInterview> entranceInterviewList = new ArrayList<>();
//        EntranceInterview entranceInterview = null;
//
//        String sql = "SELECT * FROM [entranceInterviews]";
//        try (PreparedStatement st = connection.prepareStatement(sql)) {
//            ResultSet rs = st.executeQuery();
//
//            while (rs.next()) {
//                entranceInterview = new EntranceInterview(
//                    rs.getInt("id"),
//                    rs.getDate("startTime"),
//                    rs.getDate("endTime"),
//                    rs.getInt("engagementId"),
//                    rs.getString("comment"),
//                    rs.getBoolean("isApproved"),
//                    rs.getDate("createdAt"),
//                    rs.getDate("updatedAt")
//                );
//
//                entranceInterviewList.add(entranceInterview);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return entranceInterviewList;
//    }
//
//    public EntranceInterview getEntranceInterviewById(int id) {
//        EntranceInterview entranceInterview = null;
//        String sql = "SELECT * FROM [entranceInterviews] WHERE id = ?";
//        try (PreparedStatement st = connection.prepareStatement(sql)) {
//            st.setInt(1, id);
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()) {
//                entranceInterview = new EntranceInterview(
//                    rs.getInt("id"),
//                    rs.getDate("startTime"),
//                    rs.getDate("endTime"),
//                    rs.getInt("engagementId"),
//                    rs.getString("comment"),
//                    rs.getBoolean("isApproved"),
//                    rs.getDate("createdAt"),
//                    rs.getDate("updatedAt")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return entranceInterview;
//    }
//
//    public boolean addEntranceInterview(EntranceInterview entranceInterview) {
//        String sql = "INSERT INTO [entranceInterviews] (startTime, endTime, engagementId, comment, isApproved, createdAt, updatedAt) " +
//                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement st = connection.prepareStatement(sql)) {
//            st.setDate(1, (java.sql.Date) entranceInterview.getStartTime());
//            st.setDate(2, (java.sql.Date) entranceInterview.getEndTime());
//            st.setInt(3, entranceInterview.getEngagementId());
//            st.setString(4, entranceInterview.getComment());
//            st.setBoolean(5, entranceInterview.isApproved());
//            st.setDate(6, (java.sql.Date) entranceInterview.getCreatedAt());
//            st.setDate(7, (java.sql.Date) entranceInterview.getUpdatedAt());
//
//            int rowsInserted = st.executeUpdate();
//            return rowsInserted == 1;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
    public boolean updateEntranceInterview(EntranceInterview entranceInterview) {
        String sql = "UPDATE [entranceInterviews] SET " +
                     "startTime = ?, endTime = ?, engagementId = ?, comment = ?, isApproved = ?, updatedAt = ? " +
                     "WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setDate(1, (java.sql.Date) entranceInterview.getStartTime());
            st.setDate(2, (java.sql.Date) entranceInterview.getEndTime());
            st.setInt(3, entranceInterview.getEngagementId());
            st.setString(4, entranceInterview.getComment());
            st.setBoolean(5, entranceInterview.isApproved());
            st.setDate(6, (java.sql.Date) entranceInterview.getUpdatedAt());
            st.setInt(7, entranceInterview.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//
    public boolean deleteEntranceInterview(int entranceInterviewId) {
        String sql = "DELETE FROM [entranceInterviews] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, entranceInterviewId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
//
