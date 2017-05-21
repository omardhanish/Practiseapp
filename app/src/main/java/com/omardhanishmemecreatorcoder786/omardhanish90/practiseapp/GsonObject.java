package com.omardhanishmemecreatorcoder786.omardhanish90.practiseapp;

/**
 * Created by omar on 20-05-2017.
 */

public class GsonObject {

    private String from;
    private String to;
    private String duration;
    private String toName;
    private String datetime;



    public GsonObject(){
    }

    public GsonObject(String from,String to,String duration, String firstName , String datetime){
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.toName = firstName;
        this.datetime = datetime;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFirstName() {
        return toName;
    }

    public void setFirstName(String firstName) {
        this.toName = firstName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString()
    {
        return "Details [from=" + from + ", to=" + to + ", " +
                "duration=" + duration + ", name=" + toName + ", datetime=" + datetime + "]";
    }
}
