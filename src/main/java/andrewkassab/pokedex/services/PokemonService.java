package andrewkassab.pokedex.services;

import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface PokemonService {

    Page<Pokemon> getAllPokemon(Type type, Integer pageNumber, Integer pageSize);

    Optional<Pokemon> getPokemonById(Integer id);

    Pokemon saveNewPokemon(Pokemon pokemon);

    Optional<Pokemon> updatePokemonById(Integer id, Pokemon pokemon);

    Boolean deletePokemonById(Integer id);

}
