package com.techelevator.dao;

import java.time.LocalDate;

public interface PlantTreatmentDao {

    void deletePlantTreatment(int plantId, int careId);

    void deletePlantTreatmentsByDate(LocalDate careDate);

}
