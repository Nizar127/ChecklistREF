package com.CEFS.checklist.Model;

public class TheUserData {
    String name;
    String ID;
    String password;
    String designation;

    public TheUserData(){

    }

    public TheUserData(String name, String ID, String password, String designation) {
        this.name = name;
        this.ID = ID;
        this.password = password;
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
