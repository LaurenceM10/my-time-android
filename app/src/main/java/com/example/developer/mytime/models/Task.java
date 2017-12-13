package com.example.developer.mytime.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by developer on 7/12/2017.
 */

@IgnoreExtraProperties
public class Task {
    private int id;
    private String title;
    private String description;
    private Location location;
    private Date taskDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }
}
