package nl.jansolo.checkers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import nl.jansolo.checkers.api.dto.CheckersGameDto;
import nl.jansolo.checkers.api.dto.MoveDto;
import nl.jansolo.checkers.api.dto.StartGameDto;
import nl.jansolo.checkers.mapper.PlayerMapper;
import nl.jansolo.checkers.model.Player;
import nl.jansolo.checkers.service.CheckersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/checkers")
@RequiredArgsConstructor
public class CheckersController {

    private final CheckersService service;
    private final PlayerMapper mapper;

    @PostMapping
    @Operation(summary = "Starts a game, with two players, be warned this ends all other games")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the initial game state, with two players, and a board",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CheckersGameDto.class)) }),
            @ApiResponse(responseCode = "404", description = "The chosen opponent does not exist",
                    content = @Content)})
    public ResponseEntity<CheckersGameDto> startGame(Principal principal, @RequestBody StartGameDto startGame) {
        Player player = service.startGame(principal, startGame.getOpponentName(), startGame.getColorToPlayWith());
        return new ResponseEntity<>(mapper.toDto(player), HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Api to do a move, you can only play if its your turn, and you can only play one of your owns stones to an empty spot on the board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the game state after the move of this player, check the isMyTurn value to see if you sh",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CheckersGameDto.class)) }),
            @ApiResponse(responseCode = "404", description = "There is no game active for the player, or the stone you are trying to move does not exist",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Its not your turn, or the game has finished",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid move",
                    content = @Content)})
    public ResponseEntity<CheckersGameDto> move(Principal principal, @RequestBody MoveDto move) {
        Player player = service.doMove(principal, move.getFrom().getRow(), move.getFrom().getColumn(), move.getTo().getRow(), move.getTo().getColumn());
        return new ResponseEntity<>(mapper.toDto(player), HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "If the player is in a current game, this will end it")
    public ResponseEntity<Void> endGame(Principal principal) {
        service.endGameForUser(principal);
        return new ResponseEntity<Void>( HttpStatus.NO_CONTENT);
    }

}
