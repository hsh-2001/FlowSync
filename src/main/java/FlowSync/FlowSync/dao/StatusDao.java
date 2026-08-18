package FlowSync.FlowSync.dao;

import FlowSync.FlowSync.dto.BasePageResponse;
import FlowSync.FlowSync.dto.ProjectStatusResponse;

import java.util.List;

public interface StatusDao {
    BasePageResponse<ProjectStatusResponse> findAll(
            int page,
            int pageSize
    );
}