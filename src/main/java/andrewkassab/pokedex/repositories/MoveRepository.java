package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.entitites.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MoveRepository extends JpaRepository<Move, Integer> {

    Optional<Move> findByName(String name);

}
