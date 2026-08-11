package FlowSync.FlowSync.repositories;

import FlowSync.FlowSync.entities.EUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewUserRepository extends JpaRepository<EUserEntity, Integer> {
    Optional<EUserEntity> findByUsername(String username);
}
