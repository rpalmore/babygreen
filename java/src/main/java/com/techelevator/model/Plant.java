package com.techelevator.model;

public class Plant {
    private int plantId;
    private int userId;
    private String plantImg;
    private String plantName;
    private Boolean indoor;
    private String info_url;
    private int plant_age;

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

    public String getInfo_url() {
        return info_url;
    }

    public void setInfo_url(String info_url) {
        this.info_url = info_url;
    }

    public int getPlant_age() {
        return plant_age;
    }

    public void setPlant_age(int plant_age) {
        this.plant_age = plant_age;
    }
}
