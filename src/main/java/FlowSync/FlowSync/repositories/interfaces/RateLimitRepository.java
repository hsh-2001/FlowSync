package FlowSync.FlowSync.repositories.interfaces;

import FlowSync.FlowSync.entities.RateLimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateLimitRepository extends JpaRepository<RateLimitEntity, Long> {
    List<RateLimitEntity> findByIsActive(Integer isActive);
}
