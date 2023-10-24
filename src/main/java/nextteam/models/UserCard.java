/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

/**
 *
 * @author admin
 */
public class UserCard {
    private int id;
    private String avatarURL;
    private String fullname;
    private String studentCode;
    private String gender;
    private int departmentId;
    private String status;

    public UserCard(int id, String avatarURL, String fullname, String studentCode, String gender) {
        this.id = id;
        this.avatarURL = avatarURL;
        this.fullname = fullname;
        this.studentCode = studentCode;
        this.gender = gender;
    }

    public UserCard(int id, String avatarURL, String fullname, String studentCode, String gender, int departmentId, String status) {
        this.id = id;
        this.avatarURL = avatarURL;
        this.fullname = fullname;
        this.studentCode = studentCode;
        this.gender = gender;
        this.departmentId = departmentId;
        this.status = status;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
