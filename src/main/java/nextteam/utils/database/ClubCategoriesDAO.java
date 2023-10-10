/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import nextteam.Global;
import nextteam.models.ClubCategories;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author bravee06
 */
public class ClubCategoriesDAO extends SQLDatabase{
     public ClubCategoriesDAO(Connection connection) {
        super(connection);
    }
    public List<ClubCategories> getListClubsCategories() {
        List<ClubCategories> list = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM clubCategories");
        try {
            while (rs.next()) {
            list.add(new ClubCategories(rs.getInt(1), rs.getString(2)));
            }   

        } catch (Exception e) {
            return null;
        }
        return list;
    }
    public ClubCategories getClubsCategoriesByID(int id) {
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM clubCategories where id = " + id);
        try {
            while (rs.next()) {
            return (new ClubCategories(rs.getInt(1), rs.getString(2)));
            }   

        } catch (Exception e) {
            
        }
        return null;
    }
    
    public static void main(String[] args) {
                ClubCategories c = new ClubCategoriesDAO(Global.generateConnection()).getClubsCategoriesByID(1);
                System.out.println(c);
    }
    
    
}
