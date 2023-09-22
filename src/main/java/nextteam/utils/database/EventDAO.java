package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextteam.models.Event;
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
                    rs.getBoolean("isApproved"),
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

    
}
