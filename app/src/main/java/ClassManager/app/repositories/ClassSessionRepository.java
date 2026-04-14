package ClassManager.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ClassManager.app.entities.ClassSession;

public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {
}
