package andrewkassab.pokedex.integration;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.controller.PokemonController;
import andrewkassab.pokedex.controller.exceptions.NotFoundException;
import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import andrewkassab.pokedex.repositories.PokemonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PokemonIntegrationTest extends PokedexTest {

    @Autowired
    PokemonController pokemonController;

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        var pokemon = getThreeStarterPokemon();
        pokemonRepository.saveAll(pokemon);
    }

    @Test
    void testDeletePokemonDoesntExist() {
        assertThrows(NotFoundException.class, () -> {
            pokemonController.deletePokemonById(20);
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeletePokemon() {
        var pokemon = pokemonRepository.findAll().get(0);

        var response = pokemonController.deletePokemonById(pokemon.getId());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertTrue(pokemonRepository.findById(pokemon.getId()).isEmpty());
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            pokemonController.updatePokemonById(20, Pokemon.builder().build());
        });
    }

    @Transactional
    @Rollback
    @Test
    void testUpdatePokemon() {
        var pokemon = pokemonRepository.findAll().get(0);
        var pokemonId = pokemon.getId();
        pokemon.setId(null);

        var newName = "New Pokemon";
        pokemon.setName(newName);

        var response = pokemonController.updatePokemonById(pokemonId, pokemon);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        var returnedPokemon = pokemonRepository.findById(pokemonId).orElse(null);

        assertEquals(newName, returnedPokemon.getName());
    }

    @Transactional
    @Rollback
    @Test
    void testCreatePokemon() {
        Pokemon newPokemon = Pokemon.builder()
                .id(2)
                .name("Ivysaur")
                .type(Type.GRASS)
                .build();

        var response = pokemonController.createPokemon(newPokemon);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());

        String[] locationId = response.getHeaders().getLocation().getPath().split("/");
        var savedId = Integer.parseInt(locationId[3]);

        Pokemon returnedPokemon = pokemonRepository.findById(savedId).orElse(null);

        assertNotNull(returnedPokemon);
    }


    @Test
    void testGetPokemonById() {
        var pokemon = pokemonRepository.findAll().get(0);
        var pokemonReturned = pokemonController.getPokemonById(pokemon.getId());

        assertNotNull(pokemonReturned);
    }

    @Transactional
    @Rollback
    @Test
    void testGetPokemonEmpty() {
        pokemonRepository.deleteAll();
        var pokemonList = pokemonController.getAllPokemons();

        assertEquals(pokemonList.size(), 0);
    }

    @Test
    void testGetAllPokemon() {
        var pokemonList = pokemonController.getAllPokemons();

        assertEquals(pokemonList.size(), 3);
    }


}