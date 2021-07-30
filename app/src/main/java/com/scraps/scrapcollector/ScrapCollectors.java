package com.scraps.scrapcollector;

public class ScrapCollectors {

    private int scrapcollectorid;
    private String scrapcollectorname;
    private String scrapcollectoremail;
    private String scrapcollectorcontact;
    private String scrapcollectorregdate;

    public ScrapCollectors(int scrapcollectorid, String scrapcollectorname, String scrapcollectoremail, String scrapcollectorcontact, String scrapcollectorregdate) {
        this.scrapcollectorid = scrapcollectorid;
        this.scrapcollectorname = scrapcollectorname;
        this.scrapcollectoremail = scrapcollectoremail;
        this.scrapcollectorcontact = scrapcollectorcontact;
        this.scrapcollectorregdate=scrapcollectorregdate;
    }

    public int getScrapcollectorid() {
        return scrapcollectorid;
    }

    public void setScrapcollectorid(int scrapcollectorid) {
        this.scrapcollectorid = scrapcollectorid;
    }

    public String getScrapcollectorname() {
        return scrapcollectorname;
    }

    public void setScrapcollectorname(String scrapcollectorname) {
        this.scrapcollectorname = scrapcollectorname;
    }

    public String getScrapcollectoremail() {
        return scrapcollectoremail;
    }

    public void setScrapcollectoremail(String scrapcollectoremail) {
        this.scrapcollectoremail = scrapcollectoremail;
    }

    public String getScrapcollectorcontact() {
        return scrapcollectorcontact;
    }

    public void setScrapcollectorcontact(String scrapcollectorcontact) {
        this.scrapcollectorcontact = scrapcollectorcontact;
    }

    public String getScrapcollectorregdate() {
        return scrapcollectorregdate;
    }

    public void setScrapcollectorregdate(String scrapcollectorregdate) {
        this.scrapcollectorregdate = scrapcollectorregdate;
    }
}
