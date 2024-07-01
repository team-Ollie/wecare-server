package ollie.wecare.challenge.repository;

import ollie.wecare.challenge.entity.Challenge;
import ollie.wecare.challenge.entity.ChallengeAttendance;
import ollie.wecare.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeAttendanceRepository extends JpaRepository<ChallengeAttendance, Long> {
   List<ChallengeAttendance> findByUser_UserIdx(Long userIdx);
   List<ChallengeAttendance> findByUserAndChallenge_ChallengeIdx(User user, Long challengeIdx);
   List<ChallengeAttendance> findByUserAndChallengeOrderByCreatedDate(User user, Challenge challenge);
   Integer countByUserAndChallenge(User user, Challenge challenge);
   List<ChallengeAttendance> findByUserAndStatusEquals(User user, String status);
}
