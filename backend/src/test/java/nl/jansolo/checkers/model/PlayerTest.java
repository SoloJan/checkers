package nl.jansolo.checkers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {


    /**
    We assert the setup of the game, but we only assert the position of some of the stones
     **/
    @Test
    public void creatingAPlayerShouldStartTheWholeGame(){
        Player player = new Player("Jan", "Jan's brother", true);
        assertEquals("Jan", player.getName());
        assertEquals("Jan's brother", player.getOpponent().getName());
        assertEquals(20, player.getStones().size());
        assertEquals(20, player.getOpponent().getStones().size());
        assertTrue(player.getStones().stream().allMatch(Stone::isWhite));
        assertFalse(player.getOpponent().getStones().stream().anyMatch(Stone::isWhite));
        assertTrue(player.findStone(6,1).isPresent());
        assertTrue(player.findStone(9,8).isPresent());
        assertTrue(player.getOpponent().findStone(0, 1).isPresent());
        assertTrue(player.getOpponent().findStone(3,8).isPresent());
    }




}