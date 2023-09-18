/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.PublicNotificationsView;

public class PublicNotificationsViewDAO extends DBContext {

    public List<PublicNotificationsView> getAllPublicNotificationsView() {
        List<PublicNotificationsView> publicNotificationsViews = new ArrayList<>();
        PublicNotificationsView publicNotificationsView = null;

        String sql = "SELECT * FROM public_notifications_view";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                publicNotificationsView = new PublicNotificationsView(
                    rs.getInt("id"),
                    rs.getInt("publicNotificationId"),
                    rs.getInt("hasSeenBy"),
                    rs.getTimestamp("markTime")
                );

                publicNotificationsViews.add(publicNotificationsView);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publicNotificationsViews;
    }

    public PublicNotificationsView getPublicNotificationsViewById(int id) {
        PublicNotificationsView publicNotificationsView = null;
        String sql = "SELECT * FROM public_notifications_view WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                publicNotificationsView = new PublicNotificationsView(
                    rs.getInt("id"),
                    rs.getInt("publicNotificationId"),
                    rs.getInt("hasSeenBy"),
                    rs.getTimestamp("markTime")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publicNotificationsView;
    }

    public boolean addPublicNotificationsView(PublicNotificationsView publicNotificationsView) {
        String sql = "INSERT INTO public_notifications_view (publicNotificationId, hasSeenBy, markTime) VALUES (?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, publicNotificationsView.getPublicNotificationId());
            st.setInt(2, publicNotificationsView.getHasSeenBy());
            st.setTimestamp(3, (Timestamp) publicNotificationsView.getMarkTime());

            int rowsInserted = st.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePublicNotificationsView(PublicNotificationsView publicNotificationsView) {
        String sql = "UPDATE public_notifications_view SET publicNotificationId = ?, hasSeenBy = ?, markTime = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, publicNotificationsView.getPublicNotificationId());
            st.setInt(2, publicNotificationsView.getHasSeenBy());
            st.setTimestamp(3, (Timestamp) publicNotificationsView.getMarkTime());
            st.setInt(4, publicNotificationsView.getId());

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePublicNotificationsView(int publicNotificationsViewId) {
        String sql = "DELETE FROM public_notifications_view WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, publicNotificationsViewId);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
