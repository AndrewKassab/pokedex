package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import andrewkassab.pokedex.services.PokemonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;

@WebMvcTest
class PokemonControllerTest extends PokedexTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PokemonService pokemonService;

    @Captor
    ArgumentCaptor<Integer> idArgumentCaptor;

    @Captor
    ArgumentCaptor<Pokemon> pokemonArgumentCaptor;

    List<Pokemon> pokemonList = getThreeStarterPokemon();

    @Test
    void testGetAllPokemon() throws Exception {
    }


}