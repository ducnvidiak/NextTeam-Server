package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import nextteam.models.Attendance;
import nextteam.models.Event;
import nextteam.models.User;
import nextteam.models.response.EventResponse;
import nextteam.utils.SQLDatabase;
import nextteam.models.EventRegistration;
import nextteam.models.response.EventRegistrationAttendanceResponse;
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
                + "  u.email,\n"
                + "  u.username,\n"
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
                        rs.getString("username"),
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

    public List<EventRegistrationAttendanceResponse> getAllEventRegistrationByEventIdForAttendance(String eventId) {
        List<EventRegistrationAttendanceResponse> list = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("""
                                                     SELECT 
                                                     id, registeredBy, isJoined, reasonsForAbsence, createdAt, updatedAt
                                                     FROM eventRegistrations
                                                     WHERE event = ?
                                                     ORDER BY createdAt DESC""", eventId);
        try {
            while (rs.next()) {
                EventRegistrationAttendanceResponse eventRegistrationResponse = new EventRegistrationAttendanceResponse(
                        rs.getInt("id"),
                        rs.getInt("registeredBy"),
                        rs.getBoolean("isJoined"),
                        rs.getNString("reasonsForAbsence") == null ? "" : rs.getNString("reasonsForAbsence"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt")
                );
                list.add(eventRegistrationResponse);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int setAttendance(Attendance atten) {
        return executeUpdatePreparedStatement(
                "UPDATE eventRegistrations SET reasonsForAbsence=?, isJoined=? WHERE id=?",
                atten.getNote(), atten.isAtten(), atten.getId()
        );
    }

    public void takeAttendances(List<Attendance> attendances) {
        for (Attendance atten : attendances) {
            setAttendance(atten);
        }
    }

    public int take(int mid, int eid) {
        return executeUpdatePreparedStatement("UPDATE eventRegistrations SET isJoined=1 WHERE event=? AND registeredBy=?", eid, mid);
    }
}
