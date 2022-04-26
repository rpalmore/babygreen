package com.techelevator.dao;

import com.techelevator.model.TreatmentDetails;

import java.util.List;

public interface TreatmentDetailsDao {

    List<TreatmentDetails> getAllTreatments(int userId);

    List<TreatmentDetails> getLatestWaterings(int userId);

}
