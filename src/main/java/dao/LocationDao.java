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
import model.Location;

public class LocationDao extends DBContext {

    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<>();
        Location location = null;

        String sql = "SELECT * FROM [locations]";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                location = new Location(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description")
                );

                locationList.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locationList;
    }

    public Location getLocationById(int id) {
        Location location = null;
        String sql = "SELECT * FROM [locations] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                location = new Location(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return location;
    }

    public boolean addLocation(Location location) {
        String sql = "INSERT INTO [locations] (name, description) VALUES (?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, location.getName());
            st.setString(2, location.getDescription());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLocation(Location location) {
        String sql = "UPDATE [locations] SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, location.getName());
            st.setString(2, location.getDescription());
            st.setInt(3, location.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLocation(int locationId) {
        String sql = "DELETE FROM [locations] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, locationId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
