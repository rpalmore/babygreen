package com.techelevator.dao;

import com.techelevator.model.Note;

import java.util.List;

public interface NoteDao {

    List<Note> getAllNotes(int plantId);

    Note createNote(Note newNote);

    void editNote(Note updatedNote);

    void deleteNote(int noteId);

}
