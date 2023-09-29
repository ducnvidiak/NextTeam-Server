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
import nextteam.models.User;
import nextteam.utils.ConvertPassword;
import nextteam.utils.SQLDatabase;
import org.apache.http.ParseException;

/**
 *
 * @author vnitd
 */
public class UserDAO extends SQLDatabase {

    public UserDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<User> getListUsers() {
        ArrayList<User> list = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users");
        try {
            while (rs.next()) {
                list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19), rs.getBoolean(20)));
            }

        } catch (Exception e) {
            Logger.getLogger(HomeTownDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public User getListUserById(User t) {
        User ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE id=?", t.getId());
            if (rs.next()) {
                ketQua = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19),rs.getBoolean(20));
            }

        } catch (Exception e) {
        }
        return ketQua;
    }

    public User getListUserByIdString(String t) {
        User ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE id=?", t);
            if (rs.next()) {
                ketQua = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19),rs.getBoolean(20));
            }
        } catch (Exception e) {
        }
        return ketQua;
    }

    public int delete(String id) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement("DELETE from users  WHERE id=?", id);
        return ketQua;
    }

    public int insert(final User t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "INSERT INTO users (email, username, password, avatarUrl, bannerUrl, firstname, lastname, studentCode, phoneNumber,major,academicYear,gender,dob,homeTown,facebookUrl,linkedInUrl)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                t.getEmail(),
                t.getUsername(),
                t.getPassword(),
                t.getAvatarURL(),
                t.getBannerURL(),
                t.getFirstname(),
                t.getLastname(),
                t.getStudentCode(),
                t.getPhoneNumber(),
                t.getMajor(),
                t.getAcademicYear(),
                t.getGender(),
                t.getDob(),
                t.getHomeTown(),
                t.getFacebookUrl(),
                t.getLinkedInUrl()
        );
        return ketQua;
    }

    public int register(final User t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "INSERT INTO users (email, avatarUrl, username, password, firstname, lastname, studentCode, phoneNumber, gender)  VALUES (?,?,?,?,?,?,?,?,?)",
                t.getEmail(),
                t.getAvatarURL(),
                t.getUsername(),
                t.getPassword(),
                t.getFirstname(),
                t.getLastname(),
                t.getStudentCode(),
                t.getPhoneNumber(),
                t.getGender()
        );
        return ketQua;
    }

    public int insertAll(ArrayList<User> arr) {
        int dem = 0;
        for (final User user : arr) {
            dem += this.insert(user);
        }
        return dem;
    }

    public int update(User t) {
        int ketQua = 0;
        System.out.println("dob: " + t.getDob());
        ketQua = executeUpdatePreparedStatement(
                "UPDATE users  SET  email=?, username=?, avatarUrl=?, firstname=?, lastname=?, studentCode=?, phoneNumber=?,major=?,academicYear=?,gender=?,dob=?,homeTown=?,facebookUrl=?,linkedInUrl=? WHERE id=?",
                t.getEmail(),
                t.getUsername(),
                t.getAvatarURL(),
                t.getFirstname(),
                t.getLastname(),
                t.getStudentCode(),
                t.getPhoneNumber(),
                t.getMajor(),
                t.getAcademicYear(),
                t.getGender(),
                t.getDob(),
                t.getHomeTown(),
                t.getFacebookUrl(),
                t.getLinkedInUrl(),
                t.getId()
        );
        return ketQua;

    }

    public int updateAvatar(User t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE users  SET  avatarUrl=? WHERE id=?",
                t.getAvatarURL(),
                t.getId()
        );
        return ketQua;
    }

    public int updatePassword(User t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE users  SET  password=? WHERE id=?",
                t.getPassword(),
                t.getId()
        );
        return ketQua;
    }

    public User selectByEmailAndPassword(User t) {
        User ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE email=? and password=?", t.getEmail(), t.getPassword());
            if (rs.next()) {
                ketQua = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19),rs.getBoolean(20));
            }
        } catch (Exception e) {
        }

        return ketQua;
    }

    public User selectByEmail(String email) {
        User ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE email=?", email);
            if (rs.next()) {
                ketQua = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getNString(7), rs.getNString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19),rs.getBoolean(20));
            }
        } catch (Exception e) {
        }

        return ketQua;
    }

    public boolean StudentCodeCheck(String studentCode) {
        boolean ketQua = false;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM users WHERE studentCode = ?", studentCode);
            while (rs.next()) {
                ketQua = true;
            }
        } catch (SQLException e) {
        }
        return ketQua;
    }

    // test connection 
    public void changePassword(String studentCode, String password) {
        password = ConvertPassword.toSHA1(password);
        executeUpdatePreparedStatement("UPDATE users SET password=? WHERE studentCode=?", password, studentCode);
    }

    public static void main(String... args) {
        User user = new UserDAO(Global.generateConnection()).getListUserByIdString("2");
        System.out.println("Data from mssql: " + user.getFirstname());
    }

}
