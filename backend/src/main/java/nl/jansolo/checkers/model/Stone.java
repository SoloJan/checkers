package nl.jansolo.checkers.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jansolo.checkers.service.exception.InvalidMoveException;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
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

     public Stone(Player owner, boolean isWhite,  int row, int column) {
         this.owner = owner;
         this.white = isWhite;
         this.row = row;
         this. column = column;
         this.inGame = true;
     }

     public void move(int row, int column){
         if(!canMove(row, column)){
             throw new InvalidMoveException();
         }
         this.row = row;
         this.column = column;
     }

     public boolean canMove(int row, int column){
         if(isOutOfBoard(row) || isOutOfBoard(column)){
             return false;
         }
         if(isOnTopOfOtherStone(row, column)){
             return false;
         }
        return isValidBasicMove(row, column);
     }

     private boolean isValidBasicMove(int row, int column){
       return isRowValidForBasicMove(row) && isColumnValidForBasicMove(column);
     }

     private boolean isRowValidForBasicMove(int row){
         if(white){
             return row == this.row-1;
         }
         return row == this.row +1;
     }

    private boolean isColumnValidForBasicMove(int column){
       return   column == this.column + 1 || column == this.column - 1;
    }


    public boolean isOnTopOfOtherStone(int row, int column){
         return owner.findStone(row, column).isPresent() || owner.getOpponent().findStone(row, column).isPresent();
    }

     public boolean isOutOfBoard(int rowOrColumn){
         return rowOrColumn < 0 || rowOrColumn > 9;
     }


}
