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
import model.Department;

/**
 *
 * @author bravee06
 */
public class DepartmentDAO extends DBContext {

    public List<Department> getDepartmentBySearch(String find_keyword) {
        List<Department> listDepartment = new ArrayList<>();
        Department b = null;

        String sql = "SELECT * FROM [departments] WHERE name LIKE ?";
        try ( PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + find_keyword + "%");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                b = new Department();
                b.setId(rs.getInt(1));
                b.setClubId(rs.getInt(2));
                b.setName(rs.getString(3));
                listDepartment.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return listDepartment;
    }

    public List<Department> getAllDepartment() {
        List<Department> listDepartment = new ArrayList<>();
        Department b = null;
        String sql = "select * from [NextTeam].[dbo].[departments]";
        try ( PreparedStatement st = connection.prepareStatement(sql)) {

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                b = new Department();

                b.setId(rs.getInt(1));
                b.setClubId(rs.getInt(2));
                b.setName(rs.getString(3));
                listDepartment.add(b);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return listDepartment;
    }

    // delete a category
    public boolean delete(int id) {
        String sql = "DELETE FROM [NextTeam].[dbo].[departments]\n"
                + "    WHERE id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            if (st.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean updateDepartment(Department department) throws SQLException {

        String sql = "UPDATE [NextTeam].[dbo].[departments] \n"
                + "SET\n"
                + "		 [clubId]=?\n"
                + "		,[name]=?\n"
                + "";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set the parameter values of the PreparedStatement
            statement.setInt(1, department.getClubId());
            statement.setString(2, department.getName());
            // Execute the SQL statement
            if (statement.executeUpdate() == 1) {
                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;

    }

    public Department getDepartmentById(int id) throws SQLException {
        Department b = null;
        try {
            String sql = "select * from department where id=?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                b = new Department();
                b.setId(rs.getInt(1));
                b.setClubId(rs.getInt(2));
                b.setName(rs.getString(3));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return b;
    }

    public boolean addDepartment(Department department) {

        String sql = "INSERT INTO [NextTeam].[dbo].[departments]\n"
                + "(\n"
                + "		 [clubId]\n"
                + "		,[name]\n"
                + ")\n"
                + "VALUES\n"
                + "(\n"
                + "		?\n"
                + "		,?";

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set the parameter values of the PreparedStatement
           statement.setInt(1, department.getClubId());
           statement.setString(2, department.getName());

            // Execute the SQL statement
            if (statement.executeUpdate() == 1) {
                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

   

}
