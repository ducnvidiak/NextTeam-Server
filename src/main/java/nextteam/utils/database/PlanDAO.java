/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

/**
 *
 * @author admin
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import nextteam.models.Plan;
import nextteam.models.PlanDetail;
import nextteam.utils.SQLDatabase;

public class PlanDAO extends SQLDatabase {

    public PlanDAO(Connection connection) {
        super(connection);
    }

    public int createPlan(Plan p) {
        int result = 0;

        try {
            result = executeUpdatePreparedStatement(
                    "INSERT INTO Plans (clubId, title, content) VALUES (?, ?, ?)",
                    p.getClubId(),
                    p.getTitle(),
                    p.getContent());
        } catch (Exception e) {

        }

        return result;
    }

    public int getIdLatestPlan() {
        int result = -1;

        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT MAX(id) AS id FROM plans");
            if (rs.next()) {
                result = rs.getInt("id");
            }
        } catch (Exception e) {

        }

        return result;
    }

    public List<Plan> getListPlanByClubId(String clubId) {
        List<Plan> plans = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM Plans WHERE clubId=?", clubId);
        try {
            while (rs.next()) {
                plans.add(
                        new Plan(
                                rs.getInt("id"),
                                rs.getInt("clubId"),
                                rs.getString("title"),
                                rs.getString("content"),
                                rs.getString("response"),
                                rs.getString("isApproved"),
                                rs.getDate("createdAt"),
                                rs.getDate("updatedAt")
                        )
                );
            }
        } catch (Exception e) {

        }
        return plans;
    }
    
    public List<PlanDetail> getListAllPlans() {
        List<PlanDetail> plans = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT p.*, c.name FROM Plans AS p JOIN clubs AS c ON p.clubId = c.id");
        try {
            while (rs.next()) {
                plans.add(
                        new PlanDetail(
                                rs.getInt("id"),
                                rs.getInt("clubId"),
                                rs.getString("title"),
                                rs.getString("content"),
                                rs.getString("response"),
                                rs.getString("isApproved"),
                                rs.getDate("createdAt"),
                                rs.getDate("updatedAt"),
                                rs.getString("name")
                        )
                );
            }
        } catch (Exception e) {

        }
        return plans;
    }

    public Plan getPlanById(String id) {
        Plan plan = new Plan();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM Plans WHERE id=?", id);
        try {
            while (rs.next()) {
                plan = new Plan(
                        rs.getInt("id"),
                        rs.getInt("clubId"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("response"),
                        rs.getString("isApproved"),
                        rs.getDate("createdAt"),
                        rs.getDate("updatedAt")
                );

            }
        } catch (Exception e) {

        }
        return plan;
    }

    public int updatePlan(Plan p) {
        int result = 0;
        System.out.println("received update plan");
        try {
            result = executeUpdatePreparedStatement("UPDATE plans SET title=?, content=?, isApproved=?, updatedAt=? WHERE id=?",
                    p.getTitle(), p.getContent(), p.getIsApproved(), p.getUpdatedAt(), p.getId());
        } catch (Exception e) {

        }

        return result;
    }
    
    public int updatePlanStatus(String id, String status, String response) {
        int result = 0;

        try {
            result = executeUpdatePreparedStatement("UPDATE plans SET isApproved=?, response=? WHERE id=?",
                    status, response, id);
        } catch (Exception e) {

        }

        return result;
    }

    public int deletePlanById(String id) {
        int result = 0;
        try {
            result = executeUpdatePreparedStatement("DELETE FROM plans WHERE id=?", id);
        } catch (Exception e) {

        }

        return result;
    }

}
