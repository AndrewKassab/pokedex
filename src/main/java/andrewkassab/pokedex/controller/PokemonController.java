package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.controller.exceptions.NotFoundException;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import andrewkassab.pokedex.services.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
        if (pokemonService.updatePokemonById(pokemonId, pokemon).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(POKEMON_PATH_ID)
    public ResponseEntity deletePokemonById(@PathVariable("pokemonId") Integer pokemonId) {

        if (!pokemonService.deletePokemonById(pokemonId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(POKEMON_PATH)
    public ResponseEntity createPokemon(@Validated @RequestBody Pokemon pokemon) {

        var savedPokemon = pokemonService.saveNewPokemon(pokemon);

        var headers = new HttpHeaders();
        headers.add("Location", POKEMON_PATH + "/" + savedPokemon.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(POKEMON_PATH)
    public List<Pokemon> getAllPokemons(@RequestParam(required = false) Type type) {
        return pokemonService.getAllPokemon(type);
    }

    @GetMapping(POKEMON_PATH_ID)
    public Pokemon getPokemonById(@PathVariable("pokemonId") Integer pokemonId) {
        return pokemonService.getPokemonById(pokemonId).orElseThrow(NotFoundException::new);
    }

}
