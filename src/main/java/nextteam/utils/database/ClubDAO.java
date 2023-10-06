/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextteam.Global;
import nextteam.models.Club;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class ClubDAO extends SQLDatabase {

    public ClubDAO(Connection connection) {
        super(connection);
    }
//     public Club(int id, String name, String subname, String categoryId, String description, String avatarUrl, String bannerUrl, int movementPoint, double balance, Date createdAt, Date updatedAt) {

    public ArrayList<Club> getListClubs() {
        ArrayList<Club> list = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM clubs");
        try {

            while (rs.next()) {
                //     public Club(int id, String name, String subname, int categoryId, String description, String avatarUrl, String bannerUrl, int movementPoint, double balance, Date createdAt, Date updatedAt) {

                list.add(new Club(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getDouble(9), rs.getDate(10), rs.getDate(11),rs.getBoolean(12)));
            
            }   

        } catch (Exception e) {
            Logger.getLogger(HomeTownDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public ArrayList<Club> getListClubsOfMe(String userId) {
        ArrayList<Club> list = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT clubs.id, clubs.name, clubs.subname FROM engagements INNER JOIN clubs ON engagements.clubId = clubs.id WHERE engagements.userId =?", userId);
        try {

            while (rs.next()) {
                list.add(new Club(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }   

        } catch (Exception e) {
            Logger.getLogger(ClubDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
    //    "INSERT INTO clubs (name,subname,categoryId, description, avatarUrl,bannerUrl, movementPoint,balance,createdAt,updatedAt) VALUES (?,?,?,?)",

    public int addClub(Club c) {
        int rs = 0;
        rs = executeUpdatePreparedStatement(
                "INSERT INTO clubs (name,subname,categoryId, description, avatarUrl,bannerUrl, movementPoint,balance,isActive) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)",
                c.getName(),
                c.getSubname(),
                c.getCategoryId(),
                c.getDescription(),
                c.getAvatarUrl(),
                c.getBannerUrl(),
                c.getMovementPoint(),
                c.getBalance(),
                c.isIsActive()
        );
        return rs;
    }

    public int updateClub(Club c, int id) {
        int rs = 0;
        rs = executeUpdatePreparedStatement("UPDATE clubs  SET name=?,subname=?,categoryId=?,description=?,avatarUrl=?,bannerUrl=?,movementPoint=?,balance=?,isActive=?,updatedAt=CURRENT_TIMESTAMP WHERE id=?",
                c.getName(),
                c.getSubname(),
                c.getCategoryId(),
                c.getDescription(),
                c.getAvatarUrl(),
                c.getBannerUrl(),
                c.getMovementPoint(),
                c.getBalance(),
                c.isIsActive(),
                id);
        return rs;
    }

    public int deleteClub(int id) {
        int rs = 0;
        rs = executeUpdatePreparedStatement("DELETE from clubs  WHERE id=?", id);
        return rs;
    }

    public Club getClubById(String id) {
        Club ketQua = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT * FROM clubs WHERE id=?", id);
            if (rs.next()) {
                ketQua = new Club(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getDouble(9), rs.getDate(10), rs.getDate(11), rs.getBoolean(12));}

            }catch (Exception e) {
        }
            return ketQua;
        
    }

    // test connection 
    public static void main(String... args) {
        List<Club> club = new ClubDAO(Global.generateConnection()).getListClubs();

        System.out.println(club);

    }

}
