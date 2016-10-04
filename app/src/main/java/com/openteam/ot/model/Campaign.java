package com.openteam.ot.model;

/**
 * Created by zoz on 27/09/2016.
 */

public class Campaign {
    private String id;
    private String project_title;
    private String campaign_title;
    private String picture_url;
    private String country;
    private String mission;
    private String why_support;
    private String raised_amount;
    private Theme[] themes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getCampaign_title() {
        return campaign_title;
    }

    public void setCampaign_title(String campaign_title) {
        this.campaign_title = campaign_title;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getWhy_support() {
        return why_support;
    }

    public void setWhy_support(String why_support) {
        this.why_support = why_support;
    }

    public String getRaised_amount() {
        return raised_amount;
    }

    public void setRaised_amount(String raised_amount) {
        this.raised_amount = raised_amount;
    }

    public Theme[] getThemes() {
        return themes;
    }

    public void setThemes(Theme[] themes) {
        this.themes = themes;
    }
}
