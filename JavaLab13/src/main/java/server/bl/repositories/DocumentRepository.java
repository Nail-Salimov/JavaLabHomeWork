package server.bl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entities.document.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
