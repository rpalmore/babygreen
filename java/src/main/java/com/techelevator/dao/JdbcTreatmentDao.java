package com.techelevator.dao;

import com.techelevator.model.Treatment;
import org.springframework.jdbc.core.JdbcTemplate;
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

        String sql = "SELECT care_date, care_type, treatments.care_id, plants.plant_id " +
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
//            Testing plant_id in results to see if it improves plant_detail page load
            treatments.setPlantId(new int[]{results.getInt("plant_id")});

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

}
