package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.services.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PokemonController {

    public static final String POKEMON_PATH = "/api/pokemon";
    public static final String POKEMON_PATH_ID = POKEMON_PATH + "/{pokemonId}";

    private final PokemonService pokemonService;

    @PutMapping(POKEMON_PATH_ID)
    public ResponseEntity updatePokemonById(@PathVariable("moveId") Integer moveId, @RequestBody Pokemon move) {
        return null;
    }

    @DeleteMapping(POKEMON_PATH_ID)
    public ResponseEntity deletePokemonById(@PathVariable("moveId") Integer moveId) {
        return null;
    }

    @PostMapping(POKEMON_PATH)
    public ResponseEntity createPokemon(@Validated @RequestBody Pokemon move) {
        return null;
    }

    @GetMapping(POKEMON_PATH)
    public ResponseEntity getAllPokemons() {
        return null;
    }

    @GetMapping(POKEMON_PATH_ID)
    public ResponseEntity getPokemonById(@PathVariable("moveId") Integer moveId) {
        return null;
    }

}
