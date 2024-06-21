package ollie.wecare.challenge.repository;

import ollie.wecare.challenge.entity.ChallengeAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeAttendanceRepository extends JpaRepository<ChallengeAttendance, Long> {
   List<ChallengeAttendance> findByUser_UserIdx(Long userIdx);
}
