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
import model.PostComment;

public class PostCommentDAO extends DBContext {

    public List<PostComment> getAllPostComments() {
        List<PostComment> postComments = new ArrayList<>();
        PostComment postComment = null;

        String sql = "SELECT * FROM postComments";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                postComment = new PostComment(
                    rs.getInt("id"),
                    rs.getInt("postId"),
                    rs.getInt("repPostCommentId")
                   
                );

                postComments.add(postComment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postComments;
    }

    public PostComment getPostCommentById(int id) {
        PostComment postComment = null;
        String sql = "SELECT * FROM postComments WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                postComment = new PostComment(
                    rs.getInt("id"),
                    rs.getInt("postId"),
                    rs.getInt("repPostCommentId"));
                    
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postComment;
    }

    public boolean addPostComment(PostComment postComment) {
        String sql = "INSERT INTO postComments (postId, repPostCommentId) VALUES (?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, postComment.getId());
            st.setInt(2, postComment.getRepPostCommentId());
            // You may need to set other fields as well

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePostComment(PostComment postComment) {
        String sql = "UPDATE postComments SET postId = ?, repPostCommentId = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, postComment.getPostId());
            st.setInt(2, postComment.getRepPostCommentId());
            // You may need to set other fields as well
            st.setInt(3, postComment.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePostComment(int postCommentId) {
        String sql = "DELETE FROM postComments WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, postCommentId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

