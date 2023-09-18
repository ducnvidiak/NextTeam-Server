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
import model.PaymentExpense;

public class PaymentExpenseDAO extends DBContext {

    public List<PaymentExpense> getAllPaymentExpenses() {
        List<PaymentExpense> paymentExpenses = new ArrayList<>();
        PaymentExpense paymentExpense = null;

        String sql = "SELECT * FROM paymentExpenses";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                paymentExpense = new PaymentExpense(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("clubId"),
                    rs.getDouble("amount"),
                    rs.getDate("createdAt"),
                    rs.getDate("updatedAt")
                );

                paymentExpenses.add(paymentExpense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentExpenses;
    }

    public PaymentExpense getPaymentExpenseById(int id) {
        PaymentExpense paymentExpense = null;
        String sql = "SELECT * FROM paymentExpenses WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                paymentExpense = new PaymentExpense(
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

        return paymentExpense;
    }

    public boolean addPaymentExpense(PaymentExpense paymentExpense) {
        String sql = "INSERT INTO paymentExpenses (title, description, clubId, amount, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, paymentExpense.getTitle());
            st.setString(2, paymentExpense.getDescription());
            st.setInt(3, paymentExpense.getClubId());
            st.setDouble(4, paymentExpense.getAmount());
            st.setDate(5, new java.sql.Date(paymentExpense.getCreatedAt().getTime()));
            st.setDate(6, new java.sql.Date(paymentExpense.getUpdatedAt().getTime()));

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePaymentExpense(PaymentExpense paymentExpense) {
        String sql = "UPDATE paymentExpenses SET title = ?, description = ?, clubId = ?, amount = ?, createdAt = ?, updatedAt = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, paymentExpense.getTitle());
            st.setString(2, paymentExpense.getDescription());
            st.setInt(3, paymentExpense.getClubId());
            st.setDouble(4, paymentExpense.getAmount());
            st.setDate(5, new java.sql.Date(paymentExpense.getCreatedAt().getTime()));
            st.setDate(6, new java.sql.Date(paymentExpense.getUpdatedAt().getTime()));
            st.setInt(7, paymentExpense.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePaymentExpense(int paymentExpenseId) {
        String sql = "DELETE FROM paymentExpenses WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, paymentExpenseId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
