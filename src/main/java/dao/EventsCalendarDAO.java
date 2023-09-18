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
import model.EventsCalendar;

public class EventsCalendarDAO extends DBContext {

    public List<EventsCalendar> getAllEventsCalendars() {
        List<EventsCalendar> eventsCalendarList = new ArrayList<>();
        EventsCalendar eventsCalendar = null;

        String sql = "SELECT * FROM [events]";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                eventsCalendar = new EventsCalendar(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("registeredBy"),
                    rs.getString("location"),
                    rs.getString("checkInCode"),
                    rs.getDate("startTime"),
                    rs.getDate("endTime"),
                    rs.getString("type"),
                    rs.getBoolean("isApproved"),
                    rs.getString("response"),
                    rs.getInt("clubId"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );

                eventsCalendarList.add(eventsCalendar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventsCalendarList;
    }

    public EventsCalendar getEventsCalendarById(int id) {
        EventsCalendar eventsCalendar = null;
        String sql = "SELECT * FROM [events] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                eventsCalendar = new EventsCalendar(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("registeredBy"),
                    rs.getString("location"),
                    rs.getString("checkInCode"),
                    rs.getDate("startTime"),
                    rs.getDate("endTime"),
                    rs.getString("type"),
                    rs.getBoolean("isApproved"),
                    rs.getString("response"),
                    rs.getInt("clubId"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventsCalendar;
    }

    public boolean addEventsCalendar(EventsCalendar eventsCalendar) {
        String sql = "INSERT INTO [events] (name, description, registeredBy, location, checkInCode, startTime, endTime, type, isApproved, response, clubId, createdAt, updatedAt) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, eventsCalendar.getName());
            st.setString(2, eventsCalendar.getDescription());
            st.setInt(3, eventsCalendar.getRegisteredBy());
            st.setString(4, eventsCalendar.getLocation());
            st.setString(5, eventsCalendar.getCheckInCode());
            st.setDate(6, new java.sql.Date(eventsCalendar.getStartTime().getTime()));
            st.setDate(7, new java.sql.Date(eventsCalendar.getEndTime().getTime()));
            st.setString(8, eventsCalendar.getType());
            st.setBoolean(9, eventsCalendar.isIsApproved());
            st.setString(10, eventsCalendar.getResponse());
            st.setInt(11, eventsCalendar.getClubId());
            st.setDate(12, new java.sql.Date(eventsCalendar.getCreatedAt().getTime()));
            st.setDate(13, new java.sql.Date(eventsCalendar.getUpdatedAt().getTime()));

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEventsCalendar(EventsCalendar eventsCalendar) {
        String sql = "UPDATE [events] SET " +
                     "name = ?, description = ?, registeredBy = ?, location = ?, checkInCode = ?, startTime = ?, endTime = ?, type = ?, isApproved = ?, response = ?, clubId = ?, updatedAt = ? " +
                     "WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, eventsCalendar.getName());
            st.setString(2, eventsCalendar.getDescription());
            st.setInt(3, eventsCalendar.getRegisteredBy());
            st.setString(4, eventsCalendar.getLocation());
            st.setString(5, eventsCalendar.getCheckInCode());
            st.setDate(6, new java.sql.Date(eventsCalendar.getStartTime().getTime()));
            st.setDate(7, new java.sql.Date(eventsCalendar.getEndTime().getTime()));
            st.setString(8, eventsCalendar.getType());
            st.setBoolean(9, eventsCalendar.isIsApproved());
            st.setString(10, eventsCalendar.getResponse());
            st.setInt(11, eventsCalendar.getClubId());
            st.setDate(12, new java.sql.Date(eventsCalendar.getUpdatedAt().getTime()));
            st.setInt(13, eventsCalendar.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEventsCalendar(int eventsCalendarId) {
        String sql = "DELETE FROM [events] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, eventsCalendarId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
