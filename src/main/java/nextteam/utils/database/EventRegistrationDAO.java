package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import nextteam.models.Event;
import nextteam.models.User;
import nextteam.models.response.EventResponse;
import nextteam.utils.SQLDatabase;
import nextteam.models.EventRegistration;
import nextteam.models.response.EventRegistrationResponse;

public class EventRegistrationDAO extends SQLDatabase {

    public EventRegistrationDAO(Connection connection) {
        super(connection);
    }

    public int addNewRegistration(final EventRegistration eventRegistration) throws Exception {
        int ketQua = 0;
        try {
            ketQua = executeUpdatePreparedStatement(
                    "INSERT INTO eventRegistrations (event, registeredBy) VALUES (?, ?);",
                    eventRegistration.getEventId(),
                    eventRegistration.getRegisteredBy()
            );
        } catch (Exception e) {

            return 0;
        }
        return ketQua;
    }

    public List<EventRegistrationResponse> getAllEventRegistrationByEventId(String eventId) {
        List<EventRegistrationResponse> list = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT \n"
                + "  u.firstname,\n"
                + "  u.lastname,\n"
                + "  u.studentCode ,\n"
                + "  u.email,\n"
                + "  u.phoneNumber,\n"
                + "  er.createdAt\n"
                + "FROM eventRegistrations er\n"
                + "INNER JOIN users u ON u.id = er.registeredBy\n"
                + "WHERE er.event = ?\n"
                + "ORDER BY er.createdAt DESC", eventId);
        try {
            while (rs.next()) {
                EventRegistrationResponse eventRegistrationResponse = new EventRegistrationResponse(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("studentCode"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getDate("createdAt")
                );
                list.add(eventRegistrationResponse);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
