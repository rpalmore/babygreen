package com.techelevator.dao;

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

        String sql = "SELECT care_date, care_type, plant_name, treatments.care_id " +
                "FROM treatments " +
                "JOIN plants_treatments ON plants_treatments.care_id = treatments.care_id " +
                "JOIN plants on plants.plant_id = plants_treatments.plant_id " +
                "WHERE user_id = ? " +
                "ORDER BY care_date DESC";
        SqlRowSet results = template.queryForRowSet(sql, userId);

        while(results.next()) {
            TreatmentDetails details = new TreatmentDetails();
            details.setCareDate(results.getDate("care_date").toLocalDate());
            details.setCareType(results.getString("care_type"));
            details.setPlantName(results.getString("plant_name"));
            details.setCareId(results.getInt("care_id"));

            treatmentDetails.add(details);
        }

        return treatmentDetails;
    }
}
