package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MoveRepository extends JpaRepository<Move, Integer> {

    Optional<Move> findByName(String name);

    public Page<Move> findAllByType(@Param("type") Type type, Pageable pageable);

}
