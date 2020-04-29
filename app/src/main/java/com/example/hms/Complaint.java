package com.example.hms;


public class Complaint {

    public String complaintNo;
    public String category;
    public String title;
    public String content;
    public String rollNo;
    public String date;
    public String hallCode;

    public Complaint( String complaintNo,String category, String title, String content, String rollNo, String date, String hallCode) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.complaintNo = complaintNo;
        this.rollNo = rollNo;
        this.date = date;
        this.hallCode = hallCode;
    }

    public Complaint() {
    }
}
