package com.techelevator.dao;

import com.techelevator.model.PlantNote;

import java.util.List;

public interface PlantNoteDao {

    List<PlantNote> getAllNotes(int plantId);

    PlantNote createNote(PlantNote newNote);

    void editNote(PlantNote updatedNote);

    void deleteNote(int noteId);

}
