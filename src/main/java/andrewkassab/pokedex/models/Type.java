package andrewkassab.pokedex.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Type {
    FIRE, WATER, GRASS;

    @JsonCreator
    public static Type fromValue(String value) {
        for (Type type : values()) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid type value: " + value);
    }

}
