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
import nextteam.models.Role;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class RoleDAO extends SQLDatabase {

    public RoleDAO(Connection connection) {
        super(connection);
    }

    public List<Role> getAllRole() {
        List<Role> roles = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM roles");
        try {
            while (rs.next()) {
                Role ht = new Role(rs.getInt(1), rs.getNString(2));
                roles.add(ht);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;
    }

    public Role getRoleById(String id) {
        Role ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM roles WHERE id=?", id);
            if (rs.next()) {
                ketQua = new Role(rs.getInt(1), rs.getNString(2));
            }
        } catch (Exception e) {
        }
        return ketQua;
    }
}
