package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.entitites.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
}
