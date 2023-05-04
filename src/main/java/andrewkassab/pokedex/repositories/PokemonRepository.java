package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    @Query("SELECT p FROM Pokemon p WHERE p.primaryType = :type OR p.secondaryType = :type")
    Page<Pokemon> findAllByType(@Param("type") Type type, Pageable pageable);

}
