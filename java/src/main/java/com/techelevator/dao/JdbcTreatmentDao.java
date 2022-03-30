package com.techelevator.dao;

import com.techelevator.model.Treatment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JdbcTreatmentDao implements TreatmentDao {

    private JdbcTemplate template;

    public JdbcTreatmentDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

//    @Override
//    public List<Treatment> getAllCareEvents(int userId) {
//        return null;
//    }

//    @Override
//    public List<Treatment> getSinglePlantEvents(int plantId) {
//        return null;
//    }

    @Override
    public Treatment createCareEvent(Treatment careEvent) {

        String sql = "INSERT INTO treatments(care_date, care_type) " +
                "VALUES(?, ?) RETURNING care_id";

        int careId = template.queryForObject(sql, Integer.class, careEvent.getCareDate(),
                careEvent.getCareType());

        System.out.println(careId);

        careEvent.setCareId(careId);


        return careEvent;
    }
}
