/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import nextteam.Global;
import nextteam.models.PlanFileRecord;
import nextteam.models.Plan;
import nextteam.models.Success;
import nextteam.utils.database.PlanFileStorageDAO;
import nextteam.utils.database.PlanDAO;

/**
 *
 * @author admin
 */


@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class PlanServlet extends HttpServlet {
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
    private static final String PARENT_FOLDER_ID = "1zyAwqcpX-niyS2ULWCryelF_CCJaN9Sv";

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
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String type = request.getParameter("type");
        String id = request.getParameter("id");

        String resJsonString;

        if (type.equals("byPlanId")) {
            Plan p = new PlanDAO(Global.generateConnection()).getPlanById(id);
            resJsonString = this.gson.toJson(p);
        } else {
            List<Plan> p = new PlanDAO(Global.generateConnection()).getListPlanByClubId(request.getParameter("id"));
            resJsonString = this.gson.toJson(p);
        }

        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        System.out.println("received request create plan !");
        int clubId = Integer.parseInt(request.getParameter("id"));

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        int numOfFile = Integer.parseInt(request.getParameter("numOfFile"));

        List<String> filesName = new ArrayList<>();
        List<String> filesType = new ArrayList<>();

        for (int i = 0; i < numOfFile; i++) {
            filesName.add(request.getParameter("filesname[" + i + "]"));
            filesType.add(request.getParameter("filesType[" + i + "]"));
        }

        // Tạo record proposal
        int result = new PlanDAO(Global.generateConnection()).createPlan(new Plan(clubId, title, content));
        int planId = 0;
        if (result == 1) {
            planId = new PlanDAO(Global.generateConnection()).getIdLatestPlan();
        }

        if (numOfFile > 0) {
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

                    PlanFileRecord fileRecord = new PlanFileRecord(cloudFile.fileId, Integer.toString(planId), filesType.get(i), filesName.get(i), cloudFile.downloadLink, cloudFile.viewLink);

                    result = new PlanFileStorageDAO(Global.generateConnection()).createPlanFileRecord(fileRecord);
                } catch (GeneralSecurityException ex) {
                    Logger.getLogger(ProposalServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        // Gửi file lên cloud
        // Tạo bản ghi lưu trữ file
        // Xác nhận mọi thứ đều ổn
        JsonObject jsonRes = new JsonObject();
        jsonRes.addProperty("status", (result == 1 ? "success" : "failure"));

        if (planId != 0) {
            jsonRes.addProperty("propId", planId);
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String type = request.getParameter("type");
        JsonObject jsonRes = new JsonObject();
        int planId = Integer.parseInt(request.getParameter("id"));
        int result = 0;

        if (type.equals("changeStatus")) {
            String status = request.getParameter("status");
            System.out.println("received request update plan status !");
            
            result = new PlanDAO(Global.generateConnection()).updatePlanStatus(planId+"", status);
            if (result == 1) {
                 jsonRes.addProperty("planStatus", (result == 1 ? status : "failure"));
            }
        } else {

            System.out.println("received request update plan !");

            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String planResponse = request.getParameter("response");

            int numOfFile = Integer.parseInt(request.getParameter("numOfFile"));
            int numOfDeleteFile = Integer.parseInt(request.getParameter("numOfDeleteFile"));

            List<String> filesName = new ArrayList<>();
            List<String> filesType = new ArrayList<>();

            List<String> deleteFileId = new ArrayList<>();

            for (int i = 0; i < numOfFile; i++) {
                filesName.add(request.getParameter("filesname[" + i + "]"));
                filesType.add(request.getParameter("filesType[" + i + "]"));
            }

            for (int i = 0; i < numOfDeleteFile; i++) {
                deleteFileId.add(request.getParameter("deleteFileId[" + i + "]"));
                System.out.println(deleteFileId.get(i));
            }

            // Tạo record proposal
            result = new PlanDAO(Global.generateConnection()).updatePlan(new Plan(title, content, planResponse, "pending", new Date()));

            if (numOfFile > 0) {
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

                        PlanFileRecord fileRecord = new PlanFileRecord(cloudFile.fileId, Integer.toString(planId), filesType.get(i), filesName.get(i), cloudFile.downloadLink, cloudFile.viewLink);

                        result = new PlanFileStorageDAO(Global.generateConnection()).createPlanFileRecord(fileRecord);
                    } catch (GeneralSecurityException ex) {
                        Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

            if (numOfDeleteFile > 0) {
                GoogleDriveUploader googleService = new GoogleDriveUploader();

                for (int i = 0; i < numOfDeleteFile; i++) {
                    try {
                        googleService.deleteFile(deleteFileId.get(i));
                    } catch (GeneralSecurityException ex) {
                        Logger.getLogger(ProposalServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    new PlanFileStorageDAO(Global.generateConnection()).deleteFileRecord(deleteFileId.get(i));

                }
            }

            // Gửi file lên cloud
            // Tạo bản ghi lưu trữ file
            // Xác nhận mọi thứ đều ổn
            if (planId != 0) {
                jsonRes.addProperty("clubId", planId);
            }
        }

        jsonRes.addProperty("status", (result == 1 ? "success" : "failure"));
        String resJsonString = this.gson.toJson(jsonRes);
        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        // xóa các file trên cloud
        GoogleDriveUploader googleService = new GoogleDriveUploader();
        List<String> fileIds = new PlanFileStorageDAO(Global.generateConnection()).getAllFileIdByPlanId(id);
        if (!fileIds.isEmpty()) {
            for (int i = 0; i < fileIds.size(); i++) {
                try {
                    googleService.deleteFile(fileIds.get(i));
                } catch (GeneralSecurityException ex) {
                    Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // xóa các bản ghi file
        new PlanFileStorageDAO(Global.generateConnection()).deleteAllFileRecordByPlanId(id);

        // xóa proposal
        int result = new PlanDAO(Global.generateConnection()).deletePlanById(id);

        String resJsonString = this.gson.toJson(result == 1 ? new Success("success") : new Success("failure"));
        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
