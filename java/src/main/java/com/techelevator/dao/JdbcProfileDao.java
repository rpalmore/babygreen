package com.techelevator.dao;

import com.techelevator.model.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcProfileDao implements ProfileDao {

    private JdbcTemplate template;

    public JdbcProfileDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public Profile getProfile(String username) {
        String sql = "SELECT profile_id, profiles.user_id, display_name, profile_img, " +
                "fave_plant, skill_level " +
                "FROM profiles " +
                "JOIN users ON users.user_id = profiles.user_id " +
                "WHERE username = ?";
        SqlRowSet result = template.queryForRowSet(sql, username);
        Profile profile = null;
        if (result.next()) {
            profile = new Profile();
            profile.setProfileId(result.getInt("profile_id"));
            profile.setUserId(result.getInt("user_id"));
            profile.setDisplayName(result.getString("display_name"));
            profile.setProfileImg(result.getString("profile_img"));
            profile.setFavePlant(result.getString("fave_plant"));
            profile.setSkillLevel(result.getString("skill_level"));
        }
        return profile;
    }

    @Override
    public Profile createProfile(Profile newProfile) {
        String sql = "INSERT INTO profiles(user_id, display_name, profile_img, fave_plant, skill_level) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING profile_id";
        int profileId = template.queryForObject(sql, Integer.class, newProfile.getUserId(), newProfile.getDisplayName(),
                newProfile.getProfileImg(), newProfile.getFavePlant(), newProfile.getSkillLevel());
        newProfile.setProfileId(profileId);
        return newProfile;
    }

    @Override
    public void deleteProfile(int userId) {
        String sql = "DELETE FROM profiles WHERE user_id = ?";
        template.update(sql, userId);
    }

    @Override
    public void editProfile(Profile updatedProfile) {
        String sql = "UPDATE profiles " +
                "SET display_name = ?, profile_img = ?, fave_plant = ?, skill_level = ? " +
                "WHERE user_id = ?";
        template.update(sql, updatedProfile.getDisplayName(), updatedProfile.getProfileImg(), updatedProfile.getFavePlant(),
                updatedProfile.getSkillLevel(), updatedProfile.getUserId());
    }
}
