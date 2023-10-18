/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package nextteam.controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextteam.Global;
import nextteam.models.FileRecord;
import nextteam.utils.database.FileStorageDAO;

/**
 *
 * @author admin
 */
public class ProposalFiileServlet extends HttpServlet {
    private final Gson gson = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        
        List<FileRecord> fileList;
        if (type.equals("one")) {
            fileList = new FileStorageDAO(Global.generateConnection()).getListFileRecordByPropId(id);
        } else {
            fileList = new FileStorageDAO(Global.generateConnection()).getAllFileRecordByUserId(id);
        }
        String resJsonString = this.gson.toJson(fileList);

        PrintWriter out = response.getWriter();
        out.print(resJsonString);
        out.flush();
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
//        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
