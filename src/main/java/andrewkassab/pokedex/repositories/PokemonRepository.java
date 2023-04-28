package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    public List<Pokemon> findByType(@Param("type") Type type);

}
