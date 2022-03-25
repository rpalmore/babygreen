BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS plants;
DROP TABLE IF EXISTS plant_notes;
DROP TABLE IF EXISTS plant_waterings;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE profiles (
    profile_id serial,
    user_id int NOT NULL,
    profile_img varchar(500),
    fave_plant varchar(200),
    skill_level varchar(50),
    CONSTRAINT PK_profile PRIMARY KEY (profile_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE plants (
    plant_id serial,
    user_id int NOT NULL,
    plant_img varchar(500),
    plant_name varchar(200),
    indoor BOOLEAN NOT NULL,
    info_url varchar(500),
    plant_age int,
    CONSTRAINT PK_plant PRIMARY KEY (plant_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE plant_notes (
    note_id serial,
    plant_id int NOT NULL,
    notes varchar(5000),
    note_img varchar(500),
    created_on DATE DEFAULT CURRENT_DATE,
    CONSTRAINT PK_note PRIMARY KEY (note_id),
    CONSTRAINT FK_plant FOREIGN KEY (plant_id) REFERENCES plants(plant_id)
);

CREATE TABLE plant_waterings (
    watering_id serial,
    plant_id int NOT NULL,
    date_watered date NOT NULL,
    CONSTRAINT PK_watering_id PRIMARY KEY (watering_id),
    CONSTRAINT FK_plant FOREIGN KEY (plant_id) REFERENCES plants(plant_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');


COMMIT TRANSACTION;
