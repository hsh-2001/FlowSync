package FlowSync.FlowSync.services.interfaces;

import FlowSync.FlowSync.dao.ProjectResDao;
import FlowSync.FlowSync.dto.BasePageResponse;
import FlowSync.FlowSync.dto.PageRequestDto;
import FlowSync.FlowSync.dto.ProjectStatusResponse;
import FlowSync.FlowSync.dto.project.DeleteProjectRequest;
import FlowSync.FlowSync.dto.project.DeleteStatusRequest;
import FlowSync.FlowSync.dto.ProjectResponse;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;

import java.util.List;

public interface IProjectService {
    BaseResponse<String> createProject(Project project);
    BaseResponse<String> updateProject(Project project);
    BaseResponse<List<ProjectResponse>> findAll();

    BasePageResponse<ProjectResDao> findAll(PageRequestDto pageRequest);
    BaseResponse<ProjectResponse> findById(String id);
    BaseResponse<String> deleteProject(DeleteProjectRequest request);

    BaseResponse<String> createProjectStatus(ProjectStatus projectStatus);
    BaseResponse<String> updateProjectStatus(ProjectStatus projectStatus);
    BaseResponse<List<ProjectStatus>> findAllProjectStatus();
    BaseResponse<String> deleteProjectStatus(DeleteStatusRequest request);

    BasePageResponse<ProjectStatusResponse> findAllStatus(PageRequestDto request);
}
