package com.scraps.scrapcollector;

public class Scrap {
    private int scrapid;
    private String scrapname;
    private String scrapprice;
    private String scrapdis;
    private byte[] image;

    public Scrap(String scrapname, String scrapprice, String scrapdis, byte[] image,int scrapid) {
        this.scrapname = scrapname;
        this.scrapprice = scrapprice;
        this.scrapdis = scrapdis;
        this.image = image;
        this.scrapid=scrapid;
    }

    public String getScrapname() {
        return scrapname;
    }

    public void setScrapname(String scrapname) {
        this.scrapname = scrapname;
    }

    public String getScrapprice() {
        return scrapprice;
    }

    public void setScrapprice(String scrapprice) {
        this.scrapprice = scrapprice;
    }

    public String getScrapdis() {
        return scrapdis;
    }

    public void setScrapdis(String scrapdis) {
        this.scrapdis = scrapdis;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getScrapid() {
        return scrapid;
    }

    public void setScrapid(int scrapid) {
        this.scrapid = scrapid;
    }
}
