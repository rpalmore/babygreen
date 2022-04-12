package com.techelevator.dao;

import com.techelevator.model.Treatment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTreatmentDao implements TreatmentDao {

    private JdbcTemplate template;

    public JdbcTreatmentDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Treatment> getSinglePlantEvents(int plantId) {

        List<Treatment> singlePlantTreatments = new ArrayList<>();

        String sql = "SELECT care_date, care_type, treatments.care_id " +
                "FROM treatments " +
                "JOIN plants_treatments ON plants_treatments.care_id = treatments.care_id " +
                "JOIN plants ON plants.plant_id = plants_treatments.plant_id " +
                "WHERE plants.plant_id = ?" +
                "ORDER BY care_date DESC";

        SqlRowSet results = template.queryForRowSet(sql, plantId);

        while(results.next()) {
            Treatment treatments = new Treatment();
            treatments.setCareDate(results.getDate("care_date").toLocalDate());
            treatments.setCareType(results.getString("care_type"));
            treatments.setCareId(results.getInt("care_id"));

            singlePlantTreatments.add(treatments);

        }

        return singlePlantTreatments;
    }


    @Override
    public Treatment createCareEvent(Treatment careEvent) {

        String sql = "INSERT INTO treatments(care_date, care_type) " +
                "VALUES(?, ?) RETURNING care_id";

        int careId = template.queryForObject(sql, Integer.class, careEvent.getCareDate(),
                careEvent.getCareType());

        careEvent.setCareId(careId);

        int[] plantId = new int[careEvent.getPlantId().length];

        for (int i = 0; i < plantId.length; i++) {
            String sql2 = "INSERT INTO plants_treatments(plant_id, care_id) " +
                    "VALUES(?, ?)";

            template.update(sql2, careEvent.getPlantId()[i], careId);
        }

        return careEvent;
    }

    @Override
    public Treatment getLatestTreatment(int userId) {

        // If I want to count the number of plants that were treated, I can pass that
        // int to a second Sql statement (after returning it from the statement below.
        // BUT this requires a new data model and dao, etc.

        // This would be displayed on profile page: I recently watered 6 plants on [date].

        String sql = "SELECT care_type, care_date " +
                "FROM treatments " +
                "JOIN plants_treatments ON plants_treatments.care_id = treatments.care_id " +
                "JOIN plants on plants.plant_id = plants_treatments.plant_id " +
                "WHERE user_id = ? " +
                "ORDER BY care_date DESC " +
                "LIMIT 1";
        SqlRowSet results = template.queryForRowSet(sql, userId);

        Treatment treatment = null;
        if (results.next()) {
            treatment = new Treatment();
            treatment.setCareType(results.getString("care_type"));
            treatment.setCareDate(results.getDate("care_date").toLocalDate());
        }
        return treatment;
    }
}
