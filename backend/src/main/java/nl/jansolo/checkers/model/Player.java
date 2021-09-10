package nl.jansolo.checkers.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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


    public Player(String name, String opponentName, boolean isWhite){
        this(name, isWhite);
        this.opponent = new Player(opponentName, !isWhite);
    }

    private Player(String name, boolean playsWithWhite){
        this.name = name;
        this.stones = new ArrayList<>();
        if(playsWithWhite){
            addWhiteStones();
        }
        else{
            addBlackStones();
        }
    }

    public Optional<Stone> findStone(int row, int column){
        return stones.stream().filter(s -> s.getColumn().equals(column) && s.getRow().equals(row)).findAny();
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
