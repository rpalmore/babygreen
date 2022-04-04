package com.techelevator.model;

import java.time.LocalDate;

public class Treatment {
    private int careId;
    private LocalDate careDate;
    private String careType;
    private int[] plantId;

    public int getCareId() {
        return careId;
    }

    public void setCareId(int careId) {
        this.careId = careId;
    }

    public LocalDate getCareDate() {
        return careDate;
    }

    public void setCareDate(LocalDate careDate) {
        this.careDate = careDate;
    }

    public String getCareType() {
        return careType;
    }

    public void setCareType(String careType) {
        this.careType = careType;
    }

    public int[] getPlantId() {
        return plantId;
    }

    public void setPlantId(int[] plantId) {
        this.plantId = plantId;
    }

}
