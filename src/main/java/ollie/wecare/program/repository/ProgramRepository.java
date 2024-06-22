package ollie.wecare.program.repository;

import ollie.wecare.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByDueDateBetween(LocalDateTime firstDay, LocalDateTime lastDay);

}
