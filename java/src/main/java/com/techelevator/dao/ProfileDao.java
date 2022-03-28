package com.techelevator.dao;

import com.techelevator.model.Profile;

public interface ProfileDao {

    Profile getProfile(String username);

    Profile createProfile(Profile newProfile);

    void deleteProfile(String username);

    void editProfile(Profile updatedProfile);

}
