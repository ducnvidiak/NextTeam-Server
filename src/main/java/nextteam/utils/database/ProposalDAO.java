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
import nextteam.models.Proposal;
import nextteam.models.ProposalDetail;
import nextteam.utils.SQLDatabase;

public class ProposalDAO extends SQLDatabase {

    public ProposalDAO(Connection connection) {
        super(connection);
    }

    public int createProposal(Proposal p) {
        int result = 0;

        try {
            result = executeUpdatePreparedStatement(
                    "INSERT INTO proposals (clubId, title, content, sendBy, isApproved) VALUES (?, ?, ?, ?, ?)",
                    p.getClubId(),
                    p.getTitle(),
                    p.getContent(),
                    p.getSendBy(),
                    p.getIsApproved());
        } catch (Exception e) {

        }

        return result;
    }
    
    public int getIdLatestProposal() {
        int result = -1;
        
        try {
            ResultSet rs = executeQueryPreparedStatement("SELECT MAX(id) AS id FROM proposals");
            if (rs.next()) {
                result = rs.getInt("id");
            }
        } catch (Exception e) {
            
        }
        
        return result;
    }

    public List<ProposalDetail> getListProposalByUserId(String userId) {
        List<ProposalDetail> proposals = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT p.*, c.subname FROM proposals AS p JOIN clubs AS c ON p.clubId = c.id WHERE sendBy=?", userId);
        try {
            while (rs.next()) {
                proposals.add(
                        new ProposalDetail(
                                rs.getInt("id"),
                                rs.getInt("clubId"),
                                rs.getString("title"),
                                rs.getString("content"),
                                rs.getInt("sendBy"),
                                rs.getString("isApproved"),
                                rs.getDate("createdAt"),
                                rs.getDate("updatedAt"),
                                rs.getString("subname")
                        )
                );
            }
        } catch (Exception e) {

        }
        return proposals;
    }
    public List<ProposalDetail> getListProposalByClubId(String clubId) {
        List<ProposalDetail> proposals = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT p.*, u.username, u.firstname, u.lastname, c.subname FROM proposals AS p JOIN users AS u ON p.sendBy = u.id JOIN clubs AS c ON p.clubId = c.id  WHERE clubId=?", clubId);
        try {
            while (rs.next()) {
                proposals.add(
                        new ProposalDetail(
                                rs.getInt("id"),
                                rs.getInt("clubId"),
                                rs.getString("title"),
                                rs.getString("content"),
                                rs.getInt("sendBy"),
                                rs.getString("isApproved"),
                                rs.getDate("createdAt"),
                                rs.getDate("updatedAt"),
                                rs.getString("username"),
                                rs.getString("firstname"),
                                rs.getString("lastname"),
                                rs.getString("subname")
                        )
                );
            }
        } catch (Exception e) {

        }
        return proposals;
    }

    public Proposal getProposalById(String id) {
        Proposal proposal = new Proposal();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM proposals WHERE id=?", id);
        try {
            while (rs.next()) {
                proposal = new Proposal(
                        rs.getInt("id"),
                        rs.getInt("clubId"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getInt("sendBy"),
                        rs.getString("isApproved"),
                        rs.getDate("createdAt"),
                        rs.getDate("updatedAt")
                );

            }
        } catch (Exception e) {

        }
        return proposal;
    }

    public int updateProposal(Proposal p) {
        int result = 0;

        try {
            result = executeUpdatePreparedStatement("UPDATE proposals SET title=?, content=?, isApproved=?, updatedAt=? WHERE id=?",
                    p.getTitle(), p.getContent(), p.getIsApproved(), p.getUpdatedAt(), p.getId());
        } catch (Exception e) {

        }

        return result;
    }
    
    public int updateProposalStatus(String propId, String status) {
        int result = 0;

        try {
            result = executeUpdatePreparedStatement("UPDATE proposals SET isApproved=? WHERE id=?",
                    status, propId);
        } catch (Exception e) {

        }

        return result;
    }
    
    public int deleteProposalById(String id) {
        int result = 0;
        try {
            result = executeUpdatePreparedStatement("DELETE FROM proposals WHERE id=?", id);
        } catch (Exception e) {
            
        }
        
        return result;
    }
}
