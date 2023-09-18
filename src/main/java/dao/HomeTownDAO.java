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
import model.HomeTown;

public class HomeTownDAO extends DBContext {

    public List<HomeTown> getAllHomeTowns() {
        List<HomeTown> homeTownList = new ArrayList<>();
        HomeTown homeTown = null;

        String sql = "SELECT * FROM [homeTowns]";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                homeTown = new HomeTown(
                    rs.getInt("id"),
                    rs.getString("country"),
                    rs.getString("city")
                );

                homeTownList.add(homeTown);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return homeTownList;
    }

    public HomeTown getHomeTownById(int id) {
        HomeTown homeTown = null;
        String sql = "SELECT * FROM [homeTowns] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                homeTown = new HomeTown(
                    rs.getInt("id"),
                    rs.getString("country"),
                    rs.getString("city")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return homeTown;
    }

    public boolean addHomeTown(HomeTown homeTown) {
        String sql = "INSERT INTO [homeTowns] (country, city) VALUES (?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, homeTown.getCountry());
            st.setString(2, homeTown.getCity());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateHomeTown(HomeTown homeTown) {
        String sql = "UPDATE [homeTowns] SET country = ?, city = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, homeTown.getCountry());
            st.setString(2, homeTown.getCity());
            st.setInt(3, homeTown.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHomeTown(int homeTownId) {
        String sql = "DELETE FROM [homeTowns] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, homeTownId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
