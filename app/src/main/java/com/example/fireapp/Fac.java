package com.example.fireapp;

public class Fac
{
    private String facid;
    private String name;
    private String password;

    public Fac(String facid, String name, String password)
    {
        this.facid = facid;
        this.name = name;
        this.password = password;
    }
    public Fac()
    { }

    public String getFacid() {
        return facid;
    }

    public void setFacid(String facid) {
        this.facid = facid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
