/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author vnitd
 */
public class Student {

    String id;
    String firstname;
    String lastname;
    Date birthDate;
    String homeTown;
    String gender;
    boolean isRegistered;

    public Student(String id, String firstname, String lastname, Date birthDate, String homeTown, String gender, boolean isRegistered) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.homeTown = homeTown;
        this.gender = gender;
        this.isRegistered = isRegistered;
    }

    public Student(String id, String firstname, String lastname, Date birthDate, String homeTown, String gender) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.homeTown = homeTown;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    @Override
    public String toString() {
        return """
               {
               "id": "%s",
               "firstname": "%s",
               "lastname": "%s",
               "birthDate": "%s",
               "homeTown": "%s",
               "gender": "%s",
               "isRegistered": "%s"
               }""".formatted(
                id,
                firstname,
                lastname,
                new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(birthDate.getTime())),
                homeTown,
                gender,
                isRegistered
        );
    }
}
