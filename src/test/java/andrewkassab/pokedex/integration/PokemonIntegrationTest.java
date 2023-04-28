package andrewkassab.pokedex.integration;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.controller.PokemonController;
import andrewkassab.pokedex.controller.exceptions.NotFoundException;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import andrewkassab.pokedex.repositories.PokemonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

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
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
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
        var response = pokemonController.deletePokemonById(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertTrue(pokemonRepository.findById(1).isEmpty());
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
        var pokemonReturned = pokemonController.getPokemonById(1);

        assertNotNull(pokemonReturned);
    }

    @Transactional
    @Rollback
    @Test
    void testGetPokemonByType() throws Exception {
        var typeToFilter = Type.WATER;

        var result = mockMvc.perform(get(PokemonController.POKEMON_PATH)
                .queryParam("type", typeToFilter.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(5)))
                .andReturn();

        var responseList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Pokemon>>() {});
        assertEquals(5, responseList.size());
        responseList.forEach(pokemon -> assertEquals(typeToFilter, pokemon.getType()));
    }

    @Transactional
    @Rollback
    @Test
    void testGetPokemonEmpty() {
        pokemonRepository.deleteAll();
        var pokemonList = pokemonController.getAllPokemons(null);

        assertEquals(pokemonList.size(), 0);
    }

    @Transactional
    @Rollback
    @Test
    void testGetAllPokemon() {
        var pokemonList = pokemonController.getAllPokemons(null);

        assertEquals(pokemonList.size(), 15);
    }


}