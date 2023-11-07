/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nextteam.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
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
import nextteam.models.PlanDetail;
import nextteam.models.Success;
import nextteam.utils.database.PlanFileStorageDAO;
import nextteam.utils.database.PlanDAO;
import nextteam.utils.CloudFileInfo;
import nextteam.utils.GoogleDriveUploader;

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

    private final Gson gson = new Gson();
    String PARENT_FOLDER_ID = "1zyAwqcpX-niyS2ULWCryelF_CCJaN9Sv";
    String projectLocation= "";
//    "E:/Fall23/project/"

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
        } else if (type.equals("all")) {
            List<PlanDetail> p = new PlanDAO(Global.generateConnection()).getListAllPlans();
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
        
        projectLocation= getServletContext().getRealPath("").substring(0, getServletContext().getRealPath("").indexOf("NextTeam")).replace("\\", "/");

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
            GoogleDriveUploader googleService = new GoogleDriveUploader(PARENT_FOLDER_ID, projectLocation);
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
        
        projectLocation= getServletContext().getRealPath("").substring(0, getServletContext().getRealPath("").indexOf("NextTeam")).replace("\\", "/");

        String type = request.getParameter("type");
        JsonObject jsonRes = new JsonObject();
        int planId = Integer.parseInt(request.getParameter("id"));
        int result = 0;

        if (type.equals("changeStatus")) {
            String status = request.getParameter("status");
            System.out.println("received request update plan status !");

            String feedback = request.getParameter("feedback");

            result = new PlanDAO(Global.generateConnection()).updatePlanStatus(planId + "", status, feedback);
            if (result == 1) {
                jsonRes.addProperty("planStatus", (result == 1 ? status : "failure"));
            }
        } else {

            System.out.println("received request update plan !");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
//            String planResponse = request.getParameter("response");

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
            result = new PlanDAO(Global.generateConnection()).updatePlan(new Plan(planId, title, content, "pending", new Date()));

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

                        PlanFileRecord fileRecord = new PlanFileRecord(cloudFile.fileId, Integer.toString(planId), filesType.get(i), filesName.get(i), cloudFile.downloadLink, cloudFile.viewLink);

                        result = new PlanFileStorageDAO(Global.generateConnection()).createPlanFileRecord(fileRecord);
                    } catch (GeneralSecurityException ex) {
                        Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

            if (numOfDeleteFile > 0) {
                GoogleDriveUploader googleService = new GoogleDriveUploader(PARENT_FOLDER_ID, projectLocation);

                for (int i = 0; i < numOfDeleteFile; i++) {
                    try {
                        googleService.deleteFile(deleteFileId.get(i));

                        new PlanFileStorageDAO(Global.generateConnection()).deleteFileRecord(deleteFileId.get(i));
                    } catch (GeneralSecurityException ex) {
                        Logger.getLogger(PlanServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

            // Gửi file lên cloud
            // Tạo bản ghi lưu trữ file
            // Xác nhận mọi thứ đều ổn
            if (planId != 0) {
                jsonRes.addProperty("planId", planId);
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
        
        projectLocation= getServletContext().getRealPath("").substring(0, getServletContext().getRealPath("").indexOf("NextTeam")).replace("\\", "/");

        // xóa các file trên cloud
        GoogleDriveUploader googleService = new GoogleDriveUploader(PARENT_FOLDER_ID, projectLocation);
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
