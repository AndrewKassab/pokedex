INSERT INTO move (name, type) VALUES
  ('Flamethrower', 'FIRE'),
  ('Water Gun', 'WATER'),
  ('Razor Leaf', 'GRASS'),
  ('Move 4', 'FIRE'),
  ('Move 5', 'WATER'),
  ('Move 6', 'GRASS'),
  ('Move 7', 'FIRE'),
  ('Move 8', 'WATER'),
  ('Move 9', 'GRASS'),
  ('Move 10', 'FIRE'),
  ('Move 11', 'WATER'),
  ('Move 12', 'GRASS'),
  ('Move 13', 'FIRE'),
  ('Move 14', 'WATER'),
  ('Move 15', 'GRASS');

INSERT INTO pokemon (name, type) VALUES
  ('Charmander', 'FIRE'),
  ('Squirtle', 'WATER'),
  ('Bulbasaur', 'GRASS'),
  ('Pokemon 4', 'FIRE'),
  ('Pokemon 5', 'WATER'),
  ('Pokemon 6', 'GRASS'),
  ('Pokemon 7', 'FIRE'),
  ('Pokemon 8', 'WATER'),
  ('Pokemon 9', 'GRASS'),
  ('Pokemon 10', 'FIRE'),
  ('Pokemon 11', 'WATER'),
  ('Pokemon 12', 'GRASS'),
  ('Pokemon 13', 'FIRE'),
  ('Pokemon 14', 'WATER'),
  ('Pokemon 15', 'GRASS');

INSERT INTO pokemon_moves (pokemon_id, moves_id) VALUES
  ((SELECT id FROM pokemon WHERE name = 'Charmander'), (SELECT id FROM move WHERE name = 'Flamethrower')),
  ((SELECT id FROM pokemon WHERE name = 'Squirtle'), (SELECT id FROM move WHERE name = 'Water Gun')),
  ((SELECT id FROM pokemon WHERE name = 'Bulbasaur'), (SELECT id FROM move WHERE name = 'Razor Leaf'));