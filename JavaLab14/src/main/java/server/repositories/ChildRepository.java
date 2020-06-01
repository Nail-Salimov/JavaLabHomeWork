package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entities.child.Child;

public interface ChildRepository extends JpaRepository<Child, Long> {
}
