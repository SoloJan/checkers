package nl.jansolo.checkers.mapper;

import nl.jansolo.checkers.api.dto.UserDto;
import nl.jansolo.checkers.config.UserBean;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDTO(UserBean user){
        return new UserDto(user.getName());
    }
}
