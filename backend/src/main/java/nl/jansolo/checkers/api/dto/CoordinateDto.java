package nl.jansolo.checkers.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CoordinateDto {

    @Schema(example = "6")
    private int row;
    @Schema(example = "9")
    private int column;
}
