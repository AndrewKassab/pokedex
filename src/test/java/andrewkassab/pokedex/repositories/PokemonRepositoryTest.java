package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.entitites.Pokemon;
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
                .build());

        assertThat(savedPokemon).isNotNull();
    }

}