package com.techelevator.controller;

import com.techelevator.dao.PlantNoteDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.PlantNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class PlantNoteController {

    @Autowired
    PlantNoteDao plantNoteDao;

    @Autowired
    UserDao userDao;

    @RequestMapping(path="/plants/{plantId}/notes", method=RequestMethod.GET)
    public List<PlantNote> getNotes(@PathVariable int plantId) {
        return plantNoteDao.getAllNotes(plantId);
    }

    @RequestMapping(path="/createNote", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public PlantNote createNote(@RequestBody PlantNote newNote) {
        return plantNoteDao.createNote(newNote);
    }

    @RequestMapping(path="/editNote", method=RequestMethod.PUT)
    public void editNote(@RequestBody PlantNote editedNote) {
        plantNoteDao.editNote(editedNote);
    }

    @RequestMapping(path="/deleteNote/{noteId}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeNote(@PathVariable int noteId) {
        plantNoteDao.deleteNote(noteId);
    }

}
