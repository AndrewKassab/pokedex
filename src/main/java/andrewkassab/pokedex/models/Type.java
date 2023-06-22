package andrewkassab.pokedex.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

public enum Type {

    NORMAL("Normal", "#A8A878"),
    FIRE("Fire", "#F08030"),
    WATER("Water", "#6890F0"),
    GRASS("Grass", "#78C850"),
    ELECTRIC("Electric", "#F8D030"),
    ICE("Ice", "#98D8D8"),
    FIGHTING("Fighting", "#C03028"),
    POISON("Poison", "#A040A0"),
    GROUND("Ground", "#E0C068"),
    FLYING("Flying", "#A890F0"),
    PSYCHIC("Psychic", "#F85888"),
    BUG("Bug", "#A8B820"),
    ROCK("Rock", "#B8A038"),
    GHOST("Ghost", "#705898"),
    DRAGON("Dragon", "#7038F8"),
    DARK("Dark", "#705848"),
    STEEL("Steel", "#B8B8D0"),
    FAIRY("Fairy", "#EE99AC");

    private final String name;

    @Getter
    private final String color;

    Type(String name, String color) {
        this.name = name;
        this.color = color;
    }

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

    @Override
    public String toString() {
        return name.toUpperCase();
    }

}
