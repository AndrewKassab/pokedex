package andrewkassab.pokedex.services;

import andrewkassab.pokedex.entitites.Pokemon;

import java.util.List;
import java.util.Optional;

public class PokemonServiceImpl implements PokemonService {
    @Override
    public List<Pokemon> getAllPokemon() {
        return null;
    }

    @Override
    public Optional<Pokemon> getPokemonById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Pokemon saveNewPokemon(Pokemon pokemon) {
        return null;
    }

    @Override
    public Optional<Pokemon> updatePokemonById(Integer id, Pokemon pokemon) {
        return Optional.empty();
    }

    @Override
    public Boolean deletePokemonById(Integer id) {
        return null;
    }
}
