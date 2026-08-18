package FlowSync.FlowSync.dao;

import FlowSync.FlowSync.dto.BasePageResponse;
import FlowSync.FlowSync.dto.PageRequestDto;
import FlowSync.FlowSync.dto.ProjectStatusResponse;

public interface StatusResDao {
    BasePageResponse<ProjectStatusResponse> findAll(PageRequestDto request);
}