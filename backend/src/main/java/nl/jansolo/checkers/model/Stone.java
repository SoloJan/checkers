package nl.jansolo.checkers.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
public class Stone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "player_id",  referencedColumnName = "id")
    private Player owner;

    @Column(name = "row_position")
    @Size(min = 0, max = 9, message = "You must place the stone on the board")
    private Integer row;
    @Column(name = "column_position")
    @Size(min = 0, max = 9, message = "You must place the stone on the board")
    private Integer column;

    @Column(name = "is_in_game")
    private boolean inGame;
    @Column(name = "is_white")
    private boolean white;
}
