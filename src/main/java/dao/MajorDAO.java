/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Major;

public class MajorDAO extends DBContext {

    public List<Major> getAllMajors() {
        List<Major> majorList = new ArrayList<>();
        Major major = null;

        String sql = "SELECT * FROM majors";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                major = new Major(
                    rs.getInt("id"),
                    rs.getString("name")
                );

                majorList.add(major);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return majorList;
    }

    public Major getMajorById(int id) {
        Major major = null;
        String sql = "SELECT * FROM majors WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                major = new Major(
                    rs.getInt("id"),
                    rs.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return major;
    }

    public boolean addMajor(Major major) {
        String sql = "INSERT INTO majors (name) VALUES (?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, major.getName());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMajor(Major major) {
        String sql = "UPDATE majors SET name = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, major.getName());
            st.setInt(2, major.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMajor(int majorId) {
        String sql = "DELETE FROM majors WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, majorId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
