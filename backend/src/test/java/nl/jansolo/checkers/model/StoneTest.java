package nl.jansolo.checkers.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import static org.junit.jupiter.api.Assertions.*;

class StoneTest {

    @Test
    public void createStone(){
        Stone stone = new Stone(new Player(), false, -1, 8);


    }


}