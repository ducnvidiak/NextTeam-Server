/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

/**
 *
 * @author vnitd
 */
public class Attendance {

    int id;
    String note;
    boolean atten;

    public Attendance(int id, String note, boolean atten) {
        this.id = id;
        this.note = note;
        this.atten = atten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isAtten() {
        return atten;
    }

    public void setAtten(boolean atten) {
        this.atten = atten;
    }

}
