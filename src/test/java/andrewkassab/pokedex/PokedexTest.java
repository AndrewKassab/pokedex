package andrewkassab.pokedex;

import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;

import java.util.ArrayList;
import java.util.List;

public abstract class PokedexTest {

    protected List<Pokemon> getThreeStarterPokemon() {

        Pokemon bulbasaur = Pokemon.builder()
                .id(1)
                .name("Bulbasaur")
                .type(Type.GRASS)
                .build();

        Pokemon charmander = Pokemon.builder()
                .id(4)
                .name("Charmander")
                .type(Type.FIRE)
                .build();

        Pokemon squirtle = Pokemon.builder()
                .id(7)
                .name("Squirtle")
                .type(Type.WATER)
                .build();

        List<Pokemon> pokemonList = new ArrayList<>();
        pokemonList.add(bulbasaur);
        pokemonList.add(charmander);
        pokemonList.add(squirtle);

        return pokemonList;
    }

}
