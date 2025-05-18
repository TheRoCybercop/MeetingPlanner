// Meeting.java
package com.example.meetingplanner.models;

public class Meeting {
    private String title;
    private String location;
    private String date;
    private String time;

    // Constructor
    public Meeting(String title, String location, String date, String time) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.time = time;
    }

    // Getters
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}