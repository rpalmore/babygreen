package com.techelevator.dao;

import com.techelevator.model.Treatment;
import com.techelevator.model.TreatmentDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTreatmentDetailsDao implements TreatmentDetailsDao {

    private JdbcTemplate template;

    public JdbcTreatmentDetailsDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<TreatmentDetails> getAllTreatments(int userId) {

        List<TreatmentDetails> treatmentDetails = new ArrayList<>();

        String sql = "SELECT care_date, care_type, plant_name, plants.plant_id, treatments.care_id " +
                "FROM treatments " +
                "JOIN plants_treatments ON plants_treatments.care_id = treatments.care_id " +
                "JOIN plants on plants.plant_id = plants_treatments.plant_id " +
                "WHERE user_id = ? " +
                "ORDER BY care_date DESC";
        SqlRowSet results = template.queryForRowSet(sql, userId);

        while (results.next()) {
            TreatmentDetails details = new TreatmentDetails();
            details.setCareDate(results.getDate("care_date").toLocalDate());
            details.setCareType(results.getString("care_type"));
            details.setPlantName(results.getString("plant_name"));
            details.setPlantId(results.getInt("plant_id"));
            details.setCareId(results.getInt("care_id"));

            treatmentDetails.add(details);
        }

        return treatmentDetails;
    }

    @Override
    public List<TreatmentDetails> getLatestWaterings(int userId) {

        List<TreatmentDetails> latestWaterings = new ArrayList<>();

        /**
         * Consider passing care_type from client (treatment.care_type = ?)
         * so user can filter by most recent watering, fertilizing, repotting, etc.
         * This could be a useful data set to return as an enhancement.
         */
        String sql = "SELECT plants.plant_id, plant_name, MAX(treatments.care_date) " +
                "FROM plants " +
                "JOIN plants_treatments ON plants_treatments.plant_id = plants.plant_id " +
                "JOIN treatments ON treatments.care_id = plants_treatments.care_id " +
                "WHERE user_id = ? AND treatments.care_type = 'watered' " +
                "GROUP BY plants.plant_id";
        SqlRowSet results = template.queryForRowSet(sql, userId);

        while (results.next()) {
            TreatmentDetails treatment = new TreatmentDetails();
            treatment.setCareDate(results.getDate("max").toLocalDate());
            treatment.setPlantName(results.getString("plant_name"));
            treatment.setPlantId(results.getInt("plant_id"));

            latestWaterings.add(treatment);
        }

        return latestWaterings;
    }
}
