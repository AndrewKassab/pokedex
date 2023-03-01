package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.entitites.Pokemon;
import org.springframework.data.repository.CrudRepository;

public interface PokemonRepository extends CrudRepository<Pokemon, Integer> {
}
