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

    protected List<Move> getFiveMoves() {
        Move moveOne = Move.builder()
                .id(1)
                .name("Flamethrower")
                .type(Type.FIRE)
                .build();

        Move moveTwo = Move.builder()
                .id(2)
                .name("Razor Leaf")
                .type(Type.GRASS)
                .build();

        Move moveThree = Move.builder()
                .id(3)
                .name("Water Gun")
                .type(Type.WATER)
                .build();

        Move moveFour = Move.builder()
                .id(4)
                .name("Hydro Pump")
                .type(Type.WATER)
                .build();

        Move moveFive = Move.builder()
                .id(5)
                .name("Ember")
                .type(Type.FIRE)
                .build();

        Move[] moves = new Move[] {moveOne, moveTwo, moveThree, moveFour, moveFive};

        return Arrays.asList(moves);
    }

}
