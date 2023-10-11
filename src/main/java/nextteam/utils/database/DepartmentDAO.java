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
import nextteam.models.Department;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author bravee06
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

    public int addDepartment(Department d) {
        int rs = 0;
        rs = executeUpdatePreparedStatement(
                "INSERT INTO departments (clubid,name) VALUES (?, ?)",
                d.getClubId(),
                d.getName()
        );
        return rs;
    }

    public int updateClub(Department d, int id) {
        int rs = 0;
        rs = executeUpdatePreparedStatement("UPDATE departments  SET clubId=?,name=? WHERE id=?",
                d.getClubId(),
                d.getName(),
                id);
        return rs;
    }

    public int deleteClub(int id) {
        int rs = 0;
        rs = executeUpdatePreparedStatement("DELETE from departments  WHERE id=?", id);
        return rs;
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
    
    public static void main(String[] args) {
        DepartmentDAO dp = new DepartmentDAO(Global.generateConnection());
        System.out.println(dp.getAllDepartments("1"));
    }
}
