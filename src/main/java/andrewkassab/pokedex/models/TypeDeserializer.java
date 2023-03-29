package andrewkassab.pokedex.models;

import andrewkassab.pokedex.controller.exceptions.TypeNotValidException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.IOException;

public class TypeDeserializer extends JsonDeserializer<Type> {

    @Override
    public Type deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        var value = jsonParser.getValueAsString();
        try {
            return Type.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TypeNotValidException("Type " + value + " is not a valid Type");
        }
    }

}
