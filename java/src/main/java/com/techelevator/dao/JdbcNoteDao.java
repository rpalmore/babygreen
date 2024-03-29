package com.techelevator.dao;

import com.techelevator.model.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcNoteDao implements NoteDao {

    private JdbcTemplate template;

    private JdbcNoteDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Note> getAllNotes(int userId) {

        List<Note> noteList = new ArrayList<>();

        String sql = "SELECT note_id, notes.plant_id, note, note_img, created_on " +
                "FROM notes " +
                "JOIN plants on plants.plant_id = notes.plant_id " +
                "WHERE plants.user_id = ?" +
                "ORDER BY created_on DESC";
        SqlRowSet results = template.queryForRowSet(sql, userId);

        while (results.next()) {
            Note note = new Note();
            note.setNoteId(results.getInt("note_id"));
            note.setPlantId(results.getInt("plant_id"));
            note.setNote(results.getString("note"));
            note.setNote_img(results.getString("note_img"));
            note.setCreatedOn(results.getDate("created_on").toLocalDate());

            noteList.add(note);
        }

        return noteList;
    }


    @Override
    public Note createNote(Note newNote) {

        String sql = "INSERT INTO notes(plant_id, note, note_img, created_on) " +
                "VALUES(?, ?, ?, ?) RETURNING note_id";
        int noteId = template.queryForObject(sql, Integer.class, newNote.getPlantId(), newNote.getNote(),
               newNote.getNoteImg(), newNote.getCreatedOn());

        newNote.setNoteId(noteId);
        return newNote;
    }

    @Override
    public void editNote(Note updatedNote) {

        String sql = "UPDATE notes " +
                "SET note = ?, note_img = ? " +
                "WHERE note_id = ?";
        template.update(sql, updatedNote.getNote(), updatedNote.getNoteImg(), updatedNote.getNoteId());
    }

    @Override
    public void deleteNote(int noteId) {

        String sql = "DELETE FROM notes WHERE note_id = ?";
        template.update(sql, noteId);
    }

}

