package pl.iwa.mstokfisz.model;

import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue

    private long id;
    private String firstname;
    private String lastname;
    private String studentID;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Mark> marks = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }
}
