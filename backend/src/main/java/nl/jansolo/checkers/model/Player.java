package nl.jansolo.checkers.model;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
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

}
