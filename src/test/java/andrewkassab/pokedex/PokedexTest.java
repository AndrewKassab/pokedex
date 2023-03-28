package andrewkassab.pokedex;

import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class PokedexTest {

    protected List<Pokemon> getThreeStarterPokemon() {

        Pokemon bulbasaur = Pokemon.builder()
                .name("Bulbasaur")
                .type(Type.GRASS)
                .build();

        Pokemon charmander = Pokemon.builder()
                .name("Charmander")
                .type(Type.FIRE)
                .build();

        Pokemon squirtle = Pokemon.builder()
                .name("Squirtle")
                .type(Type.WATER)
                .build();

        List<Pokemon> pokemonList = new ArrayList<>();
        pokemonList.add(bulbasaur);
        pokemonList.add(charmander);
        pokemonList.add(squirtle);

        return pokemonList;
    }

    protected static List<Move> getFiveMoves() {
        Move moveOne = Move.builder()
                .name("Flamethrower")
                .type(Type.FIRE)
                .build();

        Move moveTwo = Move.builder()
                .name("Razor Leaf")
                .type(Type.GRASS)
                .build();

        Move moveThree = Move.builder()
                .name("Water Gun")
                .type(Type.WATER)
                .build();

        Move moveFour = Move.builder()
                .name("Hydro Pump")
                .type(Type.WATER)
                .build();

        Move moveFive = Move.builder()
                .name("Ember")
                .type(Type.FIRE)
                .build();

        Move[] moves = new Move[] {moveOne, moveTwo, moveThree, moveFour, moveFive};

        return Arrays.asList(moves);
    }

}
