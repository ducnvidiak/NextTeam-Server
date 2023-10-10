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
import nextteam.models.Event;
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
                        rs.getBoolean("isApprove"),
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
        System.out.println("!!!!" + e.getClubId());
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
                "DELETE er \n"
                + "FROM eventRegistrations er\n"
                + "INNER JOIN events e ON e.id = er.event\n"
                + "WHERE e.id = ?\n"
                + "\n"
                + "DELETE e \n"
                + "FROM events e\n"
                + "WHERE e.id = ?",
                eventId, eventId
        );
        return rs;
    }

    public int updateEventByEventId(String eventId, Event e) {
        int rs = 0;
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
        //2023-10-10 10:00:00
        //2023-10-11 02:00:00
        //2023-10-13 01:00:00
        //2023-10-10 12:00:00.0
        //2023-10-13 01:00:00.0
    }

    public List<EventResponse> getAllEventsDetail(String userId) {
        List<EventResponse> events = new ArrayList<>();
        System.out.println("userId" + userId);
        ResultSet rs = executeQueryPreparedStatement("SELECT \n"
                + "  e.id AS id, \n"
                + "  e.name AS name, \n"
                + "  e.name AS description, \n"
                + "  e.type AS type, \n"
                + "  e.bannerUrl AS bannerUrl,\n"
                + "  l.name AS locationName,\n"
                + "  e.startTime AS startTime, \n"
                + "  e.endTime AS endTime,\n"
                + "  e.isApproved AS isApproved,\n"
                + "  c.subname AS clubSubname,\n"
                + "  c.avatarUrl AS clubAvatarUrl,\n"
                + "  CASE WHEN er.event IS NOT NULL THEN 1 ELSE 0 END AS isRegistered,\n"
                + "  CAST(AVG(CAST(f.point AS REAL)) as decimal(2,1)) AS avgRating,\n"
                + "  CASE WHEN f.userId = ? THEN 1 ELSE 0 END AS isFeedback\n"
                + "FROM events e\n"
                + "JOIN locations l ON e.locationId = l.id\n"
                + "JOIN clubs c ON e.clubId = c.id\n"
                + "LEFT JOIN eventRegistrations er ON e.id = er.event AND er.registeredBy = ?  \n"
                + "LEFT JOIN feedbacks f ON e.id = f.eventId\n"
                + "GROUP BY \n"
                + "  e.id, e.name, e.type, e.bannerUrl,\n"
                + "  l.name, e.startTime, e.endTime, \n"
                + "  e.isApproved, c.subname, c.avatarUrl, \n"
                + "  er.event, f.userId\n"
                + "ORDER BY \n"
                + "  e.startTime DESC, e.id ASC;", userId, userId);

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
                        rs.getBoolean("isApproved"),
                        rs.getString("locationName"),
                        rs.getString("clubSubname"),
                        rs.getString("clubAvatarUrl"),
                        rs.getBoolean("isRegistered"),
                        rs.getFloat("avgRating"),
                        rs.getBoolean("isFeedback")
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
        System.out.println(clubId);
        ResultSet rs = executeQueryPreparedStatement("SELECT\n"
                + "  e.id,\n"
                + "  e.name,\n"
                + "  e.type,\n"
                + "  e.description, \n"
                + "  e.bannerUrl,\n"
                + "  e.startTime,\n"
                + "  e.endTime,\n"
                + "  e.isApproved,\n"
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
                        rs.getBoolean("isApproved"),
                        rs.getString("locationName"),
                        rs.getString("clubSubname"),
                        rs.getString("clubAvatarUrl")
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

    public List<EventResponse> getAllEventsDetailForAdmin() {
        List<EventResponse> events = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT\n"
                + "  e.id,\n"
                + "  e.name,\n"
                + "  e.type,\n"
                + "  e.clubId,\n"
                + "  e.description,\n"
                + "  e.bannerUrl,\n"
                + "  e.startTime, \n"
                + "  e.endTime,\n"
                + "  e.isApproved,\n"
                + "  l.name AS locationName,\n"
                + "  c.subname AS clubSubname,\n"
                + "  c.avatarUrl AS clubAvatarUrl\n"
                + "FROM events e\n"
                + "INNER JOIN locations l ON l.id = e.locationId\n"
                + "LEFT JOIN clubs c ON c.id = e.clubId\n"
                + "WHERE e.clubId IS NULL\n"
                + "ORDER BY e.startTime DESC");

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
                        rs.getBoolean("isApproved"),
                        rs.getString("locationName"),
                        rs.getString("clubSubname"),
                        rs.getString("clubAvatarUrl")
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

    public List<EventResponse> getAllEventsDetailForMember(String clubId, String userId) {
        List<EventResponse> events = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT\n"
                + "  e.id,\n"
                + "  e.name,\n"
                + "  e.type,\n"
                + "  e.description,\n"
                + "  e.bannerUrl,\n"
                + "  e.startTime, \n"
                + "  e.endTime,\n"
                + "  e.isApproved,\n"
                + "  l.name AS locationName,\n"
                + "  c.subname AS clubSubname,\n"
                + "  c.avatarUrl AS clubAvatarUrl,\n"
                + "  CASE \n"
                + "    WHEN er.id IS NULL THEN 'false'\n"
                + "    ELSE 'true' \n"
                + "  END AS isRegistered\n"
                + "FROM events e  \n"
                + "INNER JOIN locations l ON l.id = e.locationId\n"
                + "INNER JOIN clubs c ON c.id = e.clubId\n"
                + "LEFT JOIN eventRegistrations er ON er.event = e.id AND er.registeredBy = ?\n"
                + "WHERE e.clubId = ?\n"
                + "ORDER BY e.startTime DESC", userId, clubId);

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
                        rs.getBoolean("isApproved"),
                        rs.getString("locationName"),
                        rs.getString("clubSubname"),
                        rs.getString("clubAvatarUrl"),
                        rs.getBoolean("isRegistered")
                );
                System.out.println("event");
                events.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }

}
