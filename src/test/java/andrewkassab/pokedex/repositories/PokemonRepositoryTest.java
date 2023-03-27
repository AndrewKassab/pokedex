package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PokemonRepositoryTest extends PokedexTest {

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    MoveRepository moveRepository;

    List<Pokemon> pokemonList = getThreeStarterPokemon();

    List<Move> moveList = getFiveMoves();

    @Test
    void testSavePokemon() {
        moveRepository.saveAll(moveList);
        Pokemon pokemonToSave = pokemonList.get(0);
        pokemonToSave.getMoves().add(moveList.get(0));
        pokemonToSave.getMoves().add(moveList.get(1));
        pokemonToSave.getMoves().add(moveList.get(2));
        pokemonToSave.getMoves().add(moveList.get(3));

        Pokemon savedPokemon = pokemonRepository.save(pokemonToSave);

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
        moveRepository.saveAll(getFiveMoves());
        var pokemonToSave = pokemonList.get(0);
        pokemonToSave.setMoves(new HashSet<>(moveList));
        assertThrows(ConstraintViolationException.class, () -> {
            Pokemon savedPokemon = pokemonRepository.save(pokemonToSave);
            pokemonRepository.flush();
        });
    }

    @Test
    void testSavePokemonMoveDoesntExist() {
        var pokemonToSave = pokemonList.get(0);
        pokemonToSave.getMoves().add(moveList.get(0));
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            Pokemon savedPokemon = pokemonRepository.save(pokemonToSave);
            pokemonRepository.flush();
        });
    }

}