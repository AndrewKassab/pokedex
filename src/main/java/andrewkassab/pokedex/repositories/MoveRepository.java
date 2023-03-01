package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.entitites.Pokemon;
import org.springframework.data.repository.CrudRepository;

public interface MoveRepository extends CrudRepository<Move, Integer> {
}
