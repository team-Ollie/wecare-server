package ollie.wecare.user.repository;

import ollie.wecare.user.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center, Long> {
    Optional<Center> findByCenterIdxAndStatusEquals(Long centerIdx, String status);
}
