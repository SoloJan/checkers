package nl.jansolo.checkers.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class OpponentNotFoundException extends RuntimeException {

    public OpponentNotFoundException(String name) {
        super(String.format("%s is not a valid opponent, please use the user/opponents api to see a list of all available opponents", name));
    }

}
