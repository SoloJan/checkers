package nl.jansolo.checkers.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckerGameDto {

    public CheckerGameDto(){
        you = new PlayerDto();
        opponent = new PlayerDto();
        opponent.setName("player2");
        opponent.setColor(Color.BLACK);
        opponent.setMyTurn(false);

    }

    private PlayerDto you;
    private PlayerDto opponent;
    private List<List<String>> board;


}
