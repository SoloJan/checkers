package nl.jansolo.checkers.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.jansolo.checkers.exception.InvalidMoveException;
import nl.jansolo.checkers.exception.NotYourTurnException;
import nl.jansolo.checkers.exception.StoneNotFoundException;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"stones, opponent"})
@ToString(exclude = {"stones, opponent"})
public class Player {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="opponent_id")
    private Player opponent;
    @OneToMany(mappedBy = "owner",  orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Stone> stones;
    private boolean myTurn;

    public Player(String name, String opponentName, boolean isWhite){
        this(name, isWhite);
        this.opponent = new Player(opponentName, !isWhite);
        this.opponent.setOpponent(this);
    }

    private Player(String name, boolean playsWithWhite){
        this.name = name;
        this.myTurn = playsWithWhite;
        this.stones = new ArrayList<>();
        if(playsWithWhite){
            addWhiteStones();
        }
        else{
            addBlackStones();
        }
    }

    public Optional<Stone> findStone(int row, int column){
        return stones.stream().filter(s -> Integer.valueOf(column).equals(s.getColumn()) && Integer.valueOf(row).equals(s.getRow())).findAny();
    }

    public Stone getStone(int row, int column){
        return findStone(row, column).orElseThrow(() -> new  StoneNotFoundException());
    }

    /**
     *  This performs a move on the stone it throws an exception if the move is invalid.
     *  It hits an opponent stone if it jumps it.
     *  It then switches turns
     * @param fromRow
     * @param fromColumn
     * @param toRow
     * @param toColumn
     * @throws NotYourTurnException if it is not the turn of this player, or the game has ended
     * @throws StoneNotFoundException if the stone does not exist on the coordinates
     * @throws InvalidMoveException if the stone exist but the move is invalid
     *
     */

    public void move(int fromRow, int fromColumn, int toRow, int toColumn){
        validateCanPlay();
        getStone(fromRow, fromColumn).move(toRow, toColumn);
        switchTurns();
    }

    private void validateCanPlay(){
        if(!myTurn){
            throw new NotYourTurnException();
        }
    }


    private void switchTurns() {
        getOpponent().setMyTurn(myTurn);
        myTurn = !myTurn;
    }


    private void addBlackStones(){
        for(int row = 0; row<=3; row++){
            addRowOfStones(row, false);
        }
    }

    private void addWhiteStones(){
        for(int row = 6; row<=9; row++){
            addRowOfStones(row, true);
        }
    }


    private void addRowOfStones(int row, boolean isStoneWhite){
        if(isZeroOrEven(row)){
            for(int column = 1; column<=9; column=column+2) {
                stones.add(new Stone(this, isStoneWhite, row, column));
            }
        }
        else{
            for(int column = 0; column<=8; column=column+2) {
                stones.add(new Stone(this, isStoneWhite, row, column));
            }
        }
    }

    private boolean isZeroOrEven(int rowNr){
       return rowNr == 0 || rowNr%2==0;
    }





}
