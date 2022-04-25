package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.model.LatestTreatment;
import com.techelevator.model.Treatment;
import com.techelevator.model.TreatmentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")

public class TreatmentController {

    @Autowired
    TreatmentDao treatmentDao;

    @Autowired
    TreatmentDetailsDao treatmentDetailsDao;

    @Autowired
    PlantTreatmentDao plantTreatmentDao;

    @Autowired
    LatestTreatmentDao latestTreatmentDao;

    @Autowired
    TreatmentTestDao treatmentTestDao;

    @Autowired
    UserDao userDao;

    @RequestMapping(path="/createTreatment", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Treatment createTreatment(@RequestBody Treatment newTreatment) {
        return treatmentDao.createCareEvent(newTreatment);
    }

    @RequestMapping(path="/plants/{plantId}/care", method=RequestMethod.GET)
    public List<Treatment> getPlantTreatments(@PathVariable int plantId) {
        return treatmentDao.getSinglePlantEvents(plantId);
    }

    @RequestMapping(path="/care", method=RequestMethod.GET)
    public List<TreatmentDetails> getTreatmentDetails(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        return treatmentDetailsDao.getAllTreatments(userId);
    }

//    @RequestMapping(path="/care", method=RequestMethod.GET)
//    public List<TreatmentTest> getTreatmentTest(Principal principal) {
//        String username = principal.getName();
//        int userId = userDao.findIdByUsername(username);
//        return treatmentTestDao.getAllTreatmentsTest(userId);
//    }

    @RequestMapping(path="/care/latest", method=RequestMethod.GET)
    public LatestTreatment getLatestTreatment(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        return latestTreatmentDao.getLatestTreatment(userId);
    }

    @RequestMapping(path="/deleteTreatment/{plantId}/{careId}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTreatment(@PathVariable int plantId, @PathVariable int careId) {
        plantTreatmentDao.deletePlantTreatment(plantId, careId);
    }



}
