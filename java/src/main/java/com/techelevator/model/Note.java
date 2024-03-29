package com.techelevator.model;

import java.sql.Date;
import java.time.LocalDate;

public class Note {
    private int noteId;
    private int plantId;
    private String note;
    private String noteImg;
    private LocalDate createdOn;
    // private Date createdOn;

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
