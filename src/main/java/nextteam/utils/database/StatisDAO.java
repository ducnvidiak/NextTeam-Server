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
import nextteam.Global;
import nextteam.models.Club;
import nextteam.models.ClubCounter;
import nextteam.models.User;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class StatisDAO extends SQLDatabase {

    public StatisDAO(Connection connection) {
        super(connection);
    }

    public List<ClubCounter> getClubData() {
        List<ClubCounter> clubs = new ArrayList<>();
        String sql = "SELECT \n" +
"    c.name AS ClubName, \n" +
"    COUNT(DISTINCT e.id) AS EventCount, \n" +
"    COUNT(DISTINCT eng.userId) AS MemberCount, \n" +
"    c.balance AS Balance, \n" +
"    c.movementPoint AS ActivityPoints,\n" +
"    eUpcoming.name AS UpcomingEventName,\n" +
"    eUpcoming.endTime AS UpcomingEventEndTime,\n" +
"    COUNT(DISTINCT p.id) AS PlanCount\n" +
"FROM \n" +
"    dbo.clubs c\n" +
"LEFT JOIN \n" +
"    dbo.events e ON c.id = e.clubId\n" +
"LEFT JOIN \n" +
"    dbo.engagements eng ON c.id = eng.clubId\n" +
"LEFT JOIN \n" +
"    dbo.plans p ON c.id = p.clubId\n" +
"LEFT JOIN \n" +
"    (SELECT clubId, name, MIN(endTime) AS endTime \n" +
"    FROM dbo.events \n" +
"    WHERE endTime > GETDATE() \n" +
"    GROUP BY clubId, name) eUpcoming ON c.id = eUpcoming.clubId\n" +
"GROUP BY\n" +
"    c.name, c.balance, c.movementPoint, eUpcoming.name, eUpcoming.endTime";

        try {
            ResultSet rs = executeQueryPreparedStatement(sql);
            while (rs.next()) {
    ClubCounter club = new ClubCounter();
    club.setName(rs.getString("ClubName"));
    club.setEventCount(rs.getInt("EventCount"));
    club.setMemberCount(rs.getInt("MemberCount"));
    club.setBalance(rs.getDouble("Balance"));
    club.setActiviyPoint(rs.getInt("ActivityPoints"));
    
    
    if(rs.getString("UpcomingEventName") == null){
        club.setUpcomingEventName("Không có sự kiện diễn ra sắp tới");
    }else{
        club.setUpcomingEventName(rs.getString("UpcomingEventName"));
    }
    
    // check if UpcomingEventEndTime is null, if it is, set a default value
    if(rs.getString("UpcomingEventEndTime") == null){
        club.setUpcomingEventTime("Không xác định"); // Update this to your desired default message
    }else{
        club.setUpcomingEventTime(rs.getString("UpcomingEventEndTime"));
    }
    
    club.setPlanCount(rs.getInt("PlanCount"));

    clubs.add(club);
}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clubs;
    }

    public int getNumberClub() {
        String sql = "select count(*) from clubs";
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;
    }

    public int getNumberEventsOccur(String clubId) {
        String sql = "SELECT COUNT(*) AS 'Number of Events Occurred'\n"
                + "FROM dbo.events\n"
                + "WHERE clubId = 1 AND endTime < GETDATE()";
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;
    }

    public int getNumberEventsNotOccur(String clubId) {
        String sql = "SELECT COUNT(*) AS 'Number of Events Occurred'\n"
                + "FROM dbo.events\n"
                + "WHERE clubId = 1 AND startTime > GETDATE()";
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;
    }

    public User getManager(String clubId) {
        String sql = "SELECT u.firstname,u.lastname,u.avatarURL,u.username,u.id\n"
                + "FROM dbo.users AS u\n"
                + "INNER JOIN dbo.engagements AS e ON u.id = e.userId\n"
                + "INNER JOIN dbo.roles AS r ON e.roleId = r.id\n"
                + "INNER JOIN dbo.clubs AS c ON e.clubId = c.id\n"
                + "WHERE c.id = '" + clubId + "' AND r.name = 'manager'";
        ResultSet rs = executeQueryPreparedStatement(sql);
        User u = new User();
        try {
            if (rs.next()) {
                u.setFirstname(rs.getString(1));
                u.setLastname(rs.getString(2));
                u.setAvatarURL(rs.getString(3));
                u.setUsername(rs.getString(4));
                u.setId(rs.getInt(5));
                return u;
            }

        } catch (Exception e) {
        }
        return u;
    }

    public int getNumberMemberInClub(String clubId) {
        String sql = "SELECT  count(e.id)  as number_egagements   FROM    engagements AS  e JOIN   clubs  AS  C  ON   e.clubId=c.id  where  clubId    =" + clubId;
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;
    }

    public int getNumberMemberOutClub(String clubId) {
        String sql = "SELECT  count(e.id)  as number_egagements   FROM    engagements AS  e JOIN   clubs  AS  C  ON   e.clubId=c.id  where  clubId    =" + clubId + "and e.status = -1";
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;
    }

    public int getNumbeMemberBannedInClub(String clubId) {
        String sql = "SELECT\n"
                + "COUNT(e.id)\n"
                + "FROM [NextTeam].[dbo].[engagements] e\n"
                + "INNER JOIN [NextTeam].[dbo].[users] u ON e.userId = u.id\n"
                + "INNER JOIN [NextTeam].[dbo].[clubs] c ON e.clubId = c.id\n"
                + "where clubId ='" + clubId + "'\n"
                + "and \n"
                + "u.isActive=0";
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;
    }

    public int getNumberEvent(String clubId) {
        String sql = "SELECT COUNT(*) AS SoLuongEvent\n"
                + "FROM [NextTeam].[dbo].[events]\n"
                + "WHERE clubId = " + clubId;
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {
            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;

    }

    public int[] getNumberEventEachMonth(String clubId) {

        int[] events = new int[12];

        String sql = "SELECT ISNULL([1], 0) AS Thang1, ISNULL([2], 0) AS Thang2, ISNULL([3], 0) AS Thang3, ISNULL([4], 0) AS Thang4,\n"
                + "       ISNULL([5], 0) AS Thang5, ISNULL([6], 0) AS Thang6, ISNULL([7], 0) AS Thang7, ISNULL([8], 0) AS Thang8,\n"
                + "       ISNULL([9], 0) AS Thang9, ISNULL([10], 0) AS Thang10, ISNULL([11], 0) AS Thang11, ISNULL([12], 0) AS Thang12\n"
                + "FROM (\n"
                + "    SELECT MONTH([startTime]) AS Thang, COUNT(*) AS SoLuongEvent\n"
                + "    FROM [NextTeam].[dbo].[events]\n"
                + "    WHERE clubId = '" + clubId + "'\n"
                + "    GROUP BY MONTH([startTime])\n"
                + ") AS SourceTable\n"
                + "PIVOT (\n"
                + "    MAX(SoLuongEvent)\n"
                + "    FOR Thang IN ([1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12])\n"
                + ") AS PivotTable";
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {

            if (rs.next()) {
                for (int i = 0; i < 11; i++) {
                    events[i] = rs.getInt("Thang" + (i + 1));
                }
            }

        } catch (Exception e) {
        }
        return events;
    }

    public int[] getNumberEngaEachMonth(String clubId) {
        int[] engas = new int[12];

        String sql = "WITH MonthlyEngagements AS (\n"
                + "    SELECT \n"
                + "        MONTH(e.createdAt) AS 'Month', \n"
                + "        YEAR(e.createdAt) AS 'Year', \n"
                + "        COUNT(*) AS 'Engagements'\n"
                + "    FROM \n"
                + "        dbo.engagements e\n"
                + "    WHERE \n"
                + "        e.clubId = '" + clubId + "'\n"
                + "    GROUP BY \n"
                + "        YEAR(e.createdAt), \n"
                + "        MONTH(e.createdAt)\n"
                + ")\n"
                + "SELECT \n"
                + "       [1] AS 'Jan', [2] AS 'Feb', [3] AS 'Mar', [4] AS 'Apr', \n"
                + "       [5] AS 'May', [6] AS 'Jun', [7] AS 'Jul', [8] AS 'Aug', \n"
                + "       [9] AS 'Sep', [10] AS 'Oct', [11] AS 'Nov', [12] AS 'Dec'\n"
                + "FROM \n"
                + "(\n"
                + "    SELECT Year, Month, Engagements\n"
                + "    FROM MonthlyEngagements\n"
                + ") AS SourceTable\n"
                + "PIVOT\n"
                + "(\n"
                + "    MAX(Engagements)\n"
                + "    FOR Month IN ([1], [2], [3], [4], [5], [6], [7], [8], [9], [10], [11], [12])\n"
                + ") AS PivotTable;";
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {

            if (rs.next()) {
                for (int i = 0; i < 11; i++) {
                    engas[i] = rs.getInt((i + 1));
                }
            }

        } catch (Exception e) {
        }
        return engas;
    }

    public int getActivityPoint(String clubId) {
        String sql = "select movementPoint \n"
                + "from clubs\n"
                + "where id=" + clubId;
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;

    }

    public int getBalanceClub(String clubId) {
        String sql = "select balance \n"
                + "from clubs\n"
                + "where id=" + clubId;
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;

    }

    public int getTotalReport(String clubId) {
        String sql = "select count(*)\n"
                + "from \n"
                + "plans \n"
                + "where \n"
                + "clubId =" + clubId;
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;
    }

    public int getTotalReports() {
        String sql = "select count(*)\n"
                + "from \n"
                + "plans";

        ResultSet rs = executeQueryPreparedStatement(sql);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;
    }

    public int getTotalPost(String clubId) {
        String sql = "select count(*)\n"
                + "from \n"
                + "posts \n"
                + "where \n"
                + "clubId =" + clubId;
        ResultSet rs = executeQueryPreparedStatement(sql);
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

        }
        return 0;
    }

    public static void main(String... args) {
        System.out.println(new StatisDAO(Global.generateConnection()).getTotalReport("1"));
    }

}
