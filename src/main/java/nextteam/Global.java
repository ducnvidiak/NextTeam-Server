/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import nextteam.utils.database.ClubCategoriesDAO;
import nextteam.utils.database.ClubDAO;
import nextteam.utils.database.DepartmentDAO;
import nextteam.utils.database.EngagementDAO;
import nextteam.utils.database.EntranceInterviewDAO;
import nextteam.utils.database.EventDAO;
import nextteam.utils.database.EventRegistrationDAO;
import nextteam.utils.database.FeedbackDAO;
import nextteam.utils.database.LocationDAO;
import nextteam.utils.database.MajorDAO;
import nextteam.utils.database.NotificationDAO;
import nextteam.utils.database.OtpCodeDAO;
import nextteam.utils.database.PointHistoryDAO;
import nextteam.utils.database.PaymentDAO;
import nextteam.utils.database.PrivateNotificationDAO;
import nextteam.utils.database.PublicNotificationDAO;
import nextteam.utils.database.RoleDAO;
import nextteam.utils.database.UserDAO;
import nextteam.utils.encryption.BCrypt;

/**
 *
 * @author vnitd
 */
public class Global {

    public static String server = "localhost";
    public static String database = "NextTeam";
    public static String username = "sa";
    public static String password = "1";

    private static Connection conn;

    public static String workingPath;

    public static ClubDAO clubDAO;
    public static MajorDAO major;
    public static EventDAO eventDao;
    public static UserDAO user;
    public static OtpCodeDAO otpCode;
    public static PublicNotificationDAO publicNotification;
    public static PrivateNotificationDAO privateNotification;
    public static DepartmentDAO department;
    public static EngagementDAO engagement;
    public static RoleDAO role;
    public static NotificationDAO notification;
    public static PointHistoryDAO pointHistory;
    public static EntranceInterviewDAO entranceInterview;
    public static LocationDAO location;
    public static EventRegistrationDAO eventRegistration;
    public static ClubCategoriesDAO clubCategories;
    public static PaymentDAO payment;
    public static FeedbackDAO feedback;

    public static final byte[] KEY = {
        46, -8, -9, 4, 61, -61, 8, 53, 112, 72, 24, -6, 23, -49, -97, 24, -45,
        -20, -40, 91, -119, 20, -31, -69, -115, -114, -58, 37, -72, -20, -85, 116
    };

    public static String getHashedPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public static Connection generateConnection() {
        try {
            Class<?> clazz = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.registerDriver((Driver) clazz.getDeclaredConstructor().newInstance());
            String url = "jdbc:sqlserver://" + server + ";databaseName=" + database + ";user=" + username + ";password=" + password + ";encrypt=true;trustServerCertificate=true";
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
        clubDAO = new ClubDAO(conn);
        major = new MajorDAO(conn);
        eventDao = new EventDAO(conn);
        user = new UserDAO(conn);
        otpCode = new OtpCodeDAO(conn);
        publicNotification = new PublicNotificationDAO(conn);
        privateNotification = new PrivateNotificationDAO(conn);
        department = new DepartmentDAO(conn);
        engagement = new EngagementDAO(conn);
        role = new RoleDAO(conn);
        notification = new NotificationDAO(conn);
        pointHistory = new PointHistoryDAO(conn);
        entranceInterview = new EntranceInterviewDAO(conn);
        location = new LocationDAO(conn);
        eventRegistration = new EventRegistrationDAO(conn);
        clubCategories = new ClubCategoriesDAO(conn);
        payment = new PaymentDAO(conn);
        feedback = new FeedbackDAO(conn);
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

    public static Cookie getCookie(HttpServletRequest req, String name) {
        for (Cookie cookie : req.getCookies()) {
            System.out.println(cookie);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(generateConnection());

    }
}
