package FlowSync.FlowSync.repositories;

import FlowSync.FlowSync.entities.ETaskPriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewTaskPriorityRepository extends JpaRepository<ETaskPriorityEntity, Integer> {

    Optional<ETaskPriorityEntity> findByPriorCode(String priorCode);
    Optional<ETaskPriorityEntity> findById(Long id);
}