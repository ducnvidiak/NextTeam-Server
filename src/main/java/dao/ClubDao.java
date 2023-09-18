/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Club;

public class ClubDAO extends DBContext {

    public List<Club> getAllClubs() {
        List<Club> clubList = new ArrayList<>();
        Club club = null;

        String sql = "SELECT * FROM [clubs]";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                club = new Club(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("avatarUrl"),
                    rs.getInt("movementPoint"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );

                clubList.add(club);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clubList;
    }

    public Club getClubById(int id) {
        Club club = null;
        String sql = "SELECT * FROM [clubs] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                club = new Club(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("avatarUrl"),
                    rs.getInt("movementPoint"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return club;
    }

    public boolean addClub(Club club) {
        String sql = "INSERT INTO [clubs] (name, description, avatarUrl, movementPoint, createdAt, updatedAt) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, club.getName());
            st.setString(2, club.getDescription());
            st.setString(3, club.getAvatarUrl());
            st.setInt(4, club.getMovementPoint());
            st.setDate(5, (java.sql.Date) club.getCreatedAt());
            st.setDate(6, (java.sql.Date) club.getUpdatedAt());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateClub(Club club) {
        String sql = "UPDATE [clubs] SET " +
                     "name = ?, description = ?, avatarUrl = ?, movementPoint = ?, updatedAt = ? " +
                     "WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, club.getName());
            st.setString(2, club.getDescription());
            st.setString(3, club.getAvatarUrl());
            st.setInt(4, club.getMovementPoint());
            st.setDate(5, (java.sql.Date) club.getUpdatedAt());
            st.setInt(6, club.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteClub(int clubId) {
        String sql = "DELETE FROM [clubs] WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, clubId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

