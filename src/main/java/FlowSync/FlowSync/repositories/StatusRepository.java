package FlowSync.FlowSync.repositories;

import FlowSync.FlowSync.entities.EProjectStatusEntity;
import FlowSync.FlowSync.models.BaseProcedureResponse;
import FlowSync.FlowSync.models.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StatusRepository extends JpaRepository<EProjectStatusEntity, String> {

    @Procedure(procedureName = "GET_ALL_PROJ_STATUSES", refCursor = true)
    List<ProjectStatus> findAllProjectStatus(
            @Param("P_PAGE") Integer page,
            @Param("P_PAGE_SIZE") Integer pageSize
    );

    @Procedure(procedureName = "CREATE_STATUS", refCursor = true)
    Object createStatus(
            @Param("P_STATUS_NAME") String statusName,
            @Param("P_STATUS_ORDER") Integer statusOrder,
            @Param("P_STATUS_COLOR") String statusColor,
            @Param("P_STATUS_CODE") String statusCode
    );


}
