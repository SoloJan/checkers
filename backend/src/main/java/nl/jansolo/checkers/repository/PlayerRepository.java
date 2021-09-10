package nl.jansolo.checkers.repository;

import nl.jansolo.checkers.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {


}
