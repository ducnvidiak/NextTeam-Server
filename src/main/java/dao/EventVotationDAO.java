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
import model.EventVotation;

public class EventVotationDAO extends DBContext {
    

    public List<EventVotation> getAllEventVotations() {
        List<EventVotation> eventVotationList = new ArrayList<>();
        EventVotation eventVotation = null;

        String sql = "SELECT * FROM [eventVotations]";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                eventVotation = new EventVotation(
                    rs.getInt("id"),
                    rs.getInt("eventId"),
                    rs.getInt("userId"),
                    rs.getBoolean("isAgreed"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );

                eventVotationList.add(eventVotation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventVotationList;
    }

    public EventVotation getEventVotationById(int id) {
        EventVotation eventVotation = null;
        String sql = "SELECT * FROM [eventVotations] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                eventVotation = new EventVotation(
                    rs.getInt("id"),
                    rs.getInt("eventId"),
                    rs.getInt("userId"),
                    rs.getBoolean("isAgreed"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventVotation;
    }

    public boolean addEventVotation(EventVotation eventVotation) {
        String sql = "INSERT INTO [eventVotations] (eventId, userId, isAgreed, createdAt, updatedAt) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, eventVotation.getEventId());
            st.setInt(2, eventVotation.getUserId());
            st.setBoolean(3, eventVotation.isIsAgreed());
            st.setDate(4, new java.sql.Date(eventVotation.getCreatedAt().getTime()));
            st.setDate(5, new java.sql.Date(eventVotation.getUpdatedAt().getTime()));

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEventVotation(EventVotation eventVotation) {
        String sql = "UPDATE [eventVotations] SET " +
                     "eventId = ?, userId = ?, isAgreed = ?, updatedAt = ? " +
                     "WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, eventVotation.getEventId());
            st.setInt(2, eventVotation.getUserId());
            st.setBoolean(3, eventVotation.isIsAgreed());
            st.setDate(4, new java.sql.Date(eventVotation.getUpdatedAt().getTime()));
            st.setInt(5, eventVotation.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEventVotation(int eventVotationId) {
        String sql = "DELETE FROM [eventVotations] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, eventVotationId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
