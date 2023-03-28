package andrewkassab.pokedex.integration;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.controller.PokemonController;
import andrewkassab.pokedex.controller.exceptions.NotFoundException;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.repositories.PokemonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        pokemonRepository.save(getThreeStarterPokemon().get(0));
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
        var pokemon = pokemonRepository.save(getThreeStarterPokemon().get(0));

        var newName = "New Pokemon";
        pokemon.setName(newName);

        var response = pokemonController.updatePokemonById(pokemon.getId(), pokemon);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        var returnedPokemon = pokemonRepository.findById(pokemon.getId()).orElse(null);

        assertEquals(newName, returnedPokemon.getName());
    }

    @Transactional
    @Rollback
    @Test
    void testCreatePokemon() {
        Pokemon newPokemon = getThreeStarterPokemon().get(0);

        var response = pokemonController.createPokemon(newPokemon);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());

        String[] locationId = response.getHeaders().getLocation().getPath().split("/");
        var savedId = Integer.parseInt(locationId[3]);

        Pokemon returnedPokemon = pokemonRepository.findById(savedId).orElse(null);

        assertNotNull(returnedPokemon);
    }

    @Transactional
    @Rollback
    @Test
    void testGetPokemonById() {
        var savedPokemon = pokemonRepository.save(getThreeStarterPokemon().get(0));
        var pokemonReturned = pokemonController.getPokemonById(savedPokemon.getId());

        assertNotNull(pokemonReturned);
    }

    @Transactional
    @Rollback
    @Test
    void testGetPokemonEmpty() {
        var pokemonList = pokemonController.getAllPokemons();

        assertEquals(pokemonList.size(), 0);
    }

    @Transactional
    @Rollback
    @Test
    void testGetAllPokemon() {
        pokemonRepository.saveAll(getThreeStarterPokemon());
        var pokemonList = pokemonController.getAllPokemons();

        assertEquals(pokemonList.size(), 3);
    }


}