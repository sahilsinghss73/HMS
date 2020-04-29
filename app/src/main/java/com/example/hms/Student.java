package com.example.hms;


public class Student {
    public String rollNo;//
    public String name;//
    public String branchCode;
    public String dateOfBirth;
    public String emailID;//
    public String phoneNo;//
    public String hallCode;
    public String type;

    public Student(String rollNo, String name, String branchCode, String dateOfBirth, String emailID, String phoneNo, String hallCode,String type) {
        this.rollNo = rollNo;
        this.name = name;
        this.branchCode = branchCode;
        this.dateOfBirth = dateOfBirth;
        this.emailID = emailID;
        this.phoneNo = phoneNo;
        this.hallCode = hallCode;
        this.type = type;
    }

    public Student() {
    }
}
