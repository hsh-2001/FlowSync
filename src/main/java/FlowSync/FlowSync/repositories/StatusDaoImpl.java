package FlowSync.FlowSync.repositories;

import FlowSync.FlowSync.dao.BaseJdbcDao;
import FlowSync.FlowSync.dao.StatusResDao;
import FlowSync.FlowSync.dto.BasePageResponse;
import FlowSync.FlowSync.dto.PageRequestDto;
import FlowSync.FlowSync.dto.ProjectStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StatusDaoImpl implements StatusResDao {

    private final BaseJdbcDao baseJdbcDao;

    @Override
    @Transactional(readOnly = true)
    public BasePageResponse<ProjectStatusResponse> findAll(PageRequestDto request) {

        Map<String, Object> result = baseJdbcDao.executeProcedure(
                "get_all_proj_statuses",
                Map.of(
                        "P_PAGE", request.getPage(),
                        "P_PAGE_SIZE", request.getPageSize()
                ),
                new SqlParameter("P_PAGE", Types.NUMERIC),
                new SqlParameter("P_PAGE_SIZE", Types.NUMERIC),
                new SqlOutParameter("P_TOTAL_COUNT", Types.NUMERIC),
                new SqlOutParameter("P_TOTAL_PAGE", Types.NUMERIC),
//                new SqlOutParameter("P_RESULT", Types.REF_CURSOR)
                new SqlOutParameter("P_RESULT",
                        Types.REF_CURSOR,
                        (rs, rowNum) -> ProjectStatusResponse.builder()
                                .statusCode(rs.getString("status_code"))
                                .statusName(rs.getString("status_name"))
                                .statusColor(rs.getString("status_color"))
                                .statusOrder(rs.getInt("status_order"))
                                .test(rs.getInt("test"))
                                .build()
                )
        );

        long totalCount = ((Number) result.get("P_TOTAL_COUNT")).longValue();
        int totalPage = ((Number) result.get("P_TOTAL_PAGE")).intValue();

        @SuppressWarnings("unchecked")
        List<ProjectStatusResponse> data = (List<ProjectStatusResponse>) result.get("P_RESULT");

        return BasePageResponse.success(
                data,
                totalCount,
                totalPage,
                request.getPage(),
                request.getPageSize()
        );
    }
}