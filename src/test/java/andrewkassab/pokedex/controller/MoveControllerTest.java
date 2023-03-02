package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.services.PokemonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PokemonController.class)
class MoveControllerTest extends PokedexTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PokemonService pokemonService;

    @Captor
    ArgumentCaptor<Integer> idArgumentCaptor;

    @Captor
    ArgumentCaptor<Pokemon> pokemonArgumentCaptor;

    List<Pokemon> pokemonList = getThreeStarterPokemon();

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testDeletePokemon() throws Exception {
        var testPokemon = pokemonList.get(0);
        given(pokemonService.deletePokemonById(any())).willReturn(true);

        mockMvc.perform(delete(PokemonController.POKEMON_PATH_ID, testPokemon.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(pokemonService).deletePokemonById(idArgumentCaptor.capture());

        assertThat(testPokemon.getId()).isEqualTo(idArgumentCaptor.getValue());
    }

    @Test
    void testUpdatePokemon() throws Exception {
        var testPokemon = pokemonList.get(0);
        testPokemon.setName("New Name");
        given(pokemonService.updatePokemonById(any(), any())).willReturn(Optional.of(testPokemon));

        mockMvc.perform(put(PokemonController.POKEMON_PATH_ID, testPokemon.getId())
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPokemon)))
                .andExpect(status().isNoContent());

        verify(pokemonService).updatePokemonById(idArgumentCaptor.capture(), pokemonArgumentCaptor.capture());

        assertThat(testPokemon.getId()).isEqualTo(idArgumentCaptor.getValue());
        assertThat(testPokemon.getName()).isEqualTo(pokemonArgumentCaptor.getValue().getName());
    }

    @Test
    void testCreatePokemon() throws Exception {
        var testPokemon = pokemonList.get(0);
        given(pokemonService.saveNewPokemon(any(Pokemon.class))).willReturn(pokemonList.get(0));

        mockMvc.perform(post(PokemonController.POKEMON_PATH)
                .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testPokemon)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name", is(testPokemon.getName())));
    }

    @Test
    void testCreatePokemonNullName() throws Exception {
        var testPokemon = pokemonList.get(0);
        testPokemon.setName(null);
        testPokemon.setId(2);
        given(pokemonService.saveNewPokemon(any(Pokemon.class))).willReturn(pokemonList.get(0));

        mockMvc.perform(post(PokemonController.POKEMON_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(objectMapper.writeValueAsString(testPokemon)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllPokemon() throws Exception {
        given(pokemonService.getAllPokemon()).willReturn(pokemonList);

        mockMvc.perform(get(PokemonController.POKEMON_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testGetPokemonById() throws Exception {
        var testPokemon = pokemonList.get(0);
        given(pokemonService.getPokemonById(testPokemon.getId())).willReturn(Optional.of(testPokemon));

        mockMvc.perform(get(PokemonController.POKEMON_PATH_ID, testPokemon.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testPokemon.getId())))
                .andExpect(jsonPath("$.name", is(testPokemon.getName())));
    }

    @Test
    void testGetPokemonByIdNotFound() throws Exception {
        given(pokemonService.getPokemonById(any(Integer.class))).willReturn(Optional.empty());

        mockMvc.perform(get(PokemonController.POKEMON_PATH_ID, 1))
                .andExpect(status().isNotFound());
    }


}