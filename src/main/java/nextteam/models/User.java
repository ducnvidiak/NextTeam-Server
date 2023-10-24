/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author vnitd
 */
public class User {

    private int id;
    private String email;
    private String username;
    private String password;
    private String avatarURL;
    private String bannerURL;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String major;
    private String academicYear;
    private String gender;
    private String dob;
    private String homeTown;
    private String facebookUrl;
    private String linkedInUrl;
    private String createdAt;
    private String updatedAt;
    private boolean isActive;
    private boolean isAdmin;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }
    public User(int id, String avatarURL) {
        this.id = id;
        this.avatarURL = avatarURL;
    }

    public User(String email, String username, String password, String phoneNumber, String gender) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public User(String email, String username, String password, String firstname, String lastname, String phoneNumber) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
    }

    public User(int id, String email, String username, String password, String avatarURL, String bannerURL,
            String firstname, String lastname, String phoneNumber, String major, String academicYear, String gender,
            String dob, String homeTown, String facebookUrl, String linkedInUrl, String createdAt, String updatedAt,
            boolean isActive, boolean isAdmin) {

        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.avatarURL = avatarURL;
        this.bannerURL = bannerURL;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.major = major;
        this.academicYear = academicYear;
        this.gender = gender;
        this.dob = dob;
        this.homeTown = homeTown;
        this.facebookUrl = facebookUrl;
        this.linkedInUrl = linkedInUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getBannerURL() {
        return bannerURL;
    }

    public void setBannerURL(String bannerURL) {
        this.bannerURL = bannerURL;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        if (dob == null) {
            Date firstDate = new Date(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(firstDate);
            return formattedDate;
        }
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getLinkedInUrl() {
        return linkedInUrl;
    }

    public void setLinkedInUrl(String linkedInUrl) {
        this.linkedInUrl = linkedInUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + id +
                ", \"email\": \"" + email + "\"" +
                ", \"username\": \"" + username + "\"" +
                ", \"password\": \"" + password + "\"" +
                ", \"avatarURL\": \"" + avatarURL + "\"" +
                ", \"bannerURL\": \"" + bannerURL + "\"" +
                ", \"firstname\": \"" + firstname + "\"" +
                ", \"lastname\": \"" + lastname + "\"" +
                ", \"phoneNumber\": \"" + phoneNumber + "\"" +
                ", \"major\": \"" + major + "\"" +
                ", \"academicYear\": \"" + academicYear + "\"" +
                ", \"gender\": \"" + gender + "\"" +
                ", \"dob\": \"" + dob + "\"" +
                ", \"homeTown\": \"" + homeTown + "\"" +
                ", \"facebookUrl\": \"" + facebookUrl + "\"" +
                ", \"linkedInUrl\": \"" + linkedInUrl + "\"" +
                ", \"createdAt\": \"" + getCreatedAt() + "\"" +
                ", \"updatedAt\": \"" + getUpdatedAt() + "\"" +
                ", \"isAdmin\": \"" + isAdmin + "\"" +
                ", \"isActive\": " + isActive +
                "}";
    }

    public String getFullname() {
        return firstname + " " + lastname;
    }
}
