package nextteam.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import com.google.gson.Gson;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.Proposal;
import nextteam.models.Success;
import nextteam.utils.database.ProposalDAO;


import com.google.auth.oauth2.GoogleCredentials;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;
import nextteam.models.FileRecord;
import nextteam.utils.database.FileStorageDAO;

/**
 *
 * @author admin
 */
class CloudFileInfo {

    public String fileId;
    public String downloadLink;
    public String viewLink;

    public CloudFileInfo() {
    }

    public CloudFileInfo(String fileId, String downloadLink, String viewLink) {
        this.fileId = fileId;
        this.downloadLink = downloadLink;
        this.viewLink = viewLink;
    }
}

class GoogleDriveUploader {

    private static final String APPLICATION_NAME = "Google Drive API";
    private static final String CREDENTIALS_FILE_PATH = "E:\\google_drive_api_credential\\credentials.json";
    private static final String PARENT_FOLDER_ID = "1-KNh7MIpZV-T0QpxmCLDrDGCtdwrag08";

    private static GoogleCredentials authorize() throws IOException {
        InputStream credentialsStream = new FileInputStream(CREDENTIALS_FILE_PATH);
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/drive"));

        return credentials;
    }

    public CloudFileInfo uploadFile(String fileName, String fileType, byte[] data) throws IOException, GeneralSecurityException {
        GoogleCredentials credential = authorize();

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
        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");
        drive.permissions().create(uploadedFile.getId(), permission).execute();

        // Get the download link
        String downloadLink = "https://drive.google.com/uc?id=" + uploadedFile.getId();

        // Get the view link
        String viewLink = "https://drive.google.com/file/d/" + uploadedFile.getId();

        CloudFileInfo result;

        result = new CloudFileInfo(uploadedFile.getId(), downloadLink, viewLink);

        return result;
    }

    public int deleteFile(String fileId) throws IOException, GeneralSecurityException {
        GoogleCredentials credential = authorize();

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

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class ProposalServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Proposal> p = new ProposalDAO(Global.generateConnection()).getListProposalByUserId(request.getParameter("id"));
        String resJsonString = this.gson.toJson(p);

        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        System.out.println("received request create proposal !");
        int userId = Integer.parseInt(request.getParameter("id"));

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int clubId = Integer.parseInt(request.getParameter("clubId"));
        int numOfFile = Integer.parseInt(request.getParameter("numOfFile"));

        List<String> filesName = new ArrayList<>();
        List<String> filesType = new ArrayList<>();

        for (int i = 0; i < numOfFile; i++) {
            filesName.add(request.getParameter("filesname[" + i + "]"));
            filesType.add(request.getParameter("filesType[" + i + "]"));
        }

        // Tạo record proposal
        int result = new ProposalDAO(Global.generateConnection()).createProposal(new Proposal(clubId, title, content, userId, "pending"));
        int propId = 0;
        if (result == 1) {
            propId = new ProposalDAO(Global.generateConnection()).getIdLatestProposal();
        }

        // Đẩy files lên cloud
        GoogleDriveUploader googleService = new GoogleDriveUploader();
        result = 0;

        for (int i = 0; i < numOfFile; i++) {
            Part filePart = request.getPart("filescontent[" + i + "]");
            InputStream fileInputStream = filePart.getInputStream();
            byte[] fileBytes = fileInputStream.readAllBytes();

            byte[] decodedBytes = Base64.getDecoder().decode(fileBytes);
            try {
                CloudFileInfo cloudFile = googleService.uploadFile(filesName.get(i), filesType.get(i), decodedBytes);

//                FileRecord fileRecord = new FileRecord(cloudFile.fileId, Integer.toString(propId), filesType.get(i), filesName.get(i), cloudFile.downloadLink, cloudFile.viewLink);
//                result = new FileStorageDAO(Global.generateConnection()).createFileRecord(fileRecord);
            } catch (GeneralSecurityException ex) {
                Logger.getLogger(ProposalServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        // Gửi file lên cloud
        // Tạo bản ghi lưu trữ file
        // Xác nhận mọi thứ đều ổn
        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty("status", (result == 1 ? "success" : "failure"));

        if (propId != 0) {
            jsonRes.addProperty("propId", propId);
        }

        String resJsonString = this.gson.toJson(jsonRes);
        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int result = new ProposalDAO(Global.generateConnection()).deleteProposalById(request.getParameter("id"));

        String resJsonString = this.gson.toJson(result == 1 ? new Success("success") : new Success("failure"));
        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();

    }

}
