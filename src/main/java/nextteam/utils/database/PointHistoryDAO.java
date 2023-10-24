/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextteam.Global;
import nextteam.models.PointsHistory;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class PointHistoryDAO extends SQLDatabase {

    public PointHistoryDAO(Connection connection) {
        super(connection);
    }

    public List<PointsHistory> getAll(int clubId) {
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM pointsHistories WHERE clubId=?", clubId);
        List<PointsHistory> phs = new ArrayList<>();
        try {
            while (rs.next()) {
                phs.add(new PointsHistory(
                        rs.getInt("id"),
                        rs.getInt("createdBy"),
                        rs.getInt("receivedBy"),
                        rs.getInt("clubId"),
                        rs.getInt("amount"),
                        rs.getNString("reason") == null ? rs.getInt("amount") > 0 ? "Cộng điểm" : "Trừ điểm" : rs.getNString("reason"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PointHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return phs;
    }

    public void add(PointsHistory ph) {
        executeUpdatePreparedStatement(
                "INSERT INTO pointsHistories(createdBy, receivedBy, clubId, amount, reason) "
                + "VALUES (?, ?, ?, ?, ?)",
                ph.getCreatedBy(),
                ph.getReceivedBy(),
                ph.getClubId(),
                ph.getAmount(),
                ph.getReason()
        );
    }

    public void edit(int id, int point, String note) {
        executeUpdatePreparedStatement("UPDATE pointsHistories SET amount=?, reason=? WHERE id=?", point, note, id);
    }

    public void delete(int id) {
        executeUpdatePreparedStatement("DELETE FROM pointsHistories WHERE id=?", id);
    }

    public List<PointsHistory> listOfSpecifiedUser(int clubId, int userId) {
        int totalPoint = Global.engagement.getEngagementPoints(userId, clubId);
        class SpecifiedPointsHistory extends PointsHistory {

            public SpecifiedPointsHistory(int id, int createdBy, int receivedBy, int clubId, int amount, String reason, Timestamp createdAt, Timestamp updatedAt) {
                super(id, createdBy, receivedBy, clubId, amount, reason, createdAt, updatedAt);
            }

            @Override
            public String toString() {
                double progress = (getAmount() > 0 ? ((((double) totalPoint) - getAmount()) / totalPoint) : (((double) Math.abs(getAmount())) / totalPoint)) * 100;
                String createdDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date(getCreatedAt().getTime()));
                return """
                        {
                            "id": "%d",
                            "createdBy": "%d",
                            "receivedBy": "%d",
                            "clubId": "%d",
                            "amount": "%d",
                            "reason": "%s",
                            "createdAt": "%s",
                            "updatedAt": "%s",
                            "progress": "%.2f"
                        }""".formatted(
                        getId(),
                        getCreatedBy(),
                        getReceivedBy(),
                        getClubId(),
                        getAmount(),
                        getReason() == null ? getAmount() > 0 ? "Cộng điểm" : "Trừ điểm" : getReason(),
                        createdDate,
                        getUpdatedAt(),
                        progress
                );
            }
        }
        List<PointsHistory> phs = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("""
                                                     SELECT TOP(10) *
                                                     FROM pointsHistories
                                                     WHERE receivedBy=? AND clubId=?
                                                     ORDER BY createdAt DESC""", userId, clubId);
        try {
            while (rs.next()) {
                phs.add(new SpecifiedPointsHistory(
                        rs.getInt("id"),
                        rs.getInt("createdBy"),
                        rs.getInt("receivedBy"),
                        rs.getInt("clubId"),
                        rs.getInt("amount"),
                        rs.getNString("reason"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PointHistoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return phs;
    }
}
