/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 *
 * @author admin
 */
public class GoogleDriveUploader {
    private static final String APPLICATION_NAME = "Google Drive API";
    private String CREDENTIALS_FILE_PATH = "E:/Fall23/project/NextTeam-Server/src/main/java/nextteam/utils/credentials.json";
    public String PARENT_FOLDER_ID;
    public String projectLocation;

    public GoogleDriveUploader(String PARENT_FOLDER_ID, String projectLocation) {
        this.PARENT_FOLDER_ID = PARENT_FOLDER_ID;
        this.projectLocation = projectLocation;
        this.CREDENTIALS_FILE_PATH = projectLocation + "NextTeam-Server/src/main/java/nextteam/utils/credentials.json";
        System.out.println("credentials file path: " + this.CREDENTIALS_FILE_PATH);
    }
    
    private GoogleCredentials authorize() throws IOException {
        InputStream credentialsStream = new FileInputStream(CREDENTIALS_FILE_PATH);
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/drive"));

        return credentials;
    }

    public CloudFileInfo uploadFile(String fileName, String fileType, byte[] data) throws IOException, GeneralSecurityException {
         GoogleCredentials credential = new GoogleDriveUploader(PARENT_FOLDER_ID, this.projectLocation).authorize();

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credential);

        Drive drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Create a new file on Google Drive
        File fileMetadata = new File();
        fileMetadata.setName(fileName);

        // Set the parent folder(s) for the file
        fileMetadata.setParents(Collections.singletonList(PARENT_FOLDER_ID));

        // Create file content from binary data
        ByteArrayContent fileContent = new ByteArrayContent(fileType, data);

        // Send the request to create a new file
        File uploadedFile = drive.files().create(fileMetadata, fileContent).execute();

        // Set the file to public view
        Permission permission = new Permission().setType("anyone").setRole("reader").setAllowFileDiscovery(false);

        drive.permissions().create(uploadedFile.getId(), permission).execute();

        // Get the download link
        String downloadLink = drive.files().get(uploadedFile.getId()).setFields("webContentLink").execute().getWebContentLink();

        // Get the view link
        String viewLink = drive.files().get(uploadedFile.getId()).setFields("webViewLink").execute().getWebViewLink();
        String previewLink = viewLink.substring(0, viewLink.lastIndexOf("/") + 1) + "preview";
        CloudFileInfo result;

        result = new CloudFileInfo(uploadedFile.getId(), downloadLink, previewLink);

        return result;
    }

    public int deleteFile(String fileId) throws IOException, GeneralSecurityException {
        GoogleCredentials credential = new GoogleDriveUploader(PARENT_FOLDER_ID, this.projectLocation).authorize();

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credential);

        Drive drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();

        drive.files().delete(fileId).execute();
        return 1;
    }
}
