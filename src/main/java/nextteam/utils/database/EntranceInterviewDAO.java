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
import nextteam.Global;
import nextteam.models.Club;
import nextteam.models.Department;
import nextteam.models.Engagement;
import nextteam.models.EntranceInterview;
import nextteam.models.HomeTown;
import nextteam.models.Role;
import nextteam.models.User;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class EntranceInterviewDAO extends SQLDatabase {

    
    public EntranceInterviewDAO(Connection connection) {
        super(connection);
    }
    
    public int addInterview(final EntranceInterview t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "INSERT INTO entranceInterviews (startTime, endTime, engagementId )  VALUES (?,?,?)",
                t.getStartTime(),
                t.getEndTime(),
                t.getEngagementId()
        );
        return ketQua;
    }
    
    public EntranceInterview getInterviewById(String id) {
        EntranceInterview ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM entranceInterviews WHERE id=?", id);
            if (rs.next()) {
                ketQua = new EntranceInterview(rs.getInt(1), rs.getTimestamp(2),rs.getTimestamp(3),rs.getInt(4),rs.getString(5),  rs.getBoolean(6),rs.getInt(7), rs.getTimestamp(8),rs.getTimestamp(9));
            }
        } catch (Exception e) {
        }
        return ketQua;
    }
    public EntranceInterview getInterviewByEngagementId(String id) {
        EntranceInterview ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM entranceInterviews WHERE engagementId=?", id);
            if (rs.next()) {
                ketQua = new EntranceInterview(rs.getInt(1), rs.getTimestamp(2),rs.getTimestamp(3),rs.getInt(4),rs.getString(5),  rs.getBoolean(6),rs.getInt(7), rs.getTimestamp(8),rs.getTimestamp(9));
            }
        } catch (Exception e) {
        }
        return ketQua;
    }
    
    public int Update(EntranceInterview e) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE entranceInterviews SET  comment=?, isApproved=?,approvedBy=? WHERE id=?",
                e.getComment(),
                e.isIsApproved(),
                e.getApprovedBy(),
                e.getId()             
        );
        return ketQua;

    }
    
   
}
