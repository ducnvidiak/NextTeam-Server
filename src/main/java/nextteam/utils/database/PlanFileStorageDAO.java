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
import nextteam.models.PlanFileRecord;
import nextteam.utils.SQLDatabase;

public class PlanFileStorageDAO extends SQLDatabase {

    public PlanFileStorageDAO(Connection connection) {
        super(connection);
    }

    public int createPlanFileRecord(PlanFileRecord file) {
        int result = 0;
        try {
            result = executeUpdatePreparedStatement("INSERT INTO planFileStorage (fileId, planId, type, name, downloadLink, viewLink) VALUES (?, ?, ?, ?, ?, ?)",
                    file.getFileId(),
                    file.getPlanId(),
                    file.getType(),
                    file.getName(),
                    file.getDownloadLink(),
                    file.getViewLink()
            );
        } catch (Exception e) {

        }

        return result;
    }

    public List<String> getAllFileIdByPlanId(String id) {
        List<String> result = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT fileId FROM planFileStorage WHERE planId=?", id);
        try {
            while (rs.next()) {
                result.add(rs.getString("fileId"));
            }
        } catch (Exception e) {

        }

        return result;
    }

    public List<PlanFileRecord> getListFileRecordByPlanId(String id) {
        List<PlanFileRecord> fileRecords = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM planFileStorage WHERE planId=?", id);

        try {
            while (rs.next()) {
                fileRecords.add(
                        new PlanFileRecord(
                                rs.getInt("id"),
                                rs.getString("fileId"),
                                rs.getString("planId"),
                                rs.getString("type"),
                                rs.getString("name"),
                                rs.getString("downloadLink"),
                                rs.getString("viewLink")
                        )
                );
            }
        } catch (Exception e) {

        }

        return fileRecords;

    }
    
    public List<PlanFileRecord> getListAllFileRecords() {
        List<PlanFileRecord> fileRecords = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM planFileStorage");

        try {
            while (rs.next()) {
                fileRecords.add(
                        new PlanFileRecord(
                                rs.getInt("id"),
                                rs.getString("fileId"),
                                rs.getString("planId"),
                                rs.getString("type"),
                                rs.getString("name"),
                                rs.getString("downloadLink"),
                                rs.getString("viewLink")
                        )
                );
            }
        } catch (Exception e) {

        }

        return fileRecords;

    }

    public List<PlanFileRecord> getAllFileRecordByClubId(String id) {
        List<PlanFileRecord> fileRecords = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT fs.* FROM planFileStorage fs JOIN plans p ON fs.planId = p.id WHERE p.clubId=?", id);

        try {
            while (rs.next()) {
                fileRecords.add(
                        new PlanFileRecord(
                                rs.getInt("id"),
                                rs.getString("fileId"),
                                rs.getString("planId"),
                                rs.getString("type"),
                                rs.getString("name"),
                                rs.getString("downloadLink"),
                                rs.getString("viewLink")
                        )
                );
            }
        } catch (Exception e) {

        }

        return fileRecords;

    }

    public int deleteFileRecord(String fileId) {
        int result = 0;
        try {
            result = executeUpdatePreparedStatement("DELETE FROM planFileStorage WHERE fileId=?", fileId);
        } catch (Exception e) {

        }
        return result;
    }

    public int deleteAllFileRecordByPlanId(String id) {
        int result = 0;
        try {
            result = executeUpdatePreparedStatement("DELETE FROM planFileStorage WHERE planId=?", id);
        } catch (Exception e) {

        }
        return result;
    }
}
