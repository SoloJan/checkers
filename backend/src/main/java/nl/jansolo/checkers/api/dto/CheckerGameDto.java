package nl.jansolo.checkers.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckerGameDto {

    private PlayerDto you;
    private PlayerDto opponent;
    private String[][]  board = new String[10][10];


}
