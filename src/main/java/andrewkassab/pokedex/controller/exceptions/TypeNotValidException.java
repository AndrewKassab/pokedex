package andrewkassab.pokedex.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TypeNotValidException extends RuntimeException {

    public TypeNotValidException(String message) {
        super(message);
    }

}
