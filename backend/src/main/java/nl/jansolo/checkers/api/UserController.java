package nl.jansolo.checkers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nl.jansolo.checkers.api.dto.UserDto;
import nl.jansolo.checkers.mapper.UserMapper;
import nl.jansolo.checkers.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Tag(name= "User-controller", description = "This is the user controller in the future you can create a new user here," +
        " for now the only options is to find all opponents, so you can invite someone for a game of checkers")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping("/opponent")
    @Operation(summary = "Get all the available opponents")
    public ResponseEntity<List<UserDto>> getAllAvailableOpponents(Principal principal) {
        List<UserDto> users = service.getOpponents(principal).stream().map(mapper::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
