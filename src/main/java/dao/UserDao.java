/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO extends DBContext {

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        User user = null;

        String sql = "SELECT * FROM [users]";
        try ( PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
//     public User(String email, String username, String password, String avatarURL, String firstname, String lastname, String studentCode, String phoneNumber, String major, String academicYear, String gender, String dob, String homeTown, String facebookUrl, String linkedInUrl, Date createdAt, Date updatedAt) 
            while (rs.next()) {
                // Use the constructor with all user properties
                user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("avatarURL"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("studentCode"),
                        rs.getString("phoneNumber"),
                        rs.getString("major"),
                        rs.getString("academicYear"),
                        rs.getString("gender"),
                        rs.getString("dob"),
                        rs.getString("homeTown"),
                        rs.getString("facebookUrl"),
                        rs.getString("linkedInUrl"),
                        rs.getDate("createdAt"),
                        rs.getDate("updatedAt")
                );

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public User getUserById(int id) {
        User user = null;
        String sql = "SELECT * FROM [users] WHERE id = ?";
        try ( PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                // Use the constructor with all user properties
                user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("avatarURL"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("studentCode"),
                        rs.getString("phoneNumber"),
                        rs.getString("major"),
                        rs.getString("academicYear"),
                        rs.getString("gender"),
                        rs.getString("dob"),
                        rs.getString("homeTown"),
                        rs.getString("facebookUrl"),
                        rs.getString("linkedInUrl"),
                        rs.getDate("createdAt"),
                        rs.getDate("updatedAt")
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean addUser(User user) {

        String sql = "INSERT INTO [users] (email, username, password, avatarURL, firstname, lastname, studentCode, phoneNumber, major, academicYear, gender, dob, homeTown, facebookUrl, linkedInUrl, createdAt, updatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, user.getEmail());
            st.setString(2, user.getUsername());
            st.setString(3, user.getPassword());
            st.setString(4, user.getAvatarURL());
            st.setString(5, user.getFirstname());
            st.setString(6, user.getLastname());
            st.setString(7, user.getStudentCode());
            st.setString(8, user.getPhoneNumber());
            st.setString(9, user.getMajor());
            st.setString(10, user.getAcademicYear());
            st.setString(11, user.getGender());
            st.setString(12, user.getDob());
            st.setString(13, user.getHomeTown());
            st.setString(14, user.getFacebookUrl());
            st.setString(15, user.getLinkedInUrl());

            st.setDate(16, (Date) user.getCreatedAt());
            st.setDate(17, (Date) user.getUpdatedAt());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE [users] SET "
                + "email = ?, username = ?, password = ?, avatarURL = ?, firstname = ?, lastname = ?, "
                + "studentCode = ?, phoneNumber = ?, major = ?, academicYear = ?, gender = ?, "
                + "dob = ?, homeTown = ?, facebookUrl = ?, linkedInUrl = ?, updatedAt = ? "
                + "WHERE id = ?";
        try ( PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, user.getEmail());
            st.setString(2, user.getUsername());
            st.setString(3, user.getPassword());
            st.setString(4, user.getAvatarURL());
            st.setString(5, user.getFirstname());
            st.setString(6, user.getLastname());
            st.setString(7, user.getStudentCode());
            st.setString(8, user.getPhoneNumber());
            st.setString(9, user.getMajor());
            st.setString(10, user.getAcademicYear());
            st.setString(11, user.getGender());
            st.setString(12, user.getDob());
            st.setString(13, user.getHomeTown());
            st.setString(14, user.getFacebookUrl());
            st.setString(15, user.getLinkedInUrl());
            st.setDate(16, (Date) user.getUpdatedAt());
            st.setInt(17, user.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM [users] WHERE id = ?";
        try ( PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
