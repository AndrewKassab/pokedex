package andrewkassab.pokedex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Slf4j
@Controller
public class PokemonController {

    private static final String POKEMON_PATH = "/pokemon";

    private static final String POKEMON_PATH_ID = POKEMON_PATH + "/{pokemonId}";
}
