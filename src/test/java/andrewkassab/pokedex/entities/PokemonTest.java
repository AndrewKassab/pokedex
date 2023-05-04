package andrewkassab.pokedex.entities;

import andrewkassab.pokedex.entitites.Pokemon;
import andrewkassab.pokedex.models.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PokemonTest {

    @Test
    public void testPokemonIsWeakToType() {
        Pokemon newPokemon = Pokemon.builder().primaryType(Type.GRASS).build();
        newPokemon.getTypeWeaknesses().add(Type.FIRE);
        assertTrue(newPokemon.isWeakToType(Type.FIRE));
    }

}
