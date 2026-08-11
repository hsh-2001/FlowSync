package FlowSync.FlowSync.repositories;

import FlowSync.FlowSync.entities.EProjectStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<EProjectStatusEntity, String> {

}
