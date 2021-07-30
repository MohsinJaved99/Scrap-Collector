package com.scraps.scrapcollector;

public class collectorAppointment {

    private int appid;
    private int scid;
    private int userid;
    private String collector;
    private String date;
    private String time;
    private String entrydate;
    private String status;

    public collectorAppointment(int appid,int scid,int userid,String collector,String date,String time,String entrydate,String status) {
        this.appid = appid;
        this.scid = scid;
        this.userid = userid;
        this.collector = collector;
        this.date = date;
        this.time=time;
        this.entrydate=entrydate;
        this.status=status;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getScid() {
        return scid;
    }

    public void setScid(int scid) {
        this.scid = scid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
