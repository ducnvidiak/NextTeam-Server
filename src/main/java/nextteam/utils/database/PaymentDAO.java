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
import nextteam.models.Major;
import nextteam.models.PaymentCategory;
import nextteam.models.PaymentExpense;
import nextteam.models.TransactionHistory;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class PaymentDAO extends SQLDatabase {

    public PaymentDAO(Connection connection) {
        super(connection);
    }

    public class Payment {

        private int id;
        private String title;
        private String description;
        private double amount;
        private String type;
        private String status;
        private String firstname;
        private String lastname;
        private Date createdAt;
        private Date updatedAt;
        private int clubId;
        private String paymentForm;

        public Payment(int id, String title, String description, double amount, String type, String status, String firstname, String lastname, Date createdAt, Date updatedAt, int clubId, String paymentForm) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.amount = amount;
            this.type = type;
            this.status = status;
            this.firstname = firstname;
            this.lastname = lastname;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.clubId = clubId;
            this.paymentForm = paymentForm;
        }
        
         public Payment(int id, String title, String description, double amount, String type, String status, String firstname, String lastname, Date createdAt, Date updatedAt, int clubId) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.amount = amount;
            this.type = type;
            this.status = status;
            this.firstname = firstname;
            this.lastname = lastname;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.clubId = clubId;
        }

        public Payment(int id, String title, String description, double amount, String status, Date createdAt, Date updatedAt) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.amount = amount;
            this.status = status;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
        
          public Payment(int id, String title, String description, double amount, String type,Date createdAt, Date updatedAt,int clubId) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.amount = amount;
             this.type = type;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.clubId = clubId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getClubId() {
            return clubId;
        }

        public void setClubId(int clubId) {
            this.clubId = clubId;
        }

    }

    public class Balance {

        private double sumIn;
        private double sumOut;
        private double balance;

        public Balance(double sumIn, double sumOut, double balance) {
            this.sumIn = sumIn;
            this.sumOut = sumOut;
            this.balance = balance;
        }

        public double getSumIn() {
            return sumIn;
        }

        public void setSumIn(double sumIn) {
            this.sumIn = sumIn;
        }

        public double getSumOut() {
            return sumOut;
        }

        public void setSumOut(double sumOut) {
            this.sumOut = sumOut;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

    }

    public List<Payment> getAllPayment(String clubId) {
        List<Payment> payments = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT th.id, pc.title, pc.description, pc.amount, 'in' AS type, th.status, users.firstname, users.lastname, th.createdAt, th.updatedAt, pc.clubId\n"
                + "FROM transactionHistories th\n"
                + "INNER JOIN paymentCategories pc\n"
                + "ON th.categoryId = pc.id\n"
                + "INNER JOIN users\n"
                + "ON th.paidBy = users.id\n"
                + "WHERE pc.clubId = ?\n"
                + "UNION ALL\n"
                + "SELECT id,title,description,amount, 'out' AS type, NULL AS status, NULL AS firstname, NULL AS lastname, createdAt,updatedAt, clubId\n"
                + "FROM paymentExpenses\n"
                + "WHERE clubId = ?\n"
                + "ORDER BY createdAt DESC\n",
                clubId, clubId);
        try {
            while (rs.next()) {
                Payment m = new Payment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getTimestamp(9), rs.getTimestamp(10), rs.getInt(11));
                payments.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payments;
    }
    
    public List<Payment> getAllPaymentInCategory(String categoryId) {
        List<Payment> payments = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT th.id, pc.title, pc.description, pc.amount, 'in' AS type, th.status, users.firstname, users.lastname, th.createdAt, th.updatedAt, pc.clubId, th.paymentForm\n"
                + "FROM transactionHistories th\n"
                + "INNER JOIN paymentCategories pc\n"
                + "ON th.categoryId = pc.id\n"
                + "INNER JOIN users\n"
                + "ON th.paidBy = users.id\n"
                + "WHERE pc.id = ?\n"
                + "ORDER BY createdAt DESC\n",
                categoryId);
        try {
            while (rs.next()) {
                Payment m = new Payment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getTimestamp(9), rs.getTimestamp(10), rs.getInt(11),rs.getString(12));
                payments.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payments;
    }

    public List<Payment> getAllPaymentByCategory(String clubId) {
        List<Payment> payments = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT pc.id, pc.title, pc.description, pc.amount * (SELECT COUNT(*) FROM transactionHistories  WHERE categoryId = pc.id AND status = 1) AS amount, 'in' AS type, pc.createdAt, pc.updatedAt ,pc.clubId\n"
                + "              FROM paymentCategories pc\n"
                + "      UNION ALL\n"
                + "       SELECT id,title,description,amount, 'out' AS type, createdAt,updatedAt, clubId\n"
                + "        FROM paymentExpenses\n"
                + "  WHERE clubId = ?\n"
                + "     ORDER BY createdAt DESC",
                 clubId
        );
        try {
            while (rs.next()) {
                Payment m = new Payment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5),  rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8));
                payments.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payments;
    }

    public List<Payment> getAllPaymentOfMe(String userId) {
        List<Payment> payments = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT th.id, pc.title, pc.description, pc.amount, th.status, th.createdAt, th.updatedAt\n"
                + "FROM transactionHistories th\n"
                + "INNER JOIN paymentCategories pc\n"
                + "ON th.categoryId = pc.id\n"
                + "WHERE th.paidBy = ?\n"
                + "ORDER BY createdAt DESC",
                userId);
        try {
            while (rs.next()) {
                Payment m = new Payment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getTimestamp(6), rs.getTimestamp(7));
                payments.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payments;
    }

    public int addCategory(final PaymentCategory t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "INSERT INTO paymentCategories (title, description, clubId, amount )  VALUES (?,?,?,?)",
                t.getTitle(),
                t.getDescription(),
                t.getClubId(),
                t.getAmount()
        );
        return ketQua;
    }

    public Balance SumBalance(String id) {
        Balance data = null;
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT SUM(pc.amount) AS sumIn, SUM(pe.amount) AS sumOut, (SUM(pc.amount) - SUM(pe.amount)) AS balance\n"
                    + "FROM transactionHistories th\n"
                    + "INNER JOIN paymentCategories pc ON th.categoryId = pc.id\n"
                    + "INNER JOIN paymentExpenses pe ON pe.clubId = pc.clubId\n"
                    + "WHERE pc.clubId = ? AND th.status = 1;", id);
            if (rs.next()) {
                data = new Balance(rs.getDouble(1), rs.getDouble(2), rs.getDouble(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }

    public int payByCash(String id) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE transactionHistories SET  status=1, paymentForm='cash' WHERE id=?",
                id
        );
        return ketQua;

    }
    
    public int payByOnline(String id, String status ) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE transactionHistories SET  status=?, paymentForm='online' WHERE id=?",
                status
                ,id
        );
        return ketQua;

    }

    public int addExpense(final PaymentExpense t) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "INSERT INTO paymentExpenses (title, description, clubId, amount )  VALUES (?,?,?,?)",
                t.getTitle(),
                t.getDescription(),
                t.getClubId(),
                t.getAmount()
        );
        return ketQua;
    }
    
    public int updateBalance(String clubId, String balance) {
        int ketQua = 0;
        ketQua = executeUpdatePreparedStatement(
                "UPDATE clubs SET  balance=? WHERE id=?",
                balance,clubId
        );
        return ketQua;

    }

}
