package com.techelevator.model;

public class Profile {
    private int profileId;
    private String username;
    private String profileImg;
    private String favePlant;
    private String skillLevel;

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getFavePlant() {
        return favePlant;
    }

    public void setFavePlant(String favePlant) {
        this.favePlant = favePlant;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

}
