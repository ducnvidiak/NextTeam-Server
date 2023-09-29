/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.util.Date;

/**
 *
 * @author vnitd
 */
public class TransactionHistory {

    private int id;
    private int paidby;
//    private int clubId;
    private int categoryId;
    private String status;
    private String paymentForm;
    private Date createdAt;
    private Date updatedAt;

    public TransactionHistory(int id, int paidby, int categoryId, String status, String paymentForm, Date createdAt, Date updatedAt) {
        this.id = id;
        this.paidby = paidby;
        this.categoryId = categoryId;
        this.status = status;
        this.paymentForm = paymentForm;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaidby() {
        return paidby;
    }

    public void setPaidby(int paidby) {
        this.paidby = paidby;
    }

//    public int getClubId() {
//        return clubId;
//    }
//
//    public void setClubId(int clubId) {
//        this.clubId = clubId;
//    }
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(String paymentForm) {
        this.paymentForm = paymentForm;
    }
    
}
