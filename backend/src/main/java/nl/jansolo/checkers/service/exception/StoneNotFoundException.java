package nl.jansolo.checkers.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StoneNotFoundException extends RuntimeException {

    public StoneNotFoundException() {
        super(String.format("The stone you are trying to move does not exist"));
    }

}
