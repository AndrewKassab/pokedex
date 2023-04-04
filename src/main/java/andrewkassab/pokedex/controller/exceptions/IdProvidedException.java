package andrewkassab.pokedex.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Id should not be provided")
public class IdProvidedException extends RuntimeException {

    public IdProvidedException(String message) {
        super(message);
    }

}
