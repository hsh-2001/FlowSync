package FlowSync.FlowSync.repositories;

import FlowSync.FlowSync.dto.DeleteProjectRequest;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;
import FlowSync.FlowSync.repositories.interfaces.IProjectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProjectRepository implements IProjectRepository {
    private final JdbcTemplate jdbcTemplate;
    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String save(Project project) {
        String sql = """
                INSERT INTO TMS_TBL_PROJECT
                (
                    PROJ_ID,
                    PROJ_NAME,
                    PROJ_DES,
                    PROJ_TYPE,
                    PROJ_MGT,
                    PROJ_OWNER,
                    PRIOR_CODE,
                    STATUS_CODE,
                    START_DATE,
                    END_DATE,
                    PROGRESS,
                    IS_ACTIVE,
                    CREATED_BY,
                    CREATED_DATE
                )
                VALUES
                (
                    ?,?,?,?,?,?,?,?,?,?,
                    ?,?,?,SYSDATE
                )
                """;


        jdbcTemplate.update(
             sql,
             project.getProjId(),
             project.getProjName(),
             project.getProjDes(),
             project.getProjType(),
             project.getProjMgt(),
             project.getProjOwner(),
             project.getPriorCode(),
             project.getStatusCode(),
             project.getStartDate() != null
                     ? Date.valueOf(project.getStartDate())
                     : null,
             project.getEndDate() != null
                     ? Date.valueOf(project.getEndDate())
                     : null,
             project.getProgress(),
             project.getIsActive(),
             project.getCreatedBy()
         );
         return project.getProjId();
    }

    @Override
    public String update(Project project) {
        String sql = """
                UPDATE TMS_TBL_PROJECT
                SET
                    PROJ_NAME = ?,
                    PROJ_DES = ?,
                    PROJ_TYPE = ?,
                    PROJ_MGT = ?,
                    PROJ_OWNER = ?,
                    PRIOR_CODE = ?,
                    STATUS_CODE = ?,
                    START_DATE = ?,
                    END_DATE = ?,
                    ACTUAL_END_DATE = ?,
                    PROGRESS = ?,
                    IS_ACTIVE = ?,
                    UPDATED_BY = ?,
                    UPDATED_DATE = SYSDATE
                WHERE PROJ_ID = ?
                """;


         jdbcTemplate.update(
                sql,
                project.getProjName(),
                project.getProjDes(),
                project.getProjType(),
                project.getProjMgt(),
                project.getProjOwner(),
                project.getPriorCode(),
                project.getStatusCode(),
                project.getStartDate() != null
                        ? Date.valueOf(project.getStartDate())
                        : null,
                project.getEndDate() != null
                        ? Date.valueOf(project.getEndDate())
                        : null,
                project.getActualEndDate() != null
                        ? Date.valueOf(project.getActualEndDate())
                        : null,
                project.getProgress(),
                project.getIsActive(),
                project.getUpdatedBy(),
                project.getProjId()
        );

        return project.getProjId();
    }

    @Override
    public void delete(DeleteProjectRequest request) {
        String sql = """
                    DELETE FROM TMS_TBL_PROJECT WHERE PROJ_ID = ?
                """;
        jdbcTemplate.update(sql, request.getId());
    };

    @Override
    public List<Project> findAll() {
        String sql = """
                SELECT
                    PROJ_ID,
                    PROJ_NAME,
                    PROJ_DES,
                    PROJ_TYPE,
                    PROJ_MGT,
                    PROJ_OWNER,
                    PRIOR_CODE,
                    STATUS_CODE,
                    START_DATE,
                    END_DATE,
                    ACTUAL_END_DATE,
                    PROGRESS,
                    IS_ACTIVE,
                    CREATED_BY,
                    CREATED_DATE,
                    UPDATED_BY,
                    UPDATED_DATE
                FROM TMS_TBL_PROJECT
                WHERE IS_ACTIVE = '1'
                ORDER BY CREATED_DATE DESC
                """;


        return jdbcTemplate.query(sql, (rs, rowNum) -> mapProject(rs));
    }

    @Override
    public Project findById(String id) {
        String sql = """
                 SELECT
                    PROJ_ID,
                    PROJ_NAME,
                    PROJ_DES,
                    PROJ_TYPE,
                    PROJ_MGT,
                    PROJ_OWNER,
                    PRIOR_CODE,
                    STATUS_CODE,
                    START_DATE,
                    END_DATE,
                    ACTUAL_END_DATE,
                    PROGRESS,
                    IS_ACTIVE,
                    CREATED_BY,
                    CREATED_DATE,
                    UPDATED_BY,
                    UPDATED_DATE
                FROM TMS_TBL_PROJECT
                WHERE PROJ_ID = ?
                ORDER BY CREATED_DATE DESC
                """;
        List<Project> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapProject(rs),
                id
        );
        return result.isEmpty() ? null : result.getFirst();
    }

    @Override
    public String createStatus(ProjectStatus status) {
        String sql = """
            INSERT INTO TMS_PROJ_STATUS
            (
                STATUS_CODE,
                STATUS_NAME,
                STATUS_ORDER,
                STATUS_COLOR
            )
            VALUES (?, ?, ?, ?)
        """;

        int result = jdbcTemplate.update(
                sql,
                status.getStatusCode(),
                status.getStatusName(),
                status.getStatusOrder(),
                status.getStatusColor()
        );
        if (result > 0) {
            return "Create Successfully!";
        }
        return null;
    }

    @Override
    public List<ProjectStatus> findAllStatus() {
        String sql = """
            SELECT
                STATUS_CODE,
                STATUS_NAME,
                STATUS_ORDER,
                STATUS_COLOR
            FROM TMS_PROJ_STATUS
            ORDER BY STATUS_ORDER
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ProjectStatus status = new ProjectStatus();

            status.setStatusCode(rs.getString("STATUS_CODE"));
            status.setStatusName(rs.getString("STATUS_NAME"));
            status.setStatusOrder(rs.getInt("STATUS_ORDER"));
            status.setStatusColor(rs.getString("STATUS_COLOR"));

            return status;
        });
    }

    @Override
    public ProjectStatus findStatusById(String id) {
        return null;
    }

    @Override
    public ProjectStatus findStatusByCode(String statusCode) {
        String sql = """
                SELECT
                    STATUS_CODE,
                    STATUS_NAME,
                    STATUS_ORDER,
                    STATUS_COLOR
                FROM TMS_PROJ_STATUS
                WHERE STATUS_CODE = ?
                """;
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> {
                        ProjectStatus status = new ProjectStatus();
                        status.setStatusCode(rs.getString("STATUS_CODE"));
                        status.setStatusName(rs.getString("STATUS_NAME"));
                        status.setStatusOrder(rs.getInt("STATUS_ORDER"));
                        status.setStatusColor(rs.getString("STATUS_COLOR"));
                        return status;
                    },
                    statusCode
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public ProjectStatus findStatusByName(String name) {
        String sql = """
        SELECT
            STATUS_CODE,
            STATUS_NAME,
            STATUS_ORDER,
            STATUS_COLOR
        FROM TMS_PROJ_STATUS
        WHERE STATUS_NAME = ?
        """;

        try {
           return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> {
                        ProjectStatus status = new ProjectStatus();
                        status.setStatusCode(rs.getString("STATUS_CODE"));
                        status.setStatusName(rs.getString("STATUS_NAME"));
                        status.setStatusOrder(rs.getInt("STATUS_ORDER"));
                        status.setStatusColor(rs.getString("STATUS_COLOR"));
                        return status;
                    },
                    name
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private Project mapProject(ResultSet rs) throws SQLException {

        Project project = new Project();

        project.setProjId(rs.getString("PROJ_ID"));
        project.setProjName(rs.getString("PROJ_NAME"));
        project.setProjDes(rs.getString("PROJ_DES"));

        project.setProjType(rs.getString("PROJ_TYPE"));
        project.setProjMgt(rs.getString("PROJ_MGT"));
        project.setProjOwner(rs.getString("PROJ_OWNER"));

        project.setPriorCode(rs.getString("PRIOR_CODE"));
        project.setStatusCode(rs.getString("STATUS_CODE"));

        if(rs.getDate("START_DATE") != null)
            project.setStartDate(
                    rs.getDate("START_DATE").toLocalDate()
            );

        if(rs.getDate("END_DATE") != null)
            project.setEndDate(
                    rs.getDate("END_DATE").toLocalDate()
            );

        if(rs.getDate("ACTUAL_END_DATE") != null)
            project.setActualEndDate(
                    rs.getDate("ACTUAL_END_DATE").toLocalDate()
            );

        project.setProgress(
                rs.getBigDecimal("PROGRESS")
        );

        project.setIsActive(
                rs.getString("IS_ACTIVE")
        );

        project.setCreatedBy(
                rs.getString("CREATED_BY")
        );

        if(rs.getDate("CREATED_DATE") != null)
            project.setCreatedDate(
                    rs.getDate("CREATED_DATE").toLocalDate()
            );

        project.setUpdatedBy(
                rs.getString("UPDATED_BY")
        );

        if(rs.getDate("UPDATED_DATE") != null)
            project.setUpdatedDate(
                    rs.getDate("UPDATED_DATE").toLocalDate()
            );

        return project;
    }
}
