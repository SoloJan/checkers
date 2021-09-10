package nl.jansolo.checkers.mapper;

import nl.jansolo.checkers.api.dto.*;
import nl.jansolo.checkers.config.UserBean;
import nl.jansolo.checkers.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public CheckerGameDto toDto(Player player){
        return new CheckerGameDto(toPlayerDto(player, Status.PLAYING), toPlayerDto(player.getOpponent(), Status.PLAYING), toBoard(player));
    }


    public PlayerDto toPlayerDto(Player player, Status status){
        return new PlayerDto(player.getName(), toColor(player.getStones().get(0).isWhite()),  status, player.isMyTurn());
    }

    public String[][] toBoard(Player player){
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
