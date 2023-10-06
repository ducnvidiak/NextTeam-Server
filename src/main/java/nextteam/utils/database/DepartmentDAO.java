/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
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

    public ArrayList<Department> getListDepartment() {
        ArrayList<Department> list = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM departments");
        try {

            while (rs.next()) {
                Department d = new Department(rs.getInt(1), rs.getInt(2), rs.getString(3));
                list.add(d);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
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
        rs = executeUpdatePreparedStatement("UPDATE departments  SET clubid=? WHERE id=?",
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

    public static void main(String[] args) {
        DepartmentDAO dp = new DepartmentDAO(Global.generateConnection());
        System.out.println(dp.getListDepartment());
    }
}
