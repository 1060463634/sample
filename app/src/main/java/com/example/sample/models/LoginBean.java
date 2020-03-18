package com.example.sample.models;

public class LoginBean {
    private String userId;
    private String sessionid;
    private String url;
    private String kamdellarloginKey;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKamdellarloginKey() {
        return kamdellarloginKey;
    }

    public void setKamdellarloginKey(String kamdellarloginKey) {
        this.kamdellarloginKey = kamdellarloginKey;
    }
}