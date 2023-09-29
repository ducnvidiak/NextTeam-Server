package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
                        rs.getDate("startTime"),
                        rs.getDate("endTime"),
                        rs.getString("type"),
                        rs.getString("planUrl"),
                        rs.getString("planUrl"),
                        rs.getBoolean("bannerUrl"),
                        rs.getString("response"),
                        rs.getInt("clubId"),
                        rs.getDate("createdAt"),
                        rs.getDate("updatedAt")
                );
                events.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }

    public List<EventResponse> getAllEventsDetail(String userId) {
        List<EventResponse> events = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT\n"
                + "    e.id AS id,\n"
                + "    e.name AS name,\n"
                + "    e.description AS description,\n"
                + "    e.bannerUrl AS bannerUrl,\n"
                + "    l.name AS locationName,\n"
                + "    e.startTime AS startTime,\n"
                + "    e.endTime AS endTime,\n"
                + "    c.subname AS clubSubname,\n"
                + "    c.avatarUrl AS clubAvatarUrl,\n"
                + "    CASE\n"
                + "        WHEN er.event IS NOT NULL THEN 1\n"
                + "        ELSE 0\n"
                + "    END AS isRegistered\n"
                + "FROM\n"
                + "    events e\n"
                + "    JOIN locations l ON e.locationId = l.id\n"
                + "    JOIN clubs c ON e.clubId = c.id\n"
                + "    LEFT JOIN (\n"
                + "        SELECT DISTINCT event\n"
                + "        FROM eventRegistrations\n"
                + "        WHERE registeredBy = ?\n"
                + "    ) er ON e.id = er.event\n"
                + "ORDER BY e.startTime;", userId);

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
                        rs.getBoolean("isRegistered")
                );
                events.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return events;
    }

}
