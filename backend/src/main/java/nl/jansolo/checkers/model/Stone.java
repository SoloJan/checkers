package nl.jansolo.checkers.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.jansolo.checkers.exception.InvalidMoveException;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Optional;

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

    @Column(name = "is_white")
    private boolean white;

    Stone(Player owner, boolean isWhite,  int row, int column) {
         this.owner = owner;
         this.white = isWhite;
         this.row = row;
         this.column = column;
     }


     public void move(int row, int column){
         if(!canMove(row, column)){
             throw new InvalidMoveException();
         }
         hitIfPossible(row, column);
         this.row = row;
         this.column = column;
     }


    /**
     * This method checks if the stone can do a requested move.
     * A valid move is within the space of the board The upper left corner has coordinates (0,0) the bottom right corner has coordinates (9,9)
     * A valid move is not on top of a different stone
     * A valid move can be a hit in which case you jump a stone of the opponent
     * If you can hit, you should hit.
     * If a different stone can hit, you are not allowed to do a normal move.
     * White moves up in the board, black moves down but hits can go in both directions
     *
     * @param row the row to move the stone to
     * @param column the column to move the stone to
     * @return if the requested move is valid
     */
     public boolean canMove(int row, int column){
         if(isOutOfBoard(row) || isOutOfBoard(column)){
             return false;
         }
         if(isOnTopOfOtherStone(row, column)){
             return false;
         }
         if(canHit()){
             return isValidHit(row, column);
         }
        return !canADifferentStoneHit() && isValidBasicMove(row, column);
     }

    /**
     *
     * @return if the stone can move in any direction
     */
    public boolean canMove(){
         return canHit() ||
                 canMove(row+1, column+1) || canMove(row+1, column-1) ||
                 canMove(row-1, column+1) || canMove(row-1, column-1);
    }


    public boolean canHit(){
         return isValidHit(this.row+2, this.column +2) || isValidHit(this.row+2, this.column -2) ||
                 isValidHit(this.row-2, this.column +2) || isValidHit(this.row-2, this.column -2);
     }

     void gotHit(){
        this.column = null;
        this.row = null;
    }


    private void hitIfPossible(int row, int column) {
         getStoneToJump(row, column).ifPresent(Stone::gotHit);
    }

     private Optional<Stone> getStoneToJump(int row, int column){
         if(row == this.row+2){
             if(column  == this.column+2){
                 return getOwner().getOpponent().findStone(row-1, column-1);
             }
             if(column == this.getColumn()-2){
                 return getOwner().getOpponent().findStone(row-1, column+1);
             }
         }
         if(row == this.row-2){
             if(column  == this.column+2){
                 return getOwner().getOpponent().findStone(row+1, column-1);
             }
             if(column == this.getColumn()-2){
                 return getOwner().getOpponent().findStone(row+1, column+1);
             }
         }
         return Optional.empty();
     }

     private boolean isValidHit(int row, int column){
        return getStoneToJump(row, column).isPresent();
     }

     private boolean canADifferentStoneHit(){
         return this.getOwner().getStones().stream().anyMatch(Stone::canHit);
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
