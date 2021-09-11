package nl.jansolo.checkers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String name) {
        super(String.format("There is no game going on for player %s please start the game first", name));
    }


}
