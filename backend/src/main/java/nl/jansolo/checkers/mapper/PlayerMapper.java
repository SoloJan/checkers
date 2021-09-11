package nl.jansolo.checkers.mapper;

import nl.jansolo.checkers.api.dto.CheckerGameDto;
import nl.jansolo.checkers.api.dto.Color;
import nl.jansolo.checkers.api.dto.PlayerDto;
import nl.jansolo.checkers.api.dto.Status;
import nl.jansolo.checkers.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public CheckerGameDto toDto(Player player){
        return new CheckerGameDto(toPlayerDto(player), toPlayerDto(player.getOpponent()), toBoard(player));
    }


    private PlayerDto toPlayerDto(Player player){
        return new PlayerDto(player.getName(), toColor(player.getStones().get(0).isWhite()),  toStatus(player), player.isMyTurn());
    }

    private Status toStatus(Player player){
        if(player.didLose()){
            return Status.LOSER;
        }
        if(player.didWin()){
            return Status.WINNER;
        }
        return Status.PLAYING;
    }

    private String[][] toBoard(Player player){
        String[][] board = new String[10][10];
        for(int row = 0; row<=9; row++){
            for(int column = 0; column<=9; column++){
                board[row][column] = "";
                if(player.findStone(row, column).isPresent()){
                    board[row][column] =  toColor(player.findStone(row, column).get().isWhite()).toString();
                }
                if(player.getOpponent().findStone(row, column).isPresent()){
                    board[row][column] =  toColor(player.getOpponent().findStone(row, column).get().isWhite()).toString();
                }
            }
        }
        return board;
    }


    private Color toColor(boolean isWhite){
        return isWhite ? Color.WHITE : Color.BLACK;
    }

}
