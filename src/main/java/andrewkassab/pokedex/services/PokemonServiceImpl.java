package andrewkassab.pokedex.services;

import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.repositories.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Primary
@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;

    @Override
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }

    @Override
    public Optional<Pokemon> getPokemonById(Integer id) {
        return pokemonRepository.findById(id);
    }

    @Override
    public Pokemon saveNewPokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    @Override
    public Optional<Pokemon> updatePokemonById(Integer id, Pokemon pokemon) {
        var atomicReference = new AtomicReference<Optional<Pokemon>>();

        pokemonRepository.findById(id).ifPresentOrElse(foundMove -> {
            foundMove.setType(pokemon.getType());
            foundMove.setName(pokemon.getName());
            foundMove.setMoves(pokemon.getMoves());
            atomicReference.set(Optional.of(pokemonRepository.save(foundMove)));
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
