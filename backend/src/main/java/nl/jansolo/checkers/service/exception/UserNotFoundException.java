package nl.jansolo.checkers.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String name) {
        super(String.format("%s is not a valid user, please use the /opponents api to see a list of all available opponents", name));
    }

}
