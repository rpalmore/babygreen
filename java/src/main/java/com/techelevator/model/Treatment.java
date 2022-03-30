package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Treatment {
    private int careId;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate careDate;
    private String careType;

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
}
