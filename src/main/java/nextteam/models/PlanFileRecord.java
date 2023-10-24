/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

/**
 *
 * @author admin
 */
public class PlanFileRecord {
    private int id;
    private String fileId;
    private String planId;
    private String type;
    private String name;
    private String downloadLink;
    private String viewLink;

    public PlanFileRecord(String fileId, String planId, String type, String name, String downloadLink, String viewLink) {
        this.fileId = fileId;
        this.planId = planId;
        this.type = type;
        this.name = name;
        this.downloadLink = downloadLink;
        this.viewLink = viewLink;
    }

    public PlanFileRecord(int id, String fileId, String planId, String type, String name, String downloadLink, String viewLink) {
        this.id = id;
        this.fileId = fileId;
        this.planId = planId;
        this.type = type;
        this.name = name;
        this.downloadLink = downloadLink;
        this.viewLink = viewLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }

    
}
