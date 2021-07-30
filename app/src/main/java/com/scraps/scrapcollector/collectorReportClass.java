package com.scraps.scrapcollector;

public class collectorReportClass {
    private int saleid;
    private String date;
    private String username;
    private String usercontact;
    private String scraptype;
    private String weight;
    private int totalprice;

    public collectorReportClass(int saleid, String date, String username, String usercontact, String scraptype, String weight, int totalprice) {
        this.saleid=saleid;
        this.date=date;
        this.username=username;
        this.usercontact=usercontact;
        this.scraptype=scraptype;
        this.weight=weight;
        this.totalprice=totalprice;
    }

    public int getSaleid() {
        return saleid;
    }

    public void setSaleid(int saleid) {
        this.saleid = saleid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsercontact() {
        return usercontact;
    }

    public void setUsercontact(String usercontact) {
        this.usercontact = usercontact;
    }

    public String getScraptype() {
        return scraptype;
    }

    public void setScraptype(String scraptype) {
        this.scraptype = scraptype;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }
}
