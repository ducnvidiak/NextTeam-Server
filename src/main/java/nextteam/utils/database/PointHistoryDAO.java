/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
}
