package andrewkassab.pokedex.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Type {

    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY;

    @JsonCreator
    public static Type fromValue(String value) {
        for (Type type : values()) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid type value: " + value);
    }

    public static boolean valueExists(String value) {
        for (Type type : values()) {
            if (type.toString().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

}
