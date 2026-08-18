package FlowSync.FlowSync.services;

import FlowSync.FlowSync.dto.BasePageResponse;
import FlowSync.FlowSync.dto.ProjectDashboardResponseDto;
import FlowSync.FlowSync.dto.ProjectStatusResponse;
import FlowSync.FlowSync.dto.project.DeleteProjectRequest;
import FlowSync.FlowSync.dto.project.DeleteStatusRequest;
import FlowSync.FlowSync.dto.ProjectResponse;
import FlowSync.FlowSync.enums.ErrorCode;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;
import FlowSync.FlowSync.repositories.interfaces.IProjectRepository;
import FlowSync.FlowSync.services.interfaces.IProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Random;

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
        request.setProjId(randomProjectId());
        return BaseResponse.success(projectRepository.save(request));
    }

    @Override
    public BaseResponse<String> updateProject(Project request) {
        return BaseResponse.success(projectRepository.update(request));
    }

    @Override
    public BaseResponse<List<ProjectResponse>> findAll() {
        return BaseResponse.success(projectRepository.findAll());
    }

    @Override
    public BaseResponse<ProjectResponse> findById(String id) {
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
        String statusCode = projectStatus.getStatusName()
                .replaceAll("\\s+", "")
                .toUpperCase(Locale.ROOT);
        statusCode = statusCode.length() > 5
                ? statusCode.substring(0, 5)
                : "0".repeat(5 - statusCode.length()) + statusCode;
        projectStatus.setStatusCode(statusCode);
        return BaseResponse.success(projectRepository.createStatus(projectStatus));
    }

    @Override
    public BaseResponse<String> updateProjectStatus(ProjectStatus projectStatus) {
        return BaseResponse.success(projectRepository.updateStatus(projectStatus));
    }

    @Override
    public BaseResponse<List<ProjectStatus>> findAllProjectStatus() {
        return BaseResponse.success(projectRepository.findAllStatus());
    }

    @Override
    public BaseResponse<String> deleteProjectStatus(DeleteStatusRequest request) {
        int result = projectRepository.deleteStatus(request);
        return  result > 0 ? BaseResponse.success("Delete success")
                : BaseResponse.failed(ErrorCode.STATUS_ALREADY_USED.toString(),ErrorCode.STATUS_ALREADY_USED.getCode());
    }

    @Override
    public BasePageResponse<ProjectStatusResponse> findAllStatus(int page, int pageSize) {
        return null;
    }

    private  ProjectStatus getProjectStatusByCode(String statusCode) {
        return projectRepository.findStatusByCode(statusCode);
    }

    private String randomProjectId() {
        Random random = new Random();
        char letter = (char) ('A' + random.nextInt(26));
        int number = random.nextInt(1000);
        return String.format("%c%03d", letter, number);
    }
}
