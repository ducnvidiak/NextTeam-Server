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
import model.PaymentCategory;

public class PaymentCategoryDAO extends DBContext {

    public List<PaymentCategory> getAllPaymentCategories() {
        List<PaymentCategory> paymentCategories = new ArrayList<>();
        PaymentCategory paymentCategory = null;

        String sql = "SELECT * FROM paymentCategories";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                paymentCategory = new PaymentCategory(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("clubId"),
                    rs.getDouble("amount"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );

                paymentCategories.add(paymentCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentCategories;
    }

    public PaymentCategory getPaymentCategoryById(int id) {
        PaymentCategory paymentCategory = null;
        String sql = "SELECT * FROM paymentCategories WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                paymentCategory = new PaymentCategory(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("clubId"),
                    rs.getDouble("amount"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentCategory;
    }

    public boolean addPaymentCategory(PaymentCategory paymentCategory) {
        String sql = "INSERT INTO paymentCategories (title, description, clubId, amount, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, paymentCategory.getTitle());
            st.setString(2, paymentCategory.getDescription());
            st.setInt(3, paymentCategory.getClubId());
            st.setDouble(4, paymentCategory.getAmount());
            st.setDate(5, new java.sql.Date(paymentCategory.getCreatedAt().getTime()));
            st.setDate(6, new java.sql.Date(paymentCategory.getUpdatedAt().getTime()));

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePaymentCategory(PaymentCategory paymentCategory) {
        String sql = "UPDATE paymentCategories SET title = ?, description = ?, clubId = ?, amount = ?, createdAt = ?, updatedAt = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, paymentCategory.getTitle());
            st.setString(2, paymentCategory.getDescription());
            st.setInt(3, paymentCategory.getClubId());
            st.setDouble(4, paymentCategory.getAmount());
            st.setDate(5, new java.sql.Date(paymentCategory.getCreatedAt().getTime()));
            st.setDate(6, new java.sql.Date(paymentCategory.getUpdatedAt().getTime()));
            st.setInt(7, paymentCategory.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePaymentCategory(int paymentCategoryId) {
        String sql = "DELETE FROM paymentCategories WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, paymentCategoryId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

