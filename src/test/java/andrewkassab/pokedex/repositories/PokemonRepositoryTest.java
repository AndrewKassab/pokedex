package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PokemonRepositoryTest {

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    MoveRepository moveRepository;

    @Test
    void testSavePokemon() {
        Pokemon pokemonToSave = Pokemon.builder().name("New Pokemon").type(Type.FIRE).build();
        pokemonToSave.getMoves().add(Move.builder().id(1).build());
        pokemonToSave.getMoves().add(Move.builder().id(2).build());

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
        var pokemonToSave = Pokemon.builder().name("New Pokemon").type(Type.FIRE).build();
        pokemonToSave.getMoves().add(Move.builder().id(1).build());
        pokemonToSave.getMoves().add(Move.builder().id(2).build());
        pokemonToSave.getMoves().add(Move.builder().id(3).build());
        pokemonToSave.getMoves().add(Move.builder().id(4).build());
        pokemonToSave.getMoves().add(Move.builder().id(5).build());
        assertThrows(ConstraintViolationException.class, () -> {
            Pokemon savedPokemon = pokemonRepository.save(pokemonToSave);
            pokemonRepository.flush();
        });
    }

    @Test
    void testSavePokemonMoveDoesntExist() {
        var pokemonToSave = Pokemon.builder().name("New Pokemon").type(Type.GRASS).build();
        pokemonToSave.getMoves().add(Move.builder().id(17).build());
        assertThrows(DataIntegrityViolationException.class, () -> {
            Pokemon savedPokemon = pokemonRepository.save(pokemonToSave);
            pokemonRepository.flush();
        });
    }

}