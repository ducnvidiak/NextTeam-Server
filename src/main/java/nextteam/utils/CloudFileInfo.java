/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils;

/**
 *
 * @author admin
 */
public class CloudFileInfo {
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
