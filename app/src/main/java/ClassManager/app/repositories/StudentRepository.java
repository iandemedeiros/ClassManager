package ClassManager.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ClassManager.app.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
