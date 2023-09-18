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
import model.Plan;

public class PlanDAO extends DBContext {

    public List<Plan> getAllPlans() {
        List<Plan> plans = new ArrayList<>();
        Plan plan = null;

        String sql = "SELECT * FROM plans";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                plan = new Plan(
                    rs.getInt("id"),
                    rs.getInt("clubId"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("attachement"),
                    rs.getString("response"),
                    rs.getBoolean("isApproved"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );

                plans.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plans;
    }

    public Plan getPlanById(int id) {
        Plan plan = null;
        String sql = "SELECT * FROM plans WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                plan = new Plan(
                    rs.getInt("id"),
                    rs.getInt("clubId"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("attachement"),
                    rs.getString("response"),
                    rs.getBoolean("isApproved"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plan;
    }

    public boolean addPlan(Plan plan) {
        String sql = "INSERT INTO plans (clubId, title, content, attachement, response, isApproved, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, plan.getClubId());
            st.setString(2, plan.getTitle());
            st.setString(3, plan.getContent());
            st.setString(4, plan.getAttachmentUrl());
            st.setString(5, plan.getResponse());
            st.setBoolean(6, plan.isIsApprovedByAdmin());
            st.setDate(7, new java.sql.Date(plan.getCreatedAt().getTime()));
            st.setDate(8, new java.sql.Date(plan.getUpdatedAt().getTime()));

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePlan(Plan plan) {
        String sql = "UPDATE plans SET clubId = ?, title = ?, content = ?, attachement = ?, response = ?, isApprovedByAdmin = ?, createdAt = ?, updatedAt = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, plan.getClubId());
            st.setString(2, plan.getTitle());
            st.setString(3, plan.getContent());
            st.setString(4, plan.getAttachmentUrl());
            st.setString(5, plan.getResponse());
            st.setBoolean(6, plan.isIsApprovedByAdmin());
            st.setDate(7, new java.sql.Date(plan.getCreatedAt().getTime()));
            st.setDate(8, new java.sql.Date(plan.getUpdatedAt().getTime()));
            st.setInt(9, plan.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePlan(int planId) {
        String sql = "DELETE FROM plans WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, planId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

