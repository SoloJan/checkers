package nl.jansolo.checkers.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StartGameDto {
    @Schema(example = "player2")
    private String opponentName;
    @Schema(example = "WHITE")
    private Color colorToPlayWith;
}
