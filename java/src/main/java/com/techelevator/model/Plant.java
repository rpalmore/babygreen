package com.techelevator.model;

import java.time.LocalDate;

public class Plant {
    private int plantId;
    private int userId;
    private String plantImg;
    private String plantName;
    private Boolean indoor;
    private String infoUrl;
    private LocalDate plantAge;
    private int plantSchedule;
    private String locationType;

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPlantImg() {
        return plantImg;
    }

    public void setPlantImg(String plantImg) {
        this.plantImg = plantImg;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public Boolean getIndoor() {
        return indoor;
    }

    public void setIndoor(Boolean indoor) {
        this.indoor = indoor;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public LocalDate getPlantAge() {
        return plantAge;
    }

    public void setPlantAge(LocalDate plantAge) {
        this.plantAge = plantAge;
    }

    public int getPlantSchedule() {
        return plantSchedule;
    }

    public void setPlantSchedule(int plantSchedule) {
        this.plantSchedule = plantSchedule;
    }

    public String getLocationType() {return locationType;}

    public void setLocationType(String locationType) {this.locationType = locationType;}
}
