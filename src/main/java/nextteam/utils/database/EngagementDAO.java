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
import nextteam.models.Role;
import nextteam.models.User;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class EngagementDAO extends SQLDatabase {
    
    public class EngagementModelInfo {
        
        private User user;
        private Department dept;
        private Club club;
        private Role role;
        private Engagement engagement;
        private EntranceInterview interview;

        public User getUser() {
            return user;
        }
        
        public void setUser(User user) {
            this.user = user;
        }
        
        public Department getDept() {
            return dept;
        }
        
        public void setDept(Department dept) {
            this.dept = dept;
        }
        
        public Club getClub() {
            return club;
        }
        
        public void setClub(Club club) {
            this.club = club;
        }
        
        public Role getRole() {
            return role;
        }
        
        public void setRole(Role role) {
            this.role = role;
        }
        
        public Engagement getEngagement() {
            return engagement;
        }
        
        public void setEngagement(Engagement engagement) {
            this.engagement = engagement;
        }

        public EntranceInterview getInterview() {
            return interview;
        }

        public void setInterview(EntranceInterview interview) {
            this.interview = interview;
        }
        
        
        
    }
    
    public EngagementDAO(Connection connection) {
        super(connection);
    }
    
    public List<Engagement> getListOfMe(String t) {
        List<Engagement> engagements = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM engagements WHERE userId = ?", t);
        try {
            while (rs.next()) {
                Engagement ht = new Engagement(rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5), rs.getString(6),rs.getInt(7), rs.getTimestamp(8),rs.getTimestamp(9));
                engagements.add(ht);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EngagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return engagements;
    }
    
    public int addEngagement(final Engagement t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "INSERT INTO engagements (userId, departmentId, clubId, cvUrl )  VALUES (?,?,?,?)",
                t.getUserId(),
                t.getDepartmentId(),
                t.getClubId(),
                t.getCvUrl()
        );
        return ketQua;
    }
    
    public Engagement getEngagementById(String id) {
        Engagement ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM engagements WHERE id=?", id);
            if (rs.next()) {
                ketQua = new Engagement(rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getTimestamp(8),rs.getTimestamp(9));
            }
        } catch (Exception e) {
        }
        return ketQua;
    }
    
    public EngagementModelInfo getIdAsModel(Engagement t) {
        EngagementModelInfo info = new EngagementModelInfo();
        
        info.setUser(Global.user.getListUserByIdString(t.getUserId() + ""));
        info.setDept(Global.department.getDepartmentById(t.getDepartmentId() + ""));
        info.setClub(Global.clubDAO.getClubById(t.getClubId()+ ""));
        info.setRole(Global.role.getRoleById(t.getRoleId()+ ""));
        info.setEngagement(Global.engagement.getEngagementById(t.getId()+""));
        info.setInterview(Global.entranceInterview.getInterviewByEngagementId(t.getId()+""));
        return info;
    }
    
    public List<EngagementDAO.EngagementModelInfo> getEngagementModelList(List<Engagement> l) {
        List<EngagementDAO.EngagementModelInfo> res = new ArrayList<>();
        
        for (Engagement en : l) {
            res.add(getIdAsModel(en));
        }
        
        return res;
    }
    
    public int getEngagementPoints(int userId, int clubId) {
        ResultSet rs = executeQueryPreparedStatement("SELECT points FROM engagements WHERE userId=? AND clubId=?", userId, clubId);
        try {
            if (rs.next()) {
                return rs.getInt("points");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EngagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public String getEngagementsPointsRanking(int userId, int clubId) {
        ResultSet rs = executeQueryPreparedStatement("""
                                                    SELECT rank, points
                                                    FROM (
                                                     	SELECT
                                                     		ROW_NUMBER() OVER (ORDER BY points desc) AS rank,
                                                     		userId,
                                                     		points
                                                     	FROM engagements
                                                     	WHERE clubId=?
                                                    ) AS rankingTable
                                                    WHERE userId = ?""", clubId, userId);
        try {
            if (rs.next()) {
                return """
                        {
                            "rank": "%d",
                            "points": "%d"
                        }""".formatted(rs.getInt("rank"), rs.getInt("points"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EngagementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "{}";
    }
    
    public String[] getRoleByUserIdAndClubId(int userId, int clubId) {
        try {
            ResultSet rs = executeQueryPreparedStatement(
                    "SELECT roles.id AS [roleId], roles.name AS [roleName] "
                    + "FROM engagements "
                    + "INNER JOIN roles "
                    + "ON engagements.roleId = roles.id "
                    + "WHERE userId=? AND clubId=?",
                    userId,
                    clubId
            );
            if (rs.next()) {
                String res[] = new String[2];
                res[0] = rs.getInt("roleId") + "";
                res[1] = rs.getString("roleName");
                return res;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new String[]{"-1", ""};
    }
    public int ApproveApplication(String id) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE engagements SET  roleId=1, status=1 WHERE id=?",
                id
             
        );
        return ketQua;

    }
     public int RejectApplication(String id) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE engagements SET roleId=NULL, status=3 WHERE id=?",
                id
             
        );
        return ketQua;

    }
     public int InterviewApplication(String id) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE engagements SET  status=2 WHERE id=?",
                id
             
        );
        return ketQua;

    }

}
