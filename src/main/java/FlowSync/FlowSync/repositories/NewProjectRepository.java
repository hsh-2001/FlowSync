package FlowSync.FlowSync.repositories;

import FlowSync.FlowSync.Projection.ProjectDashboardProjection;
import FlowSync.FlowSync.entities.EProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewProjectRepository extends JpaRepository<EProjectEntity, String> {
    Optional<EProjectEntity> findByProjId(String id);
    boolean existsByStatusCode(String statusCode);

    @Query(value = """
            SELECT
                (SELECT COUNT(*) FROM TMS_TBL_PROJECT) AS total_projects,
                (SELECT COUNT(*) FROM TMS_PROJ_STATUS) AS total_statuses,
                (SELECT count(*) FROM TMS_TASK_PRIOR) AS total_priors
            FROM DUAL
        """,
        nativeQuery = true)
    ProjectDashboardProjection getDashboardSummary();
}
