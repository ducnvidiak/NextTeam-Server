/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

/**
 *
 * @author admin
 */
public class Success {
    public String message;
    public String avatarURL;

    public Success() {
    }
    
    public Success(String mess) {
        this.message = mess;
    }
    public Success(String mess, String avatar) {
        this.message = mess;
        this.avatarURL = avatar;
    }
}
