package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PokemonRepositoryTest {

    @Autowired
    PokemonRepository pokemonRepository;

    @Test
    void testSavePokemon() {
        Pokemon savedPokemon = pokemonRepository.save(Pokemon.builder()
                        .id(1)
                        .name("Bulbasaur")
                        .type(Type.GRASS)
                .build());

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

}