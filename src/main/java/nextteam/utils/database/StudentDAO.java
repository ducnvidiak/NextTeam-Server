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
import nextteam.models.Student;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class StudentDAO extends SQLDatabase {

    public StudentDAO(Connection connection) {
        super(connection);
    }

    public int add(Student student) {
        return executeUpdatePreparedStatement(
                "INSERT INTO students (id, firstname, lastname, birthDate, homeTown, gender) VALUES (?, ?, ?, ?, ?, ?)",
                student.getId(),
                student.getFirstname(),
                student.getLastname(),
                student.getBirthDate(),
                student.getHomeTown(),
                student.getGender()
        );
    }

    public List<Student> filterAll(int filter) {
        List<Student> res = new ArrayList<>();
        ResultSet rs;
        if (filter > -1) {
            rs = executeQueryPreparedStatement("SELECT * FROM students WHERE isRegistered=?", filter);
        } else {
            rs = executeQueryPreparedStatement("SELECT * FROM students");
        }
        try {
            while (rs.next()) {
                res.add(new Student(
                        rs.getString("id"),
                        rs.getNString("firstname"),
                        rs.getNString("lastname"),
                        rs.getDate("birthDate"),
                        rs.getNString("homeTown"),
                        rs.getNString("gender"),
                        rs.getBoolean("isRegistered")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    public Student check(String id) {
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM students WHERE id=? AND isRegistered=0", id);
        try {
            if (rs.next()) {
                return new Student(
                        rs.getString("id"),
                        rs.getNString("firstname"),
                        rs.getNString("lastname"),
                        rs.getDate("birthDate"),
                        rs.getNString("homeTown"),
                        rs.getNString("gender"),
                        rs.getBoolean("isRegistered")
                );

            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int delete(String id) {
        return executeUpdatePreparedStatement("DELETE FROM students WHERE id=?", id);
    }

    public int register(String id) {
        return executeUpdatePreparedStatement("UPDATE students SET isRegistered=? WHERE id=?", 1, id);
    }
}
