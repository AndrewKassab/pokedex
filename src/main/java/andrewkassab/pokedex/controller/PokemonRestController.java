package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.controller.exceptions.NotFoundException;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import andrewkassab.pokedex.services.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PokemonRestController {

    public static final String POKEMON_API_PATH = "/api/pokemon";
    public static final String POKEMON_API_PATH_ID = POKEMON_API_PATH + "/{pokemonId}";

    private final PokemonService pokemonService;

    @PutMapping(POKEMON_API_PATH_ID)
    public ResponseEntity updatePokemonById(@PathVariable("pokemonId") Integer pokemonId, @RequestBody Pokemon pokemon) {
        if (pokemonService.updatePokemonById(pokemonId, pokemon).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(POKEMON_API_PATH_ID)
    public ResponseEntity deletePokemonById(@PathVariable("pokemonId") Integer pokemonId) {

        if (!pokemonService.deletePokemonById(pokemonId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(POKEMON_API_PATH)
    public ResponseEntity createPokemon(@Validated @RequestBody Pokemon pokemon) {

        if (pokemonService.getPokemonById(pokemon.getId()).isPresent()) {
            return ResponseEntity.badRequest().body("Pokemon with ID already exists");
        }

        var savedPokemon = pokemonService.saveNewPokemon(pokemon);

        var headers = new HttpHeaders();
        headers.add("Location", POKEMON_API_PATH + "/" + savedPokemon.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(POKEMON_API_PATH)
    public Page<Pokemon> getAllPokemons(@RequestParam(required = false) Type type,
                                        @RequestParam(required = false) Integer pageNumber,
                                        @RequestParam(required = false) Integer pageSize) {
        return pokemonService.getAllPokemon(type, pageNumber, pageSize);
    }

    @GetMapping(POKEMON_API_PATH_ID)
    public Pokemon getPokemonById(@PathVariable("pokemonId") Integer pokemonId) {
        return pokemonService.getPokemonById(pokemonId).orElseThrow(NotFoundException::new);
    }

}
