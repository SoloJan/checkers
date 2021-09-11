package nl.jansolo.checkers.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckersGameDto {

    private PlayerDto you;
    private PlayerDto opponent;
    private String[][]  board = new String[10][10];


}
