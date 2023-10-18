/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextteam.Global;
import nextteam.models.Location;
import nextteam.models.Club;
import nextteam.models.ClubCategories;
import nextteam.models.User;
import nextteam.models.response.ClubResponse;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class LocationDAO extends SQLDatabase {

    public LocationDAO(Connection connection) {
        super(connection);
    }
//     public Club(int id, String name, String subname, String categoryId, String description, String avatarUrl, String bannerUrl, int movementPoint, double balance, Date createdAt, Date updatedAt) {

    public ArrayList<Location> getLocationList() {
        ArrayList<Location> list = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT id, name FROM locations");
        try {
            while (rs.next()) {
                list.add(new Location(rs.getInt("id"), rs.getString("name")));
            }

        } catch (Exception e) {
//            Logger.getLogger(HomeTownDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
    
    public Location getLocationById(String id) {
        Location ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM locations WHERE id=?", id);
            if (rs.next()) {
                ketQua = new Location(rs.getInt(1), rs.getNString(2));
            }
        } catch (Exception e) {
        }
        return ketQua;
    }
}


