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
import nextteam.utils.SQLDatabase;

public class ProposalDAO extends SQLDatabase {

    public ProposalDAO(Connection connection) {
        super(connection);
    }

    public int createProposal(Proposal p) {
        int result = 0;

        try {
            result = executeUpdatePreparedStatement(
                    "INSERT INTO proposals (clubId, title, content, sendBy, attach, isApproved) VALUES (?, ?, ?, ?, ?, ?)",
                    p.getClubId(),
                    p.getTitle(),
                    p.getContent(),
                    p.getSendBy(),
                    p.getAttach(),
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

    public List<Proposal> getListProposalByUserId(String userId) {
        List<Proposal> proposals = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM proposals WHERE sendBy=?", userId);
        try {
            while (rs.next()) {
                proposals.add(
                        new Proposal(
                                rs.getInt("id"),
                                rs.getInt("clubId"),
                                rs.getString("title"),
                                rs.getString("content"),
                                rs.getInt("sendBy"),
                                rs.getString("attach"),
                                rs.getString("isApproved"),
                                rs.getDate("createdAt"),
                                rs.getDate("updatedAt")
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
                        rs.getString("attach"),
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
            result = executeUpdatePreparedStatement("UPDATE proposals SET clubId=?, title=?, content=?, attach=?, isApproved=?, updatedAt=?",
                    p.getClubId(), p.getTitle(), p.getContent(), p.getAttach(), p.getIsApproved(), p.getUpdatedAt());
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
