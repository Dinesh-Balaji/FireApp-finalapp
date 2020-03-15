package com.example.fireapp;

public class rating
{
    private String avg;
    private String num;

    public rating(String avg, String num)
    {
        this.avg = avg;
        this.num = num;
    }
    rating()
    {}

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
