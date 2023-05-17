drop table if exists pokemon;
drop table if exists move;
drop table if exists pokemon_type_weaknesses;

CREATE TABLE pokemon (
  id INT NOT NULL UNIQUE,
  name VARCHAR(50) NOT NULL,
  primary_type VARCHAR(12) NOT NULL,
  secondary_type VARCHAR(12),
  created_date DATETIME,
  updated_date DATETIME,
  PRIMARY KEY (id)
);

CREATE TABLE move (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL UNIQUE,
  type VARCHAR(12) NOT NULL,
  created_date DATETIME,
  updated_date DATETIME,
  PRIMARY KEY (id)
);

CREATE TABLE pokemon_type_weaknesses (
  pokemon_id INT NOT NULL,
  type VARCHAR(12) NOT NULL,
  PRIMARY KEY (pokemon_id, type),
  FOREIGN KEY (pokemon_id) REFERENCES pokemon (id)
);

