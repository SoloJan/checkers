package nl.jansolo.checkers.service;

import lombok.RequiredArgsConstructor;
import nl.jansolo.checkers.api.dto.Color;
import nl.jansolo.checkers.config.UserBean;
import nl.jansolo.checkers.exception.PlayerNotFoundException;
import nl.jansolo.checkers.model.Player;
import nl.jansolo.checkers.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CheckersService {

    private final UserService userService;
    private final PlayerRepository playerRepository;


    @Transactional
    public Player startGame(Principal principal, String opponentName, Color colorToPlayWith) {
        UserBean opponent = userService.getOpponent(principal, opponentName);
        endGameForUser(principal);
        return playerRepository.save(new Player(principal.getName(), opponent.getName(), Color.WHITE.equals(colorToPlayWith)));
    }

    @Transactional
    public Player doMove(Principal principal, int fromRow, int fromColumn, int toRow, int toColumn) {
        Player player = playerRepository.findByName(principal.getName()).orElseThrow(()-> new PlayerNotFoundException(principal.getName()));
        player.move(fromRow, fromColumn, toRow, toColumn);
        return player;
    }

    @Transactional
    public void endGameForUser(Principal principal) {
        playerRepository.findByName(principal.getName()).ifPresent(p -> playerRepository.delete(p));
    }


}
