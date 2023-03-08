package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PokemonRepositoryTest extends PokedexTest {

    @Autowired
    PokemonRepository pokemonRepository;

    List<Pokemon> pokemonList = getThreeStarterPokemon();

    List<Move> moveList = getFiveMoves();

    @Test
    void testSavePokemon() {
        Pokemon pokemonToSave = pokemonList.get(0);
        Pokemon savedPokemon = pokemonRepository.save(pokemonToSave);
        pokemonToSave.getMoves().add(moveList.get(0));
        pokemonToSave.getMoves().add(moveList.get(1));
        pokemonToSave.getMoves().add(moveList.get(2));
        pokemonToSave.getMoves().add(moveList.get(3));

        pokemonRepository.flush();
        assertThat(savedPokemon).isNotNull();
    }

    @Test
    void testSavePokemonBlankName() {
        assertThrows(ConstraintViolationException.class, () -> {
            Pokemon savedPokemon = pokemonRepository.save(Pokemon.builder()
                    .id(1)
                    .name("")
                    .type(Type.GRASS)
                    .build());
            pokemonRepository.flush();
        });
    }

    @Test
    void testSavePokemonMissingType() {
        assertThrows(ConstraintViolationException.class, () -> {
            Pokemon savedPokemon = pokemonRepository.save(Pokemon.builder()
                    .id(1)
                    .name("Bulbasaur")
                    .build());
            pokemonRepository.flush();
        });
    }

    @Test
    void testSavePokemonTooManyMoves() {
        var pokemonToSave = pokemonList.get(0);
        pokemonToSave.setMoves(new HashSet<>(moveList));
        assertThrows(ConstraintViolationException.class, () -> {
            Pokemon savedPokemon = pokemonRepository.save(pokemonToSave);
            pokemonRepository.flush();
        });
    }

}