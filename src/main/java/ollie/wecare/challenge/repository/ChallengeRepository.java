package ollie.wecare.challenge.repository;


import ollie.wecare.challenge.entity.Challenge;
import ollie.wecare.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByNameContainingAndParticipantsNotContaining(String searchWord, User user);
    List<Challenge> findByAdmin(User user);
    Optional<Challenge> findTop1ByOrderByAttendanceRateDesc();
    @Query(value = "select c " +
            "from Challenge c " +
            "where size(c.participants) = " + "" +
            "(select max(size(subc.participants)) from Challenge subc)")
    Page<Challenge> findMostParticipatedChallenge(Pageable pageable);

    Optional<Challenge> findTop1ByOrderByCreatedDateDesc();
    Optional<Challenge> findByChallengeIdxAndStatusEquals(Long challengeIdx, String status);
    List<Challenge> findByParticipantsContaining(User user);
    List<Challenge> findByParticipantsNotContaining(User user);
}
