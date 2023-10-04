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
import nextteam.models.Department;
import nextteam.models.HomeTown;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class DepartmentDAO extends SQLDatabase {

    public DepartmentDAO(Connection connection) {
        super(connection);
    }

    public List<Department> getAllDepartments(String t) {
        List<Department> departments = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM departments WHERE clubId = ?",t);
        try {
            while (rs.next()) {
                Department ht = new Department(rs.getInt(1), rs.getInt(2), rs.getNString(3));
                departments.add(ht);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return departments;
    }
    public Department getDepartmentById(String id) {
        Department ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM departments WHERE id=?", id);
            if (rs.next()) {
                ketQua = new Department(rs.getInt(1), rs.getInt(2), rs.getNString(3));
            }
        } catch (Exception e) {
        }
        return ketQua;
    }
}
