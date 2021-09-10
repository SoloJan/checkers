package nl.jansolo.bookstore.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class BookstoreDto {

    private String name;
    private List<StockDto> stock;
}
