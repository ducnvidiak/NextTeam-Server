/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextteam.Global;
import nextteam.models.Club;
import nextteam.models.User;
import nextteam.utils.SQLDatabase;
import nextteam.utils.encryption.BCrypt;
import org.apache.http.ParseException;

/**
 *
 * @author vnitd
 */
public class StatisDAO extends SQLDatabase {

    public StatisDAO(Connection connection) {
        super(connection);
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
