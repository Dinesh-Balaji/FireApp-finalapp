package com.example.fireapp;

public class reviews
{
    private String time;
    private String comment;

    public reviews(String time, String comment)
    {
        this.time = time;
        this.comment = comment;
    }
    reviews()
    {}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
