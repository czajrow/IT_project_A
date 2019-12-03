package com.example.scheduler;

public class Event {

    private String title;
    private String description;
    private String presenter;
    private String email;
    private String location;
    private String lat;
    private String lng;
    private String start;
    private String end;
    private String duration;

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

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Event{" + "\n\t\t" +
                "title='" + title + "\',\n\t\t" +
                "description='" + description + "\',\n\t\t" +
                "presenter='" + presenter + "\',\n\t\t" +
                "email='" + email + "\',\n\t\t" +
                "location='" + location + "\',\n\t\t" +
                "lat='" + lat + "\',\n\t\t" +
                "lng='" + lng + "\',\n\t\t" +
                "start='" + start + "\',\n\t\t" +
                "end='" + end + "\',\n\t\t" +
                "duration='" + duration + "\',\n" +
                '}' + '\n';
    }
}
