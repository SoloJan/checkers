package nl.jansolo.checkers.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidMoveException extends RuntimeException {

    public InvalidMoveException() {
        super("The move you are doing is not valid");
    }
}
