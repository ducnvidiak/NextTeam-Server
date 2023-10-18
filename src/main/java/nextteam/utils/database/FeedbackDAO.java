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
import nextteam.models.Feedback;
import nextteam.models.response.EventResponse;
import nextteam.utils.SQLDatabase;

public class FeedbackDAO extends SQLDatabase {

    public FeedbackDAO(Connection connection) {
        super(connection);
    }

    public List<Feedback> getAllFeedBacksByEventId(String eventId) {
        List<Feedback> feedbacks = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT \n"
                + "u.firstname,\n"
                + "u.lastname,\n"
                + "u.email,\n"
                + "u.username,\n"
                + "f.point,\n"
                + "f.content,\n"
                + "f.createdAt\n"
                + "FROM feedbacks f\n"
                + "INNER JOIN users u ON u.id = f.userId\n"
                + "WHERE f.eventId = ?\n"
                + "ORDER BY f.createdAt DESC", eventId);
        try {
            while (rs.next()) {
                Feedback feedback = new Feedback(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getInt("point"),
                        rs.getString("content")
                );
                feedbacks.add(feedback);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return feedbacks;
    }

    public int createFeedback(Feedback f) {
        int rs = 0;
        System.out.println("!!!!");
        System.out.println(f.getUserId());
        System.out.println(f.getEventId());
        System.out.println(f.getPoint());
        System.out.println(f.getContent());
        rs = executeUpdatePreparedStatement(
                "INSERT INTO feedbacks (userId, eventId, point, content) VALUES (?, ?, ?, ?);",
                f.getUserId(),
                f.getEventId(),
                f.getPoint(),
                f.getContent()
        );
        return rs;
    }
}
