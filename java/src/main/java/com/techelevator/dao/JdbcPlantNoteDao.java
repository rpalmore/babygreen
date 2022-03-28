package com.techelevator.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techelevator.model.PlantNote;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPlantNoteDao implements PlantNoteDao {

    private JdbcTemplate template;

    private JdbcPlantNoteDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<PlantNote> getAllNotes(int plantId) {

        List<PlantNote> noteList = new ArrayList<>();

        String sql = "SELECT note_id, note, note_img, created_on " +
                "FROM plant_notes WHERE plant_id = ? " +
                "ORDER BY note_id DESC";
        SqlRowSet results = template.queryForRowSet(sql, plantId);

        while (results.next()) {
            PlantNote note = new PlantNote();
            note.setNoteId(results.getInt("note_id"));
            note.setPlantId(plantId);
            note.setNote(results.getString("note"));
            note.setNote_img(results.getString("note_img"));
            note.setCreatedOn(results.getDate("created_on").toLocalDate());

            noteList.add(note);
        }

        return noteList;
    }

    @Override
    public PlantNote createNote(PlantNote newNote) {

        String sql = "INSERT INTO plant_notes(plant_id, note, note_img, created_on) " +
                "VALUES(?, ?, ?, DEFAULT) RETURNING note_id";
        int noteId = template.queryForObject(sql, Integer.class, newNote.getPlantId(), newNote.getNote(),
               newNote.getNoteImg());

        newNote.setNoteId(noteId);
        return newNote;
    }

    @Override
    public void editNote(PlantNote updatedNote) {

        String sql = "UPDATE plant_notes " +
                "SET note = ?, note_img = ? " +
                "WHERE note_id = ?";
        template.update(sql, updatedNote.getNote(), updatedNote.getNoteImg(), updatedNote.getNoteId());
    }

    @Override
    public void deleteNote(int noteId) {

        String sql = "DELETE FROM plant_notes WHERE note_id = ?";
        template.update(sql, noteId);

    }

}

