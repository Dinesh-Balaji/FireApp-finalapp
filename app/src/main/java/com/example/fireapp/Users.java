package com.example.fireapp;

public class Users
{
    private String stid;
    private String name;
    private String email;
    private String password;
    public Users()
    { }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users(String stid, String name, String email, String password)
    {
        this.email=email;
        this.name=name;
        this.stid=stid;
        this.password=password;
    }

    public Users(String stid, String name, String email)
    {
        this.email=email;
        this.name=name;
        this.stid=stid;
    }

    public String getStid() {
        return stid;
    }

    public void setStid(String stid) {
        this.stid = stid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
