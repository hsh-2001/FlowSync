package FlowSync.FlowSync.repositories.interfaces;

import FlowSync.FlowSync.dto.DeleteProjectRequest;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;

import java.util.List;

public interface IProjectRepository {
    String save(Project request);
    String update(Project request);
    void delete(DeleteProjectRequest request);
    List<Project> findAll();
    Project findById(String id);

    String createStatus(ProjectStatus request);
    List<ProjectStatus> findAllStatus();
    ProjectStatus findStatusById(String id);
    ProjectStatus findStatusByCode(String statusCode);
    ProjectStatus findStatusByName(String name);
}
