/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import nextteam.utils.database.MajorDAO;

/**
 *
 * @author vnitd
 */
public class Global {

    public static String server = "localhost";
    public static String database = "NextTeam";
    public static String user = "sa";
    public static String pass = "Phanbao@123";

    private static Connection conn;

    public static MajorDAO major;

    private static Connection generateConnection() {
        try {
            Class<?> clazz = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.registerDriver((Driver) clazz.getDeclaredConstructor().newInstance());
            String url = "jdbc:sqlserver://" + server + ";databaseName=" + database + ";user=" + user + ";password=" + pass + ";encrypt=true;trustServerCertificate=true";
            return DriverManager.getConnection(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void setDAOConnection() {
        conn = generateConnection();
        if (conn == null) {
            throw new RuntimeException("Error while trying connect to SQL Server!");
        }
        major = new MajorDAO(conn);
    }

    public static void closeDAOConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            conn = null;
        }
    }
}
