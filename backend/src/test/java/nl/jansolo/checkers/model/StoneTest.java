package nl.jansolo.checkers.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import static org.junit.jupiter.api.Assertions.*;

class StoneTest {



    @Test
    public void canMove(){
        Player whitePlayer =  startGame();
        Stone topRight = whitePlayer.findStone(6, 9).get();
        Stone topLeft = whitePlayer.findStone(6, 1).get();

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
        Stone topRight = whitePlayer.findStone(6, 9).get();
        Stone farLeft = whitePlayer.findStone(7, 0).get();
        boolean topRightCanNotMoveToRightOnNextRow =  topRight.canMove(5, 10);
        boolean farLeftCanNotMoveMoreLeft =  farLeft.canMove(5, -1);
        assertFalse(topRightCanNotMoveToRightOnNextRow);
        assertFalse(farLeftCanNotMoveMoreLeft);
    }

    @Test
    public void canNotMoveOnTopOfOtherStone(){
        Player whitePlayer =  startGame();
        Stone stone = whitePlayer.findStone(7, 8).get();
        boolean canNotMoveOnTopOfOtherStone =  stone.canMove(6, 9);
        assertFalse(canNotMoveOnTopOfOtherStone);
    }

    @Test
    public void canMoveForwardsButNotBackwards(){
        Player whitePlayer =  startGame();
        Player blackPlayer =  whitePlayer.getOpponent();
        Stone white = whitePlayer.findStone(6, 7).get();
        Stone black = blackPlayer.findStone(3, 2).get();
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

    private Player startGame(){
        return  new Player("Jan", "Jan's brother", true);
    }


}