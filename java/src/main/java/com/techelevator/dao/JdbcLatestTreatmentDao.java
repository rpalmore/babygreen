package com.techelevator.dao;

import com.techelevator.model.LatestTreatment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcLatestTreatmentDao implements LatestTreatmentDao {

    private JdbcTemplate template;

    public JdbcLatestTreatmentDao(DataSource dataSource) { template = new JdbcTemplate(dataSource); }

    @Override
    public LatestTreatment getLatestTreatment(int userId) {

        /**
         * This query is for display on user's profile page,
         * i.e., "I most recently watered 3 plants on April 22, 2022."
         */
        String sql = "SELECT care_type, care_date, treatments.care_id " +
                "FROM treatments " +
                "JOIN plants_treatments ON plants_treatments.care_id = treatments.care_id " +
                "JOIN plants on plants.plant_id = plants_treatments.plant_id " +
                "WHERE user_id = ? " +
                "ORDER BY care_date DESC, treatments.care_id DESC " +
                "LIMIT 1";

        SqlRowSet results = template.queryForRowSet(sql, userId);

        LatestTreatment latest = null;
        if (results.next()) {
            latest = new LatestTreatment();
            latest.setCareType(results.getString("care_type"));
            latest.setCareDate(results.getDate("care_date").toLocalDate());
            latest.setCareId(results.getInt("care_id"));
        }

        if (latest != null) {

            String sql2 = "SELECT count(care_id) FROM plants_treatments " +
                    "WHERE care_id = ?";

            int plants = template.queryForObject(sql2, Integer.class, latest.getCareId());

            latest.setNumPlants(plants);
        }

        return latest;
    }
}
