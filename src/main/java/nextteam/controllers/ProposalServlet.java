package nextteam.controllers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import com.google.gson.Gson;

import com.google.gson.JsonObject;
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

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;
import nextteam.models.FileRecord;
import nextteam.models.ProposalDetail;
import nextteam.utils.database.FileStorageDAO;
import nextteam.utils.GoogleDriveUploader;
import nextteam.utils.CloudFileInfo;

/**
 *
 * @author admin
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class ProposalServlet extends HttpServlet {

    String PARENT_FOLDER_ID = "1-KNh7MIpZV-T0QpxmCLDrDGCtdwrag08";
    private final Gson gson = new Gson();
    String projectLocation = "";
//    E:/Fall23/project/";
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String type = request.getParameter("type");
        String id = request.getParameter("id");

        String resJsonString = "";

        if (type.equals("byProposalId")) {
            Proposal p = new ProposalDAO(Global.generateConnection()).getProposalById(id);
            resJsonString = this.gson.toJson(p);
        } else if (type.equals("byUserId")) {
            List<ProposalDetail> p = new ProposalDAO(Global.generateConnection()).getListProposalByUserId(request.getParameter("id"));
            resJsonString = this.gson.toJson(p);
            System.out.println("get list of proposals!");
        } else if (type.equals("byClubId")) {
            System.out.println("clubid proposal");
            List<ProposalDetail> p = new ProposalDAO(Global.generateConnection()).getListProposalByClubId(request.getParameter("id"));
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
//        response.setCharacterEncoding("UTF-8");
        
        projectLocation= getServletContext().getRealPath("").substring(0, getServletContext().getRealPath("").indexOf("NextTeam")).replace("\\", "/");

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

        if (numOfFile > 0) {
            // Đẩy files lên cloud
            GoogleDriveUploader googleService = new GoogleDriveUploader(PARENT_FOLDER_ID, projectLocation);
            result = 0;
            System.out.println(googleService);
            for (int i = 0; i < numOfFile; i++) {
                Part filePart = request.getPart("filescontent[" + i + "]");
                InputStream fileInputStream = filePart.getInputStream();
                byte[] fileBytes = fileInputStream.readAllBytes();

                byte[] decodedBytes = Base64.getDecoder().decode(fileBytes);
                try {
                    CloudFileInfo cloudFile = googleService.uploadFile(filesName.get(i), filesType.get(i), decodedBytes);

                    FileRecord fileRecord = new FileRecord(cloudFile.fileId, Integer.toString(propId), filesType.get(i), filesName.get(i), cloudFile.downloadLink, cloudFile.viewLink);

                    result = new FileStorageDAO(Global.generateConnection()).createFileRecord(fileRecord);
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        projectLocation= getServletContext().getRealPath("").substring(0, getServletContext().getRealPath("").indexOf("NextTeam")).replace("\\", "/");

        System.out.println("received request update proposal !");
        String type = request.getParameter("type");
        int propId = Integer.parseInt(request.getParameter("id"));
        int result = 0;

        if (!type.equals("status")) {

            String title = request.getParameter("title");
            String content = request.getParameter("content");

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
            result = new ProposalDAO(Global.generateConnection()).updateProposal(new Proposal(propId, title, content, "pending", new Date()));

            if (numOfFile > 0) {
                // Đẩy files lên cloud
                GoogleDriveUploader googleService = new GoogleDriveUploader(PARENT_FOLDER_ID, projectLocation);
                result = 0;

                for (int i = 0; i < numOfFile; i++) {
                    Part filePart = request.getPart("filescontent[" + i + "]");
                    InputStream fileInputStream = filePart.getInputStream();
                    byte[] fileBytes = fileInputStream.readAllBytes();

                    byte[] decodedBytes = Base64.getDecoder().decode(fileBytes);
                    try {
                        CloudFileInfo cloudFile = googleService.uploadFile(filesName.get(i), filesType.get(i), decodedBytes);

                        FileRecord fileRecord = new FileRecord(cloudFile.fileId, Integer.toString(propId), filesType.get(i), filesName.get(i), cloudFile.downloadLink, cloudFile.viewLink);

                        result = new FileStorageDAO(Global.generateConnection()).createFileRecord(fileRecord);
                    } catch (GeneralSecurityException ex) {
                        Logger.getLogger(ProposalServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

            if (numOfDeleteFile > 0) {
                GoogleDriveUploader googleService = new GoogleDriveUploader(PARENT_FOLDER_ID, projectLocation);

                for (int i = 0; i < numOfDeleteFile; i++) {
                    try {
                        googleService.deleteFile(deleteFileId.get(i));
                    } catch (GeneralSecurityException ex) {
                        Logger.getLogger(ProposalServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    new FileStorageDAO(Global.generateConnection()).deleteFileRecord(deleteFileId.get(i));

                }
            }
        } else {
            String status = request.getParameter("status");
            result = new ProposalDAO(Global.generateConnection()).updateProposalStatus(propId + "", status);
            System.out.println("status");

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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        
        projectLocation= getServletContext().getRealPath("").substring(0, getServletContext().getRealPath("").indexOf("NextTeam")).replace("\\", "/");

        // xóa các file trên cloud
        GoogleDriveUploader googleService = new GoogleDriveUploader(PARENT_FOLDER_ID, projectLocation);
        List<String> fileIds = new FileStorageDAO(Global.generateConnection()).getAllFileIdByPropId(id);
        if (!fileIds.isEmpty()) {
            for (int i = 0; i < fileIds.size(); i++) {
                try {
                    googleService.deleteFile(fileIds.get(i));
                } catch (GeneralSecurityException ex) {
                    Logger.getLogger(ProposalServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // xóa các bản ghi file
        new FileStorageDAO(Global.generateConnection()).deleteAllFileRecordByPropId(id);

        // xóa proposal
        int result = new ProposalDAO(Global.generateConnection()).deleteProposalById(id);

        String resJsonString = this.gson.toJson(result == 1 ? new Success("success") : new Success("failure"));
        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();

    }

}
