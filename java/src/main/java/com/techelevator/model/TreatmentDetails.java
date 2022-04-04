package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class TreatmentDetails {

    // @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate careDate;
    private String careType;
    private String plantName;
    private int careId;

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

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public int getCareId() {
        return careId;
    }

    public void setCareId(int careId) {
        this.careId = careId;
    }
}

