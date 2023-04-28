package andrewkassab.pokedex.services;

import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import andrewkassab.pokedex.repositories.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static andrewkassab.pokedex.constants.PageValues.DEFAULT_PAGE;
import static andrewkassab.pokedex.constants.PageValues.DEFAULT_PAGE_SIZE;

@Primary
@Service
@RequiredArgsConstructor
public class PokemonServiceImpl extends PokedexService implements PokemonService {

    private final PokemonRepository pokemonRepository;

    public Page<Pokemon> getAllPokemon(Type type, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Pokemon> pokemonPage;

        if (type != null) {
            return getPokemonByType(type, pageRequest);
        }

        return pokemonRepository.findAll(pageRequest);
    }

    private Page<Pokemon> getPokemonByType(Type type, Pageable pageRequest) {
        return pokemonRepository.findAllByType(type, pageRequest);
    }

    @Override
    public Optional<Pokemon> getPokemonById(Integer id) {
        return pokemonRepository.findById(id);
    }

    @Override
    public Pokemon saveNewPokemon(Pokemon pokemon) {
        pokemon.setCreatedDate(LocalDateTime.now());
        pokemon.setUpdatedDate(LocalDateTime.now());
        return pokemonRepository.save(pokemon);
    }

    @Override
    public Optional<Pokemon> updatePokemonById(Integer id, Pokemon pokemon) {
        var atomicReference = new AtomicReference<Optional<Pokemon>>();

        pokemonRepository.findById(id).ifPresentOrElse(foundPokemon -> {
            foundPokemon.setType(pokemon.getType());
            foundPokemon.setName(pokemon.getName());
            foundPokemon.setMoves(pokemon.getMoves());
            foundPokemon.setUpdatedDate(LocalDateTime.now());
            atomicReference.set(Optional.of(pokemonRepository.save(foundPokemon)));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deletePokemonById(Integer id) {
        if (pokemonRepository.existsById(id)) {
            pokemonRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
