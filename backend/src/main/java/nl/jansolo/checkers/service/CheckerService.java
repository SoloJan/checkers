package nl.jansolo.checkers.service;

import lombok.RequiredArgsConstructor;
import nl.jansolo.checkers.api.dto.Color;
import nl.jansolo.checkers.config.UserBean;
import nl.jansolo.checkers.model.Player;
import nl.jansolo.checkers.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CheckerService {

    private final UserService userService;
    private final PlayerRepository playerRepository;


    //TODO at this stage all existing games are thrown away when a user starts a new game
    @Transactional
    public Player startGame(Principal principal, String opponentName, Color colorToPlayWith) {
        UserBean opponent = userService.getOpponent(principal, opponentName);
        playerRepository.deleteAllInBatch();
        return playerRepository.save(new Player(principal.getName(), opponent.getName(), Color.WHITE.equals(colorToPlayWith)));

    }
}
