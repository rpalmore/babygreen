package com.techelevator.model;

import java.time.LocalDate;

public class LatestTreatment {

    private String careType;
    private LocalDate careDate;
    private int careId;
    private int numPlants;

    public String getCareType() {
        return careType;
    }

    public void setCareType(String careType) {
        this.careType = careType;
    }

    public LocalDate getCareDate() {
        return careDate;
    }

    public void setCareDate(LocalDate careDate) {
        this.careDate = careDate;
    }

    public int getCareId() {
        return careId;
    }

    public void setCareId(int careId) {
        this.careId = careId;
    }

    public int getNumPlants() {
        return numPlants;
    }

    public void setNumPlants(int numPlants) {
        this.numPlants = numPlants;
    }
}
