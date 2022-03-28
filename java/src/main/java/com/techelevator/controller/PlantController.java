package com.techelevator.controller;

import com.techelevator.dao.PlantDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class PlantController {

    @Autowired
    PlantDao plantDao;

    @Autowired
    UserDao userDao;

    @RequestMapping(path="/plants", method=RequestMethod.GET)
    public List<Plant> getPlants(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        return plantDao.getAllPlants(userId);
    }

    @RequestMapping(path="/plants/{plantId}", method=RequestMethod.GET)
    public Plant getPlant(@PathVariable int plantId) {
        return plantDao.getSinglePlant(plantId);
    }

    @RequestMapping(path="/createPlant", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Plant createPlant(@RequestBody Plant newPlant) {
        return plantDao.createPlant(newPlant);
    }

    @RequestMapping(path="/editPlant", method=RequestMethod.PUT)
    public void editPlant(@RequestBody Plant editedPlant) {
        plantDao.editPlant(editedPlant);
    }

    @RequestMapping(path="/deletePlant/{plantId}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePlant(@PathVariable int plantId) {
        plantDao.deletePlant(plantId);
    }

}
