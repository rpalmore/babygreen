package com.techelevator.controller;

import com.techelevator.dao.NoteDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class NoteController {

    @Autowired
    NoteDao noteDao;

    @Autowired
    UserDao userDao;

    @RequestMapping(path="/plants/notes", method=RequestMethod.GET)
    public List<Note> getNotes(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        return noteDao.getAllNotes(userId);
    }

    @RequestMapping(path="/createNote", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@RequestBody Note newNote) {
        return noteDao.createNote(newNote);
    }

    @RequestMapping(path="/editNote", method=RequestMethod.PUT)
    public void editNote(@RequestBody Note editedNote) {
        noteDao.editNote(editedNote);
    }

    @RequestMapping(path="/deleteNote/{noteId}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeNote(@PathVariable int noteId) {
        noteDao.deleteNote(noteId);
    }

}
