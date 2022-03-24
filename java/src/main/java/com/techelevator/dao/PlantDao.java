package com.techelevator.dao;

import com.techelevator.model.Plant;

import java.util.List;

public interface PlantDao {

    List<Plant> getAllPlants(int userId);

    Plant getSinglePlant(int plantId);

    Plant createPlant(Plant newPlant);

    void updatePlant(Plant updatedPlant);

    void deletePlant(int plantId);

}
