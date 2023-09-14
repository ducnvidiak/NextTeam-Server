package Model;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Student implements Serializable {
    private int id;
    private String name;
    private char gender;
    private Date dob;
    private String userName, pass;
    public Student() {
    }
     public Student(int id, String name, String gender, Date dob, String userName, String pass) {
        this.id = id;
        this.name = name;
        this.gender = gender.charAt(0);
        this.dob = dob;
        this.userName = userName;
        this.pass = pass;
    }
     
     
     
    public Student(String name, String gender, String dob) {
        this.name = name;
        this.gender = gender.charAt(0);
        setDob(dob);
    }
    
    public Student(int id, String name, String gender, Date dob) {
        this.id = id;
        this.name = name;
        this.gender = gender.charAt(0);
        this.dob = dob;
    }
    public Student(Student s){
        this(s.id,s.name,s.getGender(),s.dob);
    }
    public Student(int id){
        this(StudentDB.getStudent(id));
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public String getGender() {
        switch(gender){
            case 'M': return "Male";
            case 'F': return "Female";
            case 'L': return "LGBT";
            default: return "Other";
        }
    }
    
    public void setGender(String gender) {
        this.gender = gender.charAt(0);
    }

    public String getDob() {
        SimpleDateFormat sd= new SimpleDateFormat("dd/MM/yyyy");
        return sd.format(dob);
    }

    public Date getDateOB(){
        return dob;
    }
    public void setDob(String dob) {
        SimpleDateFormat sd= new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.dob = new Date(sd.parse(dob).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Invalid DOB");
        }
    }
    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + name + ", gender=" + getGender() + ", dob=" + getDob() + '}';
    }

    public int addNew(){
        return StudentDB.newStudent(this);
    }

    public Student update(){
        return StudentDB.update(this);
    }
    
    public int delete(){
        return StudentDB.delete(id);
    }
}
