package nl.jansolo.checkers.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static nl.jansolo.checkers.api.dto.Color.WHITE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    private String name;
    @Schema(example = "WHITE")
    private Color color;
    @Schema(example = "PLAYING")
    private Status status;
    private boolean isMyTurn;

}
