package FlowSync.FlowSync.services.interfaces;

import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;

import java.util.List;

public interface IProjectService {
    BaseResponse<String> createProject(Project project);
    BaseResponse<List<Project>> findAll();
    BaseResponse<Project> findById(String id);
    BaseResponse<String> deleteProject(String id);
}
