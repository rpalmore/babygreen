package com.techelevator.dao;

import com.techelevator.model.Plant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPlantDao implements PlantDao {

    private JdbcTemplate template;

    public JdbcPlantDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Plant> getAllPlants(int userId) {

        List<Plant> plantList = new ArrayList<>();

        String sql = "SELECT plant_id, user_id, plant_img, plant_name, indoor, info_url, plant_age " +
                "FROM plants WHERE user_id = ?";
        SqlRowSet results = template.queryForRowSet(sql, userId);

        while (results.next()) {
            Plant plant = new Plant();
            plant.setPlantId(results.getInt("plant_id"));
            plant.setUserId(userId);
            plant.setPlantImg(results.getString("plant_img"));
            plant.setPlantName(results.getString("plant_name"));
            plant.setIndoor(results.getBoolean("indoor"));
            plant.setInfo_url(results.getString("info_url"));
            plant.setPlant_age(results.getInt("plant_age"));

            plantList.add(plant);
        }

        return plantList;
    }


    @Override
    public Plant getSinglePlant(int plantId) {

        String sql = "SELECT plant_id, user_id, plant_img, plant_name, indoor, info_url, plant_age " +
                "FROM plants WHERE plant_id = ?";
        SqlRowSet result = template.queryForRowSet(sql, plantId);

        Plant selectedPlant = null;
        if (result.next()) {
            selectedPlant = new Plant();
            selectedPlant.setPlantId(plantId);
            selectedPlant.setUserId(result.getInt("user_id"));
            selectedPlant.setPlantImg(result.getString("plant_img"));
            selectedPlant.setPlantName(result.getString("plant_name"));
            selectedPlant.setIndoor(result.getBoolean("indoor"));
            selectedPlant.setInfo_url(result.getString("info_url"));
            selectedPlant.setPlant_age(result.getInt("plant_age"));
        }

        return selectedPlant;
    }

    @Override
    public Plant createPlant(Plant newPlant) {

        String sql = "INSERT INTO plants(user_id, plant_img, plant_name, indoor, info_url, plant_age) " +
                "VALUES(?, ?, ?, ?, ?, ?) RETURNING plant_id";
        int plantId = template.queryForObject(sql, Integer.class, newPlant.getUserId(), newPlant.getPlantImg(),
                newPlant.getPlantName(), newPlant.getIndoor(), newPlant.getInfo_url(), newPlant.getPlant_age());

        newPlant.setPlantId(plantId);
        return newPlant;
    }

    @Override
    public void updatePlant(Plant updatedPlant) {

        String sql = "UPDATE plants " +
                "SET plant_img = ?, plant_name = ?, indoor = ?, info_url = ?, plant_age = ? " +
                "WHERE plant_id = ?";
        template.update(sql, updatedPlant.getPlantImg(), updatedPlant.getPlantName(),
                updatedPlant.getIndoor(), updatedPlant.getInfo_url(), updatedPlant.getPlant_age(), updatedPlant.getPlantId());
    }

    @Override
    public void deletePlant(int plantId) {

        String sql = "DELETE FROM plants WHERE plant_id = ?";
        template.update(sql, plantId);
    }
}
