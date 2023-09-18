package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Role;

public class RoleDAO extends DBContext {

    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        Role role = null;

        String sql = "SELECT * FROM roles";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                role = new Role(
                    rs.getInt("id"),
                    rs.getString("name")
                );

                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    public Role getRoleById(int id) {
        Role role = null;
        String sql = "SELECT * FROM roles WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                role = new Role(
                    rs.getInt("id"),
                    rs.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }

    public boolean addRole(Role role) {
        String sql = "INSERT INTO roles (name) VALUES (?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, role.getName());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRole(Role role) {
        String sql = "UPDATE roles SET name = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, role.getName());
            st.setInt(2, role.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRole(int roleId) {
        String sql = "DELETE FROM roles WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, roleId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
