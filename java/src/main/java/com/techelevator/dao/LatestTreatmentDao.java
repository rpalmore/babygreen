package com.techelevator.dao;

import com.techelevator.model.LatestTreatment;

public interface LatestTreatmentDao {

    LatestTreatment getLatestTreatment(int userId);
}
