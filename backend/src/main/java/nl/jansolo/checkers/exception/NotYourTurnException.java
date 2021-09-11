package nl.jansolo.checkers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class NotYourTurnException extends RuntimeException {

    public NotYourTurnException() {
        super("Its not your turn wait for the other player to move, or the game has ended");
    }
}
