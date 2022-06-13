package com.example.cms;

public class event {
    boolean isShrink;
    String Name,Date,Description,Banner;

    public String getBanner() {
        return Banner;
    }

    public void setBanner(String banner) {
        Banner = banner;
    }

    public event(){}

    public event(String name, String date, String description, String banner) {
        Name = name;
        Date = date;
        Description = description;
        Banner = banner;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
    public boolean isShrink() {

        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }

}
