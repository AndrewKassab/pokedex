package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.controller.exceptions.NotFoundException;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.services.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@Slf4j
@Controller
public class PokemonController {

    private static final String POKEMON_PATH = "/pokemon";
    private static final String POKEMON_PATH_ID = POKEMON_PATH + "/{pokemonId}";

    private final String imgSourceUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/";

    private final PokemonService pokemonService;

    @GetMapping(POKEMON_PATH_ID)
    public String viewPokemon(@PathVariable("pokemonId") Integer pokemonId, Model model) {
        Pokemon pokemon = pokemonService.getPokemonById(pokemonId).orElseThrow(NotFoundException::new);
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("imgSourceUrl", imgSourceUrl);
        return "pokemon-details";
    }

}
