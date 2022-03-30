BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS plants;
DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS treatments;
DROP TABLE IF EXISTS plants_treatments;
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
    CONSTRAINT FK_user FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE plants (
    plant_id serial,
    user_id int NOT NULL,
    plant_img varchar(500),
    plant_name varchar(200),
    indoor BOOLEAN NOT NULL,
    info_url varchar(500),
    plant_age DATE DEFAULT CURRENT_DATE NOT NULL,
    CONSTRAINT PK_plant PRIMARY KEY (plant_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE notes (
    note_id serial,
    plant_id int NOT NULL,
    note TEXT,
    note_img VARCHAR(500),
    created_on DATE DEFAULT CURRENT_DATE NOT NULL,
    CONSTRAINT PK_note PRIMARY KEY (note_id),
    CONSTRAINT FK_plant FOREIGN KEY (plant_id)
    REFERENCES plants(plant_id)
    ON DELETE CASCADE
);

CREATE TABLE treatments (
    care_id serial,
    care_date date NOT NULL,
    care_type varchar(300),
    CONSTRAINT PK_care_id PRIMARY KEY (care_id)
);

CREATE TABLE plants_treatments (
    plant_id int NOT NULL,
    care_id int NOT NULL,
    CONSTRAINT PK_plants_treatments PRIMARY KEY(plant_id, care_id),
    CONSTRAINT FK_plants_treatments_plant FOREIGN KEY(plant_id)
    REFERENCES plants(plant_id),
    CONSTRAINT FK_plants_treatments_care FOREIGN KEY(care_id)
    REFERENCES treatments(care_id)
);


INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');


COMMIT TRANSACTION;
