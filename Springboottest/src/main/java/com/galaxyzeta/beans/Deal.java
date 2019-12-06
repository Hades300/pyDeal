package com.galaxyzeta.beans;

import java.util.Date;

public class Deal {
    public Deal(int catId, String context, String title, Date ddl, int state) {
        this.catId = catId;
        this.context = context;
        this.title = title;
        this.ddl = ddl;
        this.state = state;
    }

    private int catId;
    private String context;
    private String title;
    private Date ddl;
    private int state;
    private int uid = 2333333;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDdl() {
        return ddl;
    }

    public void setDdl(Date ddl) {
        this.ddl = ddl;
    }
}
