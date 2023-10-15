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
import nextteam.models.Proposal;
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
    
    public int deleteFileRecord(String fileId) {
        int result = 0;
        try {
            result = executeUpdatePreparedStatement("DELETE FROM fileStorage WHERE fileId=?", fileId);
        } catch (Exception e) {
            
        }
        return result;
    }
    
}
