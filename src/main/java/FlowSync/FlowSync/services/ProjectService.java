package FlowSync.FlowSync.services;

import FlowSync.FlowSync.dto.DeleteProjectRequest;
import FlowSync.FlowSync.enums.ErrorCode;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;
import FlowSync.FlowSync.repositories.interfaces.IProjectRepository;
import FlowSync.FlowSync.services.interfaces.IProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements IProjectService {
    private final IProjectRepository projectRepository;

    public ProjectService(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public BaseResponse<String> createProject(Project request) {
        if (getProjectStatusByCode(request.getStatusCode()) == null) {
            return BaseResponse.failed("The status does not exist", ErrorCode.STATUS_NOT_FOUND.getCode());
        }
        return BaseResponse.success(projectRepository.save(request));
    }

    @Override
    public BaseResponse<String> updateProject(Project request) {
        return BaseResponse.success(projectRepository.update(request));
    }

    @Override
    public BaseResponse<List<Project>> findAll() {
        return BaseResponse.success(projectRepository.findAll());
    }

    @Override
    public BaseResponse<Project> findById(String id) {
        return BaseResponse.success(projectRepository.findById(id));
    }

    @Override
    public BaseResponse<String> deleteProject(DeleteProjectRequest request) {
        if (request.getId() == null) {
            return BaseResponse.failed("The id is required");
        }
        projectRepository.delete(request);
        return BaseResponse.success("Delete Success");
    }

    @Override
    public BaseResponse<String> createProjectStatus(ProjectStatus projectStatus) {
        if (getProjectStatusByName(projectStatus.getStatusName()) != null) {
            return BaseResponse.failed("Project Status Already Exist");
        }
        return BaseResponse.success(projectRepository.createStatus(projectStatus));
    }

    @Override
    public BaseResponse<String> updateProjectStatus(ProjectStatus projectStatus) {
        return null;
    }

    @Override
    public BaseResponse<List<ProjectStatus>> findAllProjectStatus() {
        return BaseResponse.success(projectRepository.findAllStatus());
    }

    private ProjectStatus getProjectStatusByName(String name) {
        return projectRepository.findStatusByName(name);
    }

    private  ProjectStatus getProjectStatusByCode(String statusCode) {
        return projectRepository.findStatusByCode(statusCode);
    }
}
