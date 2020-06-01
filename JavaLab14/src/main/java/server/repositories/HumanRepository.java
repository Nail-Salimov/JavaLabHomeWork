package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entities.human.Human;

public interface HumanRepository extends JpaRepository<Human, Long> {
}
