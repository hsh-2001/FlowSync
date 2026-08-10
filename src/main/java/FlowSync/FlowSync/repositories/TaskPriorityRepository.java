package FlowSync.FlowSync.repositories;

import FlowSync.FlowSync.dto.priority.CreateTaskPriorityRequest;
import FlowSync.FlowSync.dto.priority.UpdateTaskPriorityRequest;
import FlowSync.FlowSync.models.TaskPriority;
import FlowSync.FlowSync.repositories.interfaces.ITaskPriorityRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskPriorityRepository implements ITaskPriorityRepository {
    private final JdbcTemplate jdbcTemplate;
    public TaskPriorityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TaskPriority> findAll() {
        String sql = """
            SELECT
                ID,
                PRIOR_CODE,
                PRIOR_DESC,
                SORT_ORDER,
                IS_ACTIVE,
                CREATED_DT
            FROM TMS_TASK_PRIOR
            ORDER BY SORT_ORDER ASC, CREATED_DT DESC
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapTaskPriority(rs));
    }

    @Override
    public Optional<TaskPriority> findById(Long id) {
        String sql = """
            SELECT
                ID,
                PRIOR_CODE,
                PRIOR_DESC,
                SORT_ORDER,
                IS_ACTIVE,
                CREATED_DT
            FROM TMS_TASK_PRIOR
            WHERE ID = ?
              AND IS_ACTIVE = 1
            """;

        List<TaskPriority> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapTaskPriority(rs),
                id
        );

        return result.stream().findFirst();
    }

    @Override
    public Optional<TaskPriority> findByCode(String priorCode) {
        String sql = """
            SELECT
                ID,
                PRIOR_CODE,
                PRIOR_DESC,
                SORT_ORDER,
                IS_ACTIVE,
                CREATED_DT
            FROM TMS_TASK_PRIOR
            WHERE UPPER(PRIOR_CODE) = UPPER(?)
              AND IS_ACTIVE = 1
            """;

        List<TaskPriority> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapTaskPriority(rs),
                priorCode
        );

        return result.stream().findFirst();
    }

    @Override
    public int create(CreateTaskPriorityRequest request) {
        String sql = """
            INSERT INTO TMS_TASK_PRIOR (
                PRIOR_CODE,
                PRIOR_DESC,
                SORT_ORDER,
                IS_ACTIVE
            )
            VALUES (?, ?, ?, ?)
            """;

        return jdbcTemplate.update(
                sql,
                request.getPriorCode(),
                request.getPriorDesc(),
                request.getSortOrder(),
                request.getIsActive()
        );
    }

    @Override
    public int update(UpdateTaskPriorityRequest request) {
        String sql = """
        UPDATE TMS_TASK_PRIOR
        SET
            PRIOR_CODE = ?,
            PRIOR_DESC = ?,
            SORT_ORDER = ?,
            IS_ACTIVE = ?
        WHERE ID = ?
          AND NOT EXISTS (
              SELECT 1
              FROM TMS_TASK_PRIOR
              WHERE PRIOR_CODE = ?
                AND ID <> ?
          )
        """;

       return jdbcTemplate.update(
            sql,
            request.getPriorCode(),
            request.getPriorDesc(),
            request.getSortOrder(),
            request.getIsActive(),
            request.getId(),
            request.getPriorCode(),
            request.getId()
       );
    }

    @Override
    public int delete(Long id) {
        String sql = """
            DELETE FROM TMS_TASK_PRIOR
            WHERE ID = ?
            """;
        return jdbcTemplate.update(sql, id);
    }

    private TaskPriority mapTaskPriority(ResultSet rs) throws SQLException {
        TaskPriority taskPriority = new TaskPriority();

        taskPriority.setId(rs.getLong("ID"));
        taskPriority.setPriorCode(rs.getString("PRIOR_CODE"));
        taskPriority.setPriorDesc(rs.getString("PRIOR_DESC"));
        taskPriority.setSortOrder(rs.getInt("SORT_ORDER"));
        taskPriority.setIsActive(rs.getInt("IS_ACTIVE"));
        taskPriority.setCreatedDt(rs.getTimestamp("CREATED_DT").toLocalDateTime());

        return taskPriority;
    }
}
