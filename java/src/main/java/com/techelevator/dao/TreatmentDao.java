package com.techelevator.dao;

import com.techelevator.model.Treatment;

import java.util.List;

public interface TreatmentDao {

//    List<Treatment> getAllCareEvents(int userId);

    //List<Treatment> getSinglePlantEvents(int plantId);

    Treatment createCareEvent(Treatment careEvent);

}
