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
import model.EventRegistration;

public class EventRegistrationDao extends DBContext {

    public List<EventRegistration> getAllEventRegistrations() {
        List<EventRegistration> eventRegistrationList = new ArrayList<>();
        EventRegistration eventRegistration = null;

        String sql = "SELECT * FROM [eventRegistrations]";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                eventRegistration = new EventRegistration(
                    rs.getInt("id"),
                    rs.getInt("eventId"),
                    rs.getInt("registeredBy"),
                    rs.getBoolean("isJoined"),
                    rs.getString("reasonForAbsence"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );

                eventRegistrationList.add(eventRegistration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventRegistrationList;
    }

    public EventRegistration getEventRegistrationById(int id) {
        EventRegistration eventRegistration = null;
        String sql = "SELECT * FROM [eventRegistrations] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                eventRegistration = new EventRegistration(
                    rs.getInt("id"),
                    rs.getInt("eventId"),
                    rs.getInt("registeredBy"),
                    rs.getBoolean("isJoined"),
                    rs.getString("reasonForAbsence"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventRegistration;
    }

    public boolean addEventRegistration(EventRegistration eventRegistration) {
        String sql = "INSERT INTO [eventRegistrations] (eventId, registeredBy, isJoined, reasonForAbsence, createdAt, updatedAt) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, eventRegistration.getEventId());
            st.setInt(2, eventRegistration.getRegisteredBy());
            st.setBoolean(3, eventRegistration.isIsJoined());
            st.setString(4, eventRegistration.getReasonForAbsence());
            st.setDate(5, new java.sql.Date(eventRegistration.getCreatedAt().getTime()));
            st.setDate(6, new java.sql.Date(eventRegistration.getUpdatedAt().getTime()));

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEventRegistration(EventRegistration eventRegistration) {
        String sql = "UPDATE [eventRegistrations] SET " +
                     "eventId = ?, registeredBy = ?, isJoined = ?, reasonForAbsence = ?, updatedAt = ? " +
                     "WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, eventRegistration.getEventId());
            st.setInt(2, eventRegistration.getRegisteredBy());
            st.setBoolean(3, eventRegistration.isIsJoined());
            st.setString(4, eventRegistration.getReasonForAbsence());
            st.setDate(5, new java.sql.Date(eventRegistration.getUpdatedAt().getTime()));
            st.setInt(6, eventRegistration.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEventRegistration(int eventRegistrationId) {
        String sql = "DELETE FROM [eventRegistrations] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, eventRegistrationId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
