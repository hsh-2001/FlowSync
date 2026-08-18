package FlowSync.FlowSync.repositories;

import FlowSync.FlowSync.dao.BaseJdbcDao;
import FlowSync.FlowSync.dao.ProjectResDao;
import FlowSync.FlowSync.dto.BasePageResponse;
import FlowSync.FlowSync.dto.PageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProjectDaoImpl {
    private final BaseJdbcDao baseJdbcDao;

    public BasePageResponse<ProjectResDao> findAll(PageRequestDto request) {
        Map<String, Object> result = baseJdbcDao.executeProcedure(
                "GET_ALL_PROJECTS",
                Map.of("P_PAGE", request.getPage(),
                        "P_PAGE_SIZE", request.getPageSize()
                ),
                new SqlParameter("P_PAGE", Types.NUMERIC),
                new SqlParameter("P_PAGE_SIZE", Types.NUMERIC),
                new SqlOutParameter("P_TOTAL_COUNT", Types.NUMERIC),
                new SqlOutParameter("P_TOTAL_PAGE", Types.NUMERIC),
                new SqlOutParameter("P_RESULT", Types.REF_CURSOR,
                        (rs, rowNum) -> ProjectResDao.builder()
                                .rowNumber(rs.getInt("ROW_NUMBER"))
                                .projStatusName(rs.getString("STATUS_NAME"))
                                .projStatusColor(rs.getString("STATUS_COLOR"))
                                .actualEndDate(rs.getObject("ACTUAL_END_DATE", LocalDate.class))
                                .createdBy(rs.getString("CREATED_BY"))
                                .createdDate(rs.getObject("CREATED_DATE", LocalDate.class))
                                .endDate(rs.getObject("END_DATE", LocalDate.class))
                                .priorCode(rs.getString("PRIOR_CODE"))
                                .projDes(rs.getString("PROJ_DES"))
                                .projId(rs.getString("PROJ_ID"))
                                .projMgt(rs.getString("PROJ_MGT"))
                                .projName(rs.getString("PROJ_NAME"))
                                .projType(rs.getString("PROJ_TYPE"))
                                .statusCode(rs.getString("STATUS_CODE"))
                                .build()
                )
        );
        long totalCount = ((Number) result.get("P_TOTAL_COUNT")).longValue();
        int totalPage = ((Number) result.get("P_TOTAL_PAGE")).intValue();

        @SuppressWarnings("unchecked")
        List<ProjectResDao> data = (List<ProjectResDao>) result.get("P_RESULT");
        return BasePageResponse.success(
                data,
                totalCount,
                totalPage,
                request.getPage(),
                request.getPageSize()
        );
    }
}
