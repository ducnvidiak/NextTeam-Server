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
import nextteam.models.FileRecord;
import nextteam.utils.SQLDatabase;

public class FileStorageDAO extends SQLDatabase {

    public FileStorageDAO(Connection connection) {
        super(connection);
    }
    
    public int createFileRecord(FileRecord file) {
        int result = 0;
        try {
            result = executeUpdatePreparedStatement("INSERT INTO fileStorage (fileId, proposalId, type, name, downloadLink, viewLink) VALUES (?, ?, ?, ?, ?, ?)", 
             file.getFileId(),
             file.getProposalId(),
             file.getType(),
             file.getName(),
             file.getDownloadLink(),
             file.getViewLink()
             );
        } catch (Exception e) {
            
        }
        
        return result;
    }
    
    public List<String> getAllFileIdByPropId(String id) {
        List <String> result = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT fileId FROM fileStorage WHERE proposalId=?", id);
        try {
            while (rs.next()) {
                result.add(rs.getString("fileId"));
            }
        } catch (Exception e) {
            
        }
        
        return result;
    }
    
    public List<FileRecord> getListFileRecordByPropId(String id) {
        List<FileRecord> fileRecords = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT * FROM fileStorage WHERE proposalId=?", id);
        
        try {
            while (rs.next()) {
                fileRecords.add(
                        new FileRecord(
                                rs.getInt("id"),
                                rs.getString("fileId"),
                                rs.getString("proposalId"),
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
    
    public List<FileRecord> getAllFileRecordByUserId(String id) {
        List<FileRecord> fileRecords = new ArrayList<>();
        ResultSet rs = executeQueryPreparedStatement("SELECT fs.* FROM fileStorage fs JOIN proposals p ON fs.proposalId = p.id WHERE p.sendBy=?", id);
        
        try {
            while (rs.next()) {
                fileRecords.add(
                        new FileRecord(
                                rs.getInt("id"),
                                rs.getString("fileId"),
                                rs.getString("proposalId"),
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
            result = executeUpdatePreparedStatement("DELETE FROM fileStorage WHERE fileId=?", fileId);
        } catch (Exception e) {
            
        }
        return result;
    }
    
    public int deleteAllFileRecordByPropId(String id) {
        int result = 0;
        try {
            result = executeUpdatePreparedStatement("DELETE FROM fileStorage WHERE proposalId=?", id);
        } catch (Exception e) {
            
        }
        return result;
    }
    
}
