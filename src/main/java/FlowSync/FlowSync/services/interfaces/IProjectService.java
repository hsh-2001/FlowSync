package FlowSync.FlowSync.services.interfaces;

import FlowSync.FlowSync.dto.DeleteProjectRequest;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;

import java.util.List;

public interface IProjectService {
    BaseResponse<String> createProject(Project project);
    BaseResponse<List<Project>> findAll();
    BaseResponse<Project> findById(String id);
    BaseResponse<String> deleteProject(DeleteProjectRequest request);

    BaseResponse<String> createProjectStatus(ProjectStatus projectStatus);
    BaseResponse<String> updateProjectStatus(ProjectStatus projectStatus);
    BaseResponse<List<ProjectStatus>> findAllProjectStatus();
}
