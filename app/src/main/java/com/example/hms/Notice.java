package com.example.hms;

public class Notice {
    public String title;
    public String content;
    public String issuingAuthority;
    public String date;
    public String hallCode;

    public Notice() {
    }

    public Notice(String title, String content, String issuingAuthority, String date, String hallCode) {
        this.title = title;
        this.content = content;
        this.issuingAuthority = issuingAuthority;
        this.date = date;
        this.hallCode = hallCode;
    }

}
