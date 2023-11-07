package nextteam.utils.database;

import com.sun.java.accessibility.util.EventID;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextteam.Global;
import nextteam.models.Event;
import nextteam.models.response.EventAttendanceResponse;
import nextteam.models.response.EventResponse;
import nextteam.utils.SQLDatabase;

public class EventDAO extends SQLDatabase {

    public EventDAO(Connection connection) {
        super(connection);
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM events");
        try {
            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("registeredBy"),
                        rs.getInt("locationId"),
                        rs.getString("checkInCode"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime"),
                        rs.getString("type"),
                        rs.getString("planUrl"),
                        rs.getString("bannerUrl"),
                        rs.getString("isApprove"),
                        rs.getString("response"),
                        rs.getInt("clubId"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt")
                );
                events.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }

    public int createEvent(Event e) {
        int rs = 0;
        rs = executeUpdatePreparedStatement(
                "INSERT INTO events (name, description, registeredBy, clubId, bannerUrl, startTime, endTime, locationId, type, planUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                e.getName(),
                e.getDescription(),
                e.getRegisteredBy(),
                e.getClubId(),
                e.getBannerUrl(),
                e.getStartTime(),
                e.getEndTime(),
                e.getLocationId(),
                e.getType(),
                e.getPlanUrl()
        );
        return rs;
    }

    public int createEventForAdmin(Event e) {
        int rs = 0;
        rs = executeUpdatePreparedStatement(
                "INSERT INTO events (name, description, registeredBy, bannerUrl, startTime, endTime, locationId, type, planUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                e.getName(),
                e.getDescription(),
                e.getRegisteredBy(),
                e.getBannerUrl(),
                e.getStartTime(),
                e.getEndTime(),
                e.getLocationId(),
                e.getType(),
                e.getPlanUrl()
        );
        return rs;
    }

    public int deleteEventByEventId(String eventId) {
        int rs = 0;
        rs = executeUpdatePreparedStatement(
                "DELETE FROM feedbacks WHERE eventId = ?;\n"
                + "DELETE FROM eventRegistrations WHERE event = ?;\n"
                + "DELETE FROM events\n"
                + "WHERE id = ?;",
                eventId, eventId, eventId
        );
        return rs;
    }

    public int updateEventByEventId(String eventId, Event e) {
        int rs = 0;
        System.out.println(e.toString());
        System.out.println("time: " + e.getStartTime());
        rs = executeUpdatePreparedStatement(
                "UPDATE events \n"
                + "SET\n"
                + "  name = ?,\n"
                + "  description = ?,\n"
                + "  type = ?, \n"
                + "  bannerUrl = ?,\n"
                + "  locationId = ?,\n"
                + "  planUrl = ?,\n"
                + "  startTime = ?,\n"
                + "  endTime = ?\n"
                + "WHERE id = ?",
                e.getName(),
                e.getDescription(),
                e.getType(),
                e.getBannerUrl(),
                e.getLocationId(),
                e.getPlanUrl(),
                e.getStartTime(),
                e.getEndTime(),
                eventId
        );
        return rs;
    }

    public int updateEventStatus(String eventId, String status, String feedback) {
        int result = executeUpdatePreparedStatement("UPDATE events SET isApproved = ?, response = ? WHERE events.id = ?", status, feedback, eventId);
        return result;
    }

    public List<EventResponse> getAllEventsDetail(String userId) {
        List<EventResponse> events = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT\n"
                + "    e.id,\n"
                + "    e.name,\n"
                + "    e.description,\n"
                + "    e.bannerUrl,\n"
                + "    l.name AS locationName,\n"
                + "    e.startTime,\n"
                + "    e.endTime,\n"
                + "    c.subname AS clubSubname,\n"
                + "    c.avatarUrl AS clubAvatarUrl,\n"
                + "    CASE WHEN er.event IS NULL THEN 0 ELSE 1 END AS isRegistered,\n"
                + "    CASE WHEN f.eventId IS NULL THEN 0 ELSE 1 END AS isFeedback,\n"
                + "    ROUND(CAST((SELECT AVG(CAST(point AS DECIMAL(10, 1))) \n"
                + "           FROM feedbacks\n"
                + "           WHERE eventId = e.id) AS DECIMAL(10, 1)), 1) AS avgRating\n"
                + "FROM events e \n"
                + "JOIN locations l ON e.locationId = l.id\n"
                + "LEFT OUTER JOIN clubs c ON e.clubId = c.id\n"
                + "LEFT JOIN eventRegistrations er ON e.id = er.event AND er.registeredBy = ?\n"
                + "LEFT JOIN feedbacks f ON e.id = f.eventId AND f.userId = ?\n"
                + "WHERE e.type = 'public'\n"
                + "AND e.isApproved = 'accepted' ORDER BY e.startTime DESC;", userId, userId);

        try {
            while (rs.next()) {
                EventResponse event = new EventResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("bannerUrl"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime"),
                        rs.getString("locationName"),
                        rs.getString("clubSubname"),
                        rs.getString("clubAvatarUrl"),
                        rs.getBoolean("isRegistered"),
                        rs.getBoolean("isFeedback"),
                        rs.getFloat("avgRating")
                );
                System.out.println("event");
//                System.out.println(event.);
                events.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }

    public List<EventResponse> getAllEventsDetailForGuest() {
        List<EventResponse> events = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT\n"
                + "    e.id,\n"
                + "    e.name,\n"
                + "    e.description,\n"
                + "    e.bannerUrl,\n"
                + "    l.name AS locationName,\n"
                + "    e.startTime,\n"
                + "    e.endTime,\n"
                + "    e.isApproved,\n"
                + "    c.subname AS clubSubname,\n"
                + "    c.avatarUrl AS clubAvatarUrl,\n"
                + "    CASE WHEN er.event IS NULL THEN 0 ELSE 1 END AS isRegistered,\n"
                + "    CASE WHEN f.eventId IS NULL THEN 0 ELSE 1 END AS isFeedback,\n"
                + "    ROUND(CAST((SELECT AVG(CAST(point AS DECIMAL(10, 1))) \n"
                + "           FROM feedbacks\n"
                + "           WHERE eventId = e.id) AS DECIMAL(10, 1)), 1) AS avgRating\n"
                + "FROM events e \n"
                + "JOIN locations l ON e.locationId = l.id\n"
                + "LEFT OUTER JOIN clubs c ON e.clubId = c.id\n"
                + "LEFT JOIN eventRegistrations er ON e.id = er.event AND er.registeredBy = null\n"
                + "LEFT JOIN feedbacks f ON e.id = f.eventId AND f.userId = null\n"
                + "WHERE e.type = 'public'\n"
                + "AND e.isApproved = 'accepted' ORDER BY e.startTime DESC;");

        try {
            while (rs.next()) {
                EventResponse event = new EventResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("bannerUrl"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime"),
                        rs.getString("isApproved"),
                        rs.getString("locationName"),
                        rs.getString("clubSubname"),
                        rs.getString("clubAvatarUrl"),
                        rs.getBoolean("isRegistered"),
                        rs.getBoolean("isFeedback"),
                        rs.getFloat("avgRating")
                );
                System.out.println("event");
//                System.out.println(event.);
                events.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }

    public List<EventResponse> getAllEventsDetailForManager(String clubId) {
        List<EventResponse> events = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT\n"
                + "  e.id,\n"
                + "  e.name,\n"
                + "  e.type,\n"
                + "  e.description, \n"
                + "  e.bannerUrl,\n"
                + "  e.startTime,\n"
                + "  e.endTime,\n"
                + "  e.isApproved,\n"
                + "  e.planUrl,\n"
                + "  l.name AS locationName,\n"
                + "  c.subname AS clubSubname,\n"
                + "  c.avatarUrl AS clubAvatarUrl\n"
                + "FROM events e\n"
                + "INNER JOIN locations l ON l.id = e.locationId \n"
                + "INNER JOIN clubs c ON c.id = e.clubId\n"
                + "WHERE e.clubId = ? ORDER BY e.startTime DESC", clubId);

        try {
            while (rs.next()) {
                EventResponse event = new EventResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("bannerUrl"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime"),
                        rs.getString("isApproved"),
                        rs.getString("planUrl"),
                        rs.getString("locationName"),
                        rs.getString("clubSubname"),
                        rs.getString("clubAvatarUrl")
                );
                events.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }

    public List<EventResponse> getAllEventsDetailForAdmin() {
        List<EventResponse> events = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT\n"
                + "    e.id,\n"
                + "    e.name,\n"
                + "    e.description,\n"
                + "    e.bannerUrl,\n"
                + "    l.name AS locationName,\n"
                + "    e.startTime,\n"
                + "    e.endTime,\n"
                + "  e.isApproved,\n"
                //<<<<<<< HEAD
                //                + "  e.planUrl,\n"
                //                + "  l.name AS locationName,\n"
                //                + "  c.subname \n"
                //                + "FROM events e \n"
                //                + "INNER JOIN locations l ON l.id = e.locationId \n"
                //                + "INNER JOIN clubs c ON c.id = e.clubId\n"
                //        );
                //
                //=======
                + "    e.planUrl,\n"
                + "    e.createdAt\n"
                + "FROM events e \n"
                + "JOIN locations l ON e.locationId = l.id\n"
                //                + "WHERE e.clubId is null\n"
                + "ORDER BY e.startTime DESC;");
//>>>>>>> 77b7d72c03974a0e9e10a10bd8555253361a2d70
        try {
            while (rs.next()) {
                EventResponse event = new EventResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("bannerUrl"),
                        rs.getString("locationName"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime"),
                        rs.getString("isApproved"),
                        //<<<<<<< HEAD
                        //                        rs.getString("locationName"),
                        //                        rs.getString("subname")
                        //                );
                        //                
                        //=======
                        rs.getString("planUrl"),
                        true
                );
//>>>>>>> 77b7d72c03974a0e9e10a10bd8555253361a2d70
                events.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }

    public List<EventResponse> getAllEventsDetailForMember(String clubId, String userId) {
        List<EventResponse> events = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT\n"
                + "e.id,\n"
                + "e.name,\n"
                + "e.description,\n"
                + "e.bannerUrl,\n"
                + "e.type,\n"
                + "l.name AS locationName,\n"
                + "e.startTime,\n"
                + "e.endTime,\n"
                + "c.subname AS clubSubname,\n"
                + "c.avatarUrl AS clubAvatarUrl,\n"
                + "CASE WHEN er.event IS NULL THEN 0 ELSE 1 END AS isRegistered,\n"
                + "CASE WHEN f.eventId IS NULL THEN 0 ELSE 1 END AS isFeedback,\n"
                + "ROUND(CAST((SELECT AVG(CAST(point AS DECIMAL(10, 1))) \n"
                + "FROM feedbacks\n"
                + "WHERE eventId = e.id) AS DECIMAL(10, 1)), 1) AS avgRating\n"
                + "FROM events e \n"
                + "JOIN locations l ON e.locationId = l.id\n"
                + "JOIN clubs c ON e.clubId = c.id\n"
                + "LEFT JOIN eventRegistrations er ON e.id = er.event AND er.registeredBy = ?\n"
                + "LEFT JOIN feedbacks f ON e.id = f.eventId AND f.userId = ?\n"
                + "WHERE e.clubId = ?\n"
                + "AND e.isApproved = 'accepted' ORDER BY e.startTime DESC;", userId, userId, clubId);

        try {
            while (rs.next()) {
                EventResponse event = new EventResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("bannerUrl"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime"),
                        rs.getString("locationName"),
                        rs.getString("clubSubname"),
                        rs.getString("clubAvatarUrl"),
                        rs.getBoolean("isRegistered"),
                        rs.getBoolean("isFeedback"),
                        rs.getFloat("avgRating")
                );
                System.out.println("event");
                events.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }

    public List<EventAttendanceResponse> getAllEventsDetailForTakingAttendance(String clubId) {
        List<EventAttendanceResponse> events = new ArrayList<>();
        System.out.println(clubId);
        ResultSet rs = executeQueryPreparedStatement("SELECT\n"
                + "  e.id,\n"
                + "  e.name,\n"
                + "  e.startTime,\n"
                + "  e.endTime\n"
                + "FROM events e\n"
                + "WHERE e.clubId = ? AND e.isApproved = ? AND e.startTime < GETDATE()\n"
                + "ORDER BY e.startTime DESC", clubId, "accepted");
        System.out.println(clubId);

        try {
            while (rs.next()) {
                EventAttendanceResponse event = new EventAttendanceResponse(
                        rs.getInt("id"),
                        rs.getNString("name"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime")
                );
//                System.out.println("event");
//                System.out.println(event.);
                events.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }

    public List<EventResponse> getAllEventsDetailForAdminReview() {
        List<EventResponse> events = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT\n"
                + "    e.id,\n"
                + "    e.name,\n"
                + "    e.description,\n"
                + "    e.bannerUrl,\n"
                + "    l.name AS locationName,\n"
                + "    e.startTime,\n"
                + "    e.endTime,\n"
                + "    e.isApproved,\n"
                + "    e.planUrl,\n"
                + "    c.subname AS clubSubname,\n"
                + "    c.avatarUrl AS clubAvatarUrl,\n"
                + "    CASE WHEN er.event IS NULL THEN 0 ELSE 1 END AS isRegistered,\n"
                + "    CASE WHEN f.eventId IS NULL THEN 0 ELSE 1 END AS isFeedback,\n"
                + "    ROUND(CAST((SELECT AVG(CAST(point AS DECIMAL(10, 1))) \n"
                + "           FROM feedbacks\n"
                + "           WHERE eventId = e.id) AS DECIMAL(10, 1)), 1) AS avgRating\n"
                + "FROM events e \n"
                + "JOIN locations l ON e.locationId = l.id\n"
                + "LEFT OUTER JOIN clubs c ON e.clubId = c.id\n"
                + "LEFT JOIN eventRegistrations er ON e.id = er.event AND er.registeredBy = null\n"
                + "LEFT JOIN feedbacks f ON e.id = f.eventId AND f.userId = null\n"
                + "WHERE e.type = 'public'\n"
                + "ORDER BY e.startTime DESC;");

        try {
            while (rs.next()) {
                EventResponse event = new EventResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("bannerUrl"),
                        rs.getTimestamp("startTime"),
                        rs.getTimestamp("endTime"),
                        rs.getString("isApproved"),
                        rs.getString("planUrl"),
                        rs.getString("locationName"),
                        rs.getString("clubSubname"),
                        rs.getString("clubAvatarUrl"),
                        rs.getBoolean("isRegistered"),
                        rs.getBoolean("isFeedback"),
                        rs.getFloat("avgRating")
                );
                System.out.println("event");
                System.out.println(event.getIsApproved());
                events.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }
}
