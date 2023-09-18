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
import nextteam.models.Major;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class MajorDAO extends SQLDatabase {

    public MajorDAO(Connection connection) {
        super(connection);
    }

    public List<Major> getAllMajors() {
        List<Major> majors = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM majors");
        try {
            while (rs.next()) {
                Major m = new Major(rs.getInt(1), rs.getNString(2));
                majors.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MajorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return majors;
    }

}
