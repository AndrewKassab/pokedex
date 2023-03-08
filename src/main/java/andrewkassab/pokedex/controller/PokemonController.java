package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.services.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PokemonController {

    public static final String POKEMON_PATH = "/api/pokemon";
    public static final String POKEMON_PATH_ID = POKEMON_PATH + "/{pokemonId}";

    private final PokemonService pokemonService;

    @PutMapping(POKEMON_PATH_ID)
    public ResponseEntity updatePokemonById(@PathVariable("pokemonId") Integer pokemonId, @RequestBody Pokemon pokemon) {
        return null;
    }

    @DeleteMapping(POKEMON_PATH_ID)
    public ResponseEntity deletePokemonById(@PathVariable("pokemonId") Integer pokemonId) {
        return null;
    }

    @PostMapping(POKEMON_PATH)
    public ResponseEntity createPokemon(@Validated @RequestBody Pokemon pokemon) {
        return null;
    }

    @GetMapping(POKEMON_PATH)
    public List<Pokemon> getAllPokemons() {
        return null;
    }

    @GetMapping(POKEMON_PATH_ID)
    public Pokemon getPokemonById(@PathVariable("pokemonId") Integer pokemonId) {
        return null;
    }

}
