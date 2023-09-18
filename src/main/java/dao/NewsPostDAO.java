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
import model.NewsPost;

public class NewsPostDAO extends DBContext {

    public List<NewsPost> getAllNewsPosts() {
        List<NewsPost> newsPostList = new ArrayList<>();
        NewsPost newsPost = null;

        String sql = "SELECT * FROM newsPosts";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                newsPost = new NewsPost(
                    rs.getInt("id"),
                    rs.getInt("clubId"),
                    rs.getString("imageUrl"),
                    rs.getString("videoUrl"),
                    rs.getString("content")
                );

                newsPostList.add(newsPost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newsPostList;
    }

    public NewsPost getNewsPostById(int id) {
        NewsPost newsPost = null;
        String sql = "SELECT * FROM newsPosts WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                newsPost = new NewsPost(
                    rs.getInt("id"),
                    rs.getInt("clubId"),
                    rs.getString("imageUrl"),
                    rs.getString("videoUrl"),
                    rs.getString("content")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newsPost;
    }

    public boolean addNewsPost(NewsPost newsPost) {
        String sql = "INSERT INTO newsPosts (clubId, imageUrl, videoUrl, content) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, newsPost.getClubId());
            st.setString(2, newsPost.getImageUrl());
            st.setString(3, newsPost.getVideoUrl());
            st.setString(4, newsPost.getContent());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateNewsPost(NewsPost newsPost) {
        String sql = "UPDATE newsPosts SET clubId = ?, imageUrl = ?, videoUrl = ?, content = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, newsPost.getClubId());
            st.setString(2, newsPost.getImageUrl());
            st.setString(3, newsPost.getVideoUrl());
            st.setString(4, newsPost.getContent());
            st.setInt(5, newsPost.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteNewsPost(int newsPostId) {
        String sql = "DELETE FROM newsPosts WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, newsPostId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

