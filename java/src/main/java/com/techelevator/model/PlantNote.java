package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class PlantNote {
    private int noteId;
    private int plantId;
    private String note;
    private String noteImg;
    @JsonFormat(pattern="dd-MM-yyyy")
//    @JsonFormat(pattern="dddd-MMM-yyyy")
    private LocalDate createdOn;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteImg() {
        return noteImg;
    }

    public void setNote_img(String noteImg) {
        this.noteImg = noteImg;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }
}
