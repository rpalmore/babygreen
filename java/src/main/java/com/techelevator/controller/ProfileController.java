package com.techelevator.controller;

import com.techelevator.dao.ProfileDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    @Autowired
    UserDao userDao;

    @Autowired
    ProfileDao profileDao;

    @RequestMapping(path="/profile", method=RequestMethod.GET)
    public Profile returnProfileByUsername(Principal principal) {
        String username = principal.getName();
        return profileDao.getProfile(username);
    }

    @RequestMapping(path="/createProfile", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Profile createNewProfile(@RequestBody Profile profile) {
        return profileDao.createProfile(profile);
    }

    @RequestMapping(path="/editProfile", method=RequestMethod.PUT)
    public void editProfile(@RequestBody Profile updatedProfile) {
        profileDao.updateProfile(updatedProfile);
    }

    @RequestMapping(path="/deleteProfile/{username}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable String username) {
        profileDao.deleteProfile(username);
    }


}
