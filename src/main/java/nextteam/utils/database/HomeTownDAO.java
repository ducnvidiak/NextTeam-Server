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
import nextteam.models.HomeTown;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class HomeTownDAO extends SQLDatabase {

    public HomeTownDAO(Connection connection) {
        super(connection);
    }

    public List<HomeTown> getAllHomeTowns() {
        List<HomeTown> homeTowns = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM majors");
        try {
            while (rs.next()) {
                HomeTown ht = new HomeTown(rs.getInt(1), rs.getNString(2), rs.getNString(3));
                homeTowns.add(ht);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeTownDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return homeTowns;
    }

}
