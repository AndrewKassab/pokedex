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

    public static final String MOVE_PATH = "/api/move";
    public static final String MOVE_PATH_ID = MOVE_PATH + "/{moveId}";

    private final PokemonService pokemonService;

    @PutMapping(MOVE_PATH_ID)
    public ResponseEntity updatePokemonById(@PathVariable("moveId") Integer moveId, @RequestBody Pokemon move) {
        return null;
    }

    @DeleteMapping(MOVE_PATH_ID)
    public ResponseEntity deletePokemonById(@PathVariable("moveId") Integer moveId) {
        return null;
    }

    @PostMapping(MOVE_PATH)
    public ResponseEntity createPokemon(@Validated @RequestBody Pokemon move) {
        return null;
    }

    @GetMapping(MOVE_PATH)
    public ResponseEntity getAllPokemons() {
        return null;
    }

    @GetMapping(MOVE_PATH_ID)
    public ResponseEntity getPokemonById(@PathVariable("moveId") Integer moveId) {
        return null;
    }

}
