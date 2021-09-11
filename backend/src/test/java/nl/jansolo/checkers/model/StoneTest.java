package nl.jansolo.checkers.model;

import nl.jansolo.checkers.exception.InvalidMoveException;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class StoneTest {



    @Test
    public void canMove(){
        Player whitePlayer =  startGame();
        Stone topRight = whitePlayer.getStone(6, 9);
        Stone topLeft = whitePlayer.getStone(6, 1);

        boolean topRightCanMoveToLeftOnNextRow =  topRight.canMove(5, 8);
        boolean topLeftCanMoveToLeftOnNextRow =  topLeft.canMove(5, 0);
        boolean topLeftCanMoveToRightOnNextRow =  topLeft.canMove(5, 2);

        assertTrue(topRightCanMoveToLeftOnNextRow);
        assertTrue(topLeftCanMoveToLeftOnNextRow);
        assertTrue(topLeftCanMoveToRightOnNextRow);
    }

    @Test
    public void canNotMoveOutOfBoard(){
        Player whitePlayer =  startGame();
        Stone topRight = whitePlayer.getStone(6, 9);
        Stone farLeft = whitePlayer.getStone(7, 0);
        boolean topRightCanNotMoveToRightOnNextRow =  topRight.canMove(5, 10);
        boolean farLeftCanNotMoveMoreLeft =  farLeft.canMove(5, -1);
        assertFalse(topRightCanNotMoveToRightOnNextRow);
        assertFalse(farLeftCanNotMoveMoreLeft);
    }

    @Test
    public void canNotMoveOnTopOfOtherStone(){
        Player whitePlayer =  startGame();
        Stone stone = whitePlayer.getStone(7, 8);
        boolean canNotMoveOnTopOfOtherStone =  stone.canMove(6, 9);
        assertFalse(canNotMoveOnTopOfOtherStone);
    }

    @Test
    public void canMoveForwardsButNotBackwards(){
        Player whitePlayer =  startGame();
        Player blackPlayer =  whitePlayer.getOpponent();
        Stone white = whitePlayer.getStone(6, 7);
        Stone black = blackPlayer.getStone(3, 2);
        boolean whiteCanMoveUp =  white.canMove(5, 8);
        boolean blackCanMoveDown =  black.canMove(4, 3);
        assertTrue(whiteCanMoveUp);
        assertTrue(blackCanMoveDown);
        white.move(5, 8);
        black.move(4, 3);
        boolean whiteCanNotMoveBack =  white.canMove(6, 7);
        boolean blackCanMoveBack =  black.canMove(3, 2);
        assertFalse(whiteCanNotMoveBack );
        assertFalse(blackCanMoveBack);

    }

    @Test
    public void canHit(){
        Player whitePlayer =  startGame();
        Player blackPlayer =  whitePlayer.getOpponent();
        Stone white = whitePlayer.getStone(6, 5);
        Stone black = blackPlayer.getStone(3, 2);
        white.move(5, 4);
        black.move(4, 3);
        boolean whiteCanHit =  white.canMove(3, 2);
        boolean blackCanHit =  black.canMove(6, 5);
        assertTrue(whiteCanHit);
        assertTrue(blackCanHit);
    }

    @Test
    public void shouldHit(){
        Player whitePlayer =  startGame();
        Player blackPlayer =  whitePlayer.getOpponent();
        Stone white = whitePlayer.getStone(6, 5);
        Stone black = blackPlayer.getStone(3, 2);
        white.move(5, 4);
        black.move(4, 3);
        boolean canNotIgnoreHitWithSameStone =  white.canMove(4, 5);
        Stone secondStone = whitePlayer.getStone(6, 1);
        boolean canNotIgnoreHitWithSecondStone =  secondStone.canMove(5, 2);
        assertFalse(canNotIgnoreHitWithSameStone);
        assertFalse(canNotIgnoreHitWithSecondStone);
    }


    @Test
    public void moveThrowsExceptionIfItsInvalid(){
        Player whitePlayer =  startGame();
        Stone stone = whitePlayer.getStone(7, 8);
        assertThrows(InvalidMoveException.class, () -> stone.move(12, -4));
    }


    private Player startGame(){
        return  new Player("Jan", "Jan's brother", true);
    }


}