package nl.jansolo.checkers.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static nl.jansolo.checkers.api.dto.Color.WHITE;

@Data
public class PlayerDto {

    public PlayerDto(){
        name = "player1";
        color = WHITE;
        status = Status.PLAYING;
        isMyTurn = true;
    }

    private String name;
    @Schema(example = "WHITE")
    private Color color;
    @Schema(example = "PLAYING")
    private Status status;
    private boolean isMyTurn;

}
