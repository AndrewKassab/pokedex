drop table if exists pokemon;
drop table if exists move;
drop table if exists pokemon_type_weaknesses;

CREATE TABLE pokemon (
  id INT NOT NULL UNIQUE,
  name VARCHAR(50) NOT NULL,
  primary_type ENUM('NORMAL', 'FIRE', 'WATER', 'ELECTRIC', 'GRASS', 'ICE', 'FIGHTING', 'POISON', 'GROUND', 'FLYING', 'PSYCHIC', 'BUG', 'ROCK', 'GHOST', 'DRAGON', 'DARK', 'STEEL', 'FAIRY') NOT NULL,
  secondary_type ENUM('NORMAL', 'FIRE', 'WATER', 'ELECTRIC', 'GRASS', 'ICE', 'FIGHTING', 'POISON', 'GROUND', 'FLYING', 'PSYCHIC', 'BUG', 'ROCK', 'GHOST', 'DRAGON', 'DARK', 'STEEL', 'FAIRY'),
  type_weaknesses SET('NORMAL', 'FIRE', 'WATER', 'ELECTRIC', 'GRASS', 'ICE', 'FIGHTING', 'POISON', 'GROUND', 'FLYING', 'PSYCHIC', 'BUG', 'ROCK', 'GHOST', 'DRAGON', 'DARK', 'STEEL', 'FAIRY'),
  created_date DATETIME,
  updated_date DATETIME,
  PRIMARY KEY (id)
);

CREATE TABLE move (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL UNIQUE,
  type ENUM('NORMAL', 'FIRE', 'WATER', 'ELECTRIC', 'GRASS', 'ICE', 'FIGHTING', 'POISON', 'GROUND', 'FLYING', 'PSYCHIC', 'BUG', 'ROCK', 'GHOST', 'DRAGON', 'DARK', 'STEEL', 'FAIRY') NOT NULL,
  created_date DATETIME,
  updated_date DATETIME,
  PRIMARY KEY (id)
);

