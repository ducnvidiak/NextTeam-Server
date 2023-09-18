/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Proposal;

public class ProposalDAO extends DBContext {

    public List<Proposal> getAllProposals() {
        List<Proposal> proposals = new ArrayList<>();
        Proposal proposal = null;

        String sql = "SELECT * FROM proposals";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                proposal = new Proposal(
                    rs.getInt("id"),
                    rs.getInt("clubId"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getInt("sendBy"),
                    rs.getBoolean("isApproved"),
                    rs.getTimestamp("createdAt"),
                    rs.getTimestamp("updatedAt")
                );

                proposals.add(proposal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proposals;
    }

    public Proposal getProposalById(int id) {
        Proposal proposal = null;
        String sql = "SELECT * FROM proposals WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                proposal = new Proposal(
                    rs.getInt("id"),
                    rs.getInt("clubId"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getInt("sendBy"),
                    rs.getBoolean("isApproved"),
                    rs.getTimestamp("createdAt"),
                    rs.getTimestamp("updatedAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proposal;
    }

    public boolean addProposal(Proposal proposal) {
        String sql = "INSERT INTO proposals (clubId, title, content, sendBy, isApproved) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, proposal.getClubId());
            st.setString(2, proposal.getTitle());
            st.setString(3, proposal.getContent());
            st.setInt(4, proposal.getSendBy());
            st.setBoolean(5, proposal.isIsApproved());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProposal(Proposal proposal) {
        String sql = "UPDATE proposals SET clubId = ?, title = ?, content = ?, sendBy = ?, isApproved = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, proposal.getClubId());
            st.setString(2, proposal.getTitle());
            st.setString(3, proposal.getContent());
            st.setInt(4, proposal.getSendBy());
            st.setBoolean(5, proposal.isIsApproved());
            st.setInt(6, proposal.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProposal(int proposalId) {
        String sql = "DELETE FROM proposals WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, proposalId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
