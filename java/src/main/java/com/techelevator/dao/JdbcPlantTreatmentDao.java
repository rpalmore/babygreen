package com.techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Date;

@Component
public class JdbcPlantTreatmentDao implements PlantTreatmentDao {

    private JdbcTemplate template;

    public JdbcPlantTreatmentDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public void deletePlantTreatment(int plantId, int careId) {
        String sql = "DELETE FROM plants_treatments " +
                "WHERE plant_id = ? AND care_id = ?";
        template.update(sql, plantId, careId);

        String sql2 = "SELECT count(care_id) FROM plants_treatments " +
                "WHERE care_id = ?";

        int results = template.queryForObject(sql2, Integer.class, careId);

        if (results == 0) {

            String sql3 = "DELETE FROM treatments " +
                    "WHERE care_id = ?";

            template.update(sql3, careId);
        }

    }

    @Override
    public void deletePlantTreatmentsByDate(LocalDate careDate) {
        String sql = "DELETE FROM plants_treatments " +
                "WHERE care_id IN (SELECT care_id FROM treatments WHERE care_date = ?)";
        template.update(sql, careDate);

        String sql2 = "DELETE FROM treatments " +
                "WHERE care_date = ?";
        template.update(sql2, careDate);
    }

}
