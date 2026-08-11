package FlowSync.FlowSync.repositories;

import FlowSync.FlowSync.entities.EProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewProjectRepository extends JpaRepository<EProjectEntity, String> {
    Optional<EProjectEntity> findByProjId(String id);
    boolean existsByStatusCode(String statusCode);
}
