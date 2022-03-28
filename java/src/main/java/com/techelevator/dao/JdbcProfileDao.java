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
        String sql = "SELECT profile_id, username, profile_img, fave_plant, skill_level " +
                "FROM profiles " +
                "JOIN users ON users.user_id = profiles.user_id " +
                "WHERE username = ?";
        SqlRowSet result = template.queryForRowSet(sql, username);
        Profile profile = null;
        if (result.next()) {
            profile = new Profile();
            profile.setProfileId(result.getInt("profile_id"));
            profile.setUsername(username);
            profile.setProfileImg(result.getString("profile_img"));
            profile.setFavePlant(result.getString("fave_plant"));
            profile.setSkillLevel(result.getString("skill_level"));
        }
        return profile;
    }

    @Override
    public Profile createProfile(Profile newProfile) {
        String sql = "INSERT INTO profiles(user_id, profile_img, fave_plant, skill_level) " +
                "VALUES ((SELECT user_id FROM users WHERE username = ?), ?, ?, ?) RETURNING profile_id";
        int profileId = template.queryForObject(sql, Integer.class, newProfile.getUsername(), newProfile.getProfileImg(),
                newProfile.getFavePlant(), newProfile.getSkillLevel());
        newProfile.setProfileId(profileId);
        return newProfile;
    }

    @Override
    public void deleteProfile(String username) {
        String sql = "DELETE FROM profiles WHERE user_id IN (SELECT user_id FROM users WHERE username = ?)";
        template.update(sql, username);
    }

    @Override
    public void editProfile(Profile updatedProfile) {
        String sql = "UPDATE profiles " +
                "SET profile_img = ?, fave_plant = ?, skill_level = ? " +
                "WHERE user_id IN (SELECT user_id FROM users WHERE username = ?)";
        template.update(sql, updatedProfile.getProfileImg(), updatedProfile.getFavePlant(),
                updatedProfile.getSkillLevel(), updatedProfile.getUsername());
    }
}
