package com.techelevator.dao;

import com.techelevator.model.Treatment;

import java.util.ArrayList;
import java.util.List;

public interface TreatmentDao {

    //List<Treatment> getAllTreats(int userId);

    List<Treatment> getSinglePlantEvents(int plantId);

    Treatment createCareEvent(Treatment careEvent);

}
