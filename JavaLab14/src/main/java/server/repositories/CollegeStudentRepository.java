package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entities.child.Child;
import server.entities.students.CollegeStudent;

public interface CollegeStudentRepository extends JpaRepository<CollegeStudent, Long> {
}
