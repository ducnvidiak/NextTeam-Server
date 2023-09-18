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
import model.TransactionHistory;

public class TransactionHistoryDAO extends DBContext {

    public List<TransactionHistory> getAllTransactionHistory() {
        List<TransactionHistory> transactionHistoryList = new ArrayList<>();
        TransactionHistory transactionHistory = null;

        String sql = "SELECT * FROM transaction_history";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                transactionHistory = new TransactionHistory(
                    rs.getInt("id"),
                    rs.getInt("paidby"),
                    rs.getInt("clubId"),
                    rs.getInt("categoryId"),
                    rs.getTimestamp("createdAt"),
                    rs.getTimestamp("updatedAt")
                );

                transactionHistoryList.add(transactionHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionHistoryList;
    }

    public TransactionHistory getTransactionHistoryById(int id) {
        TransactionHistory transactionHistory = null;
        String sql = "SELECT * FROM transaction_history WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                transactionHistory = new TransactionHistory(
                    rs.getInt("id"),
                    rs.getInt("paidby"),
                    rs.getInt("clubId"),
                    rs.getInt("categoryId"),
                    rs.getTimestamp("createdAt"),
                    rs.getTimestamp("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionHistory;
    }

    public boolean addTransactionHistory(TransactionHistory transactionHistory) {
        String sql = "INSERT INTO transaction_history (paidby, clubId, categoryId) VALUES (?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, transactionHistory.getPaidby());
            st.setInt(2, transactionHistory.getClubId());
            st.setInt(3, transactionHistory.getCategoryId());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransactionHistory(TransactionHistory transactionHistory) {
        String sql = "UPDATE transaction_history SET paidby = ?, clubId = ?, categoryId = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, transactionHistory.getPaidby());
            st.setInt(2, transactionHistory.getClubId());
            st.setInt(3, transactionHistory.getCategoryId());
            st.setInt(4, transactionHistory.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTransactionHistory(int transactionHistoryId) {
        String sql = "DELETE FROM transaction_history WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, transactionHistoryId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
