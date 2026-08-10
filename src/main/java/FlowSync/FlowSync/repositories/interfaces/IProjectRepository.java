package FlowSync.FlowSync.repositories.interfaces;

import FlowSync.FlowSync.dto.project.DeleteProjectRequest;
import FlowSync.FlowSync.dto.project.DeleteStatusRequest;
import FlowSync.FlowSync.dto.ProjectResponse;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;

import java.util.List;

public interface IProjectRepository {
    String save(Project request);
    String update(Project request);
    void delete(DeleteProjectRequest request);
    List<ProjectResponse> findAll();
    ProjectResponse findById(String id);

    String createStatus(ProjectStatus request);
    List<ProjectStatus> findAllStatus();
    ProjectStatus findStatusById(String id);
    ProjectStatus findStatusByCode(String statusCode);
    ProjectStatus findStatusByName(String name);
    String updateStatus(ProjectStatus projectStatus);
    Integer deleteStatus(DeleteStatusRequest request);
}
