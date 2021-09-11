package nl.jansolo.checkers.model;

import nl.jansolo.checkers.exception.NotYourTurnException;
import nl.jansolo.checkers.exception.StoneNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {


    /**
    We assert the setup of the game, but we only assert the position of some of the stones
     **/
    @Test
    void creatingAPlayerShouldStartTheWholeGame(){
        Player player = new Player("Jan", "Jan's brother", true);
        assertEquals("Jan", player.getName());
        assertEquals("Jan's brother", player.getOpponent().getName());
        assertEquals(player, player.getOpponent().getOpponent());
        assertEquals(20, player.getStones().size());
        assertEquals(20, player.getOpponent().getStones().size());
        assertTrue(player.getStones().stream().allMatch(Stone::isWhite));
        assertFalse(player.getOpponent().getStones().stream().anyMatch(Stone::isWhite));
        assertTrue(player.findStone(6,1).isPresent());
        assertTrue(player.findStone(9,8).isPresent());
        assertTrue(player.getOpponent().findStone(0, 1).isPresent());
        assertTrue(player.getOpponent().findStone(3,8).isPresent());
    }

    @Test
    void canNotPlayIfItsNotYourTurn() {
        Player player = startAsBlackPlayer();
        assertThrows(NotYourTurnException.class, () ->  player.move(3,2, 4,3));
    }

    @Test
    void canNotPlayWithNonExistingStone() {
        Player player = startAsWhitePlayer();
        assertThrows(StoneNotFoundException.class, () ->  player.move(4,4, 3,3));
    }

    @Test
    void moveShouldSwitchTurns(){
        Player player = startAsWhitePlayer();
        player.move(6,9, 5,8);
        assertFalse(player.isMyTurn());
        assertTrue(player.getOpponent().isMyTurn());
    }

    @Test
    void playerLosesWhenHeRunsOutOfMoves(){
        Player whitePlayer = startAsWhitePlayer();

        //blackPlayer loses all his stones
        whitePlayer.getOpponent().getStones().stream().forEach(p -> p.gotHit());

        // whitePlayer moves stone, and the black player gets the turn
        whitePlayer.move(6,9, 5,8);

        assertTrue(whitePlayer.didWin());
        assertTrue(whitePlayer.getOpponent().didLose());
        assertTrue(whitePlayer.gameEnded());
        assertTrue(whitePlayer.getOpponent().gameEnded());

        //Both black and white can not move anymore because the game has ended
        assertThrows(NotYourTurnException.class, () ->  whitePlayer.move(3,2, 4,3));
        assertThrows(NotYourTurnException.class, () ->  whitePlayer.getOpponent().move(3,2, 4,3));

    }



    private Player startAsWhitePlayer() {
        return startGame(true);
    }

    private Player startAsBlackPlayer() {
        return startGame(false);
    }

    private Player startGame(boolean asWhitePlayer) {
        return new Player("Jan", "Jan's brother", asWhitePlayer);
    }


}