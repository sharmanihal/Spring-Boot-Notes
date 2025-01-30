package com.example.springmvc.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "student")
public class CollegeStudent implements Student {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column(name="email_address")
    private String emailAddress;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private List<MathGrade> mathGrades;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private List<ScienceGrade> scienceGrades;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private List<HistoryGrade> historyGrades;

    public CollegeStudent() {

    }

    public CollegeStudent(String firstname, String lastname, String emailAddress) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<MathGrade> getMathGrades() {
        return mathGrades;
    }

    public void setMathGrades(List<MathGrade> mathGrades) {
        this.mathGrades = mathGrades;
    }

    public List<ScienceGrade> getScienceGrades() {
        return scienceGrades;
    }

    public void setScienceGrades(List<ScienceGrade> scienceGrades) {
        this.scienceGrades = scienceGrades;
    }

    public List<HistoryGrade> getHistoryGrades() {
        return historyGrades;
    }

    public void setHistoryGrades(List<HistoryGrade> historyGrades) {
        this.historyGrades = historyGrades;
    }

    public String getFullName() {
        return getFirstname() + " " + getLastname();
    }

    @Override
    public String toString() {
        return "CollegeStudent{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }

    public String studentInformation() {
       return getFullName() + " " + getEmailAddress();
    }
}
