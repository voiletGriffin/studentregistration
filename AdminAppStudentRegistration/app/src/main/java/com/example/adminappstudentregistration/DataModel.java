package com.example.adminappstudentregistration;

public class DataModel {

    String year,name,rollno,branch,email;
    boolean approval;
    public DataModel() {
    }

    public DataModel(String year, String name, String rollno, String branch, String email) {
        this.year = year;
        this.name = name;
        this.rollno = rollno;
        this.branch = branch;
        this.email = email;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public String getyear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
