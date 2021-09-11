package nl.jansolo.checkers.api.dto;

import lombok.Data;

@Data
public class MoveDto {
    private CoordinateDto from;
    private CoordinateDto to;
}
