package FlowSync.FlowSync.services;

import FlowSync.FlowSync.Projection.ProjectDashboardProjection;
import FlowSync.FlowSync.dto.ProjectDashboardResponseDto;
import FlowSync.FlowSync.dto.ProjectResponse;
import FlowSync.FlowSync.dto.project.DeleteProjectRequest;
import FlowSync.FlowSync.dto.project.DeleteStatusRequest;
import FlowSync.FlowSync.entities.EProjectEntity;
import FlowSync.FlowSync.entities.EProjectStatusEntity;
import FlowSync.FlowSync.enums.ErrorCode;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;
import FlowSync.FlowSync.repositories.NewProjectRepository;
import FlowSync.FlowSync.repositories.StatusRepository;
import FlowSync.FlowSync.services.interfaces.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class NewProjectService implements IProjectService {

    private final NewProjectRepository newProjectRepository;
    private final StatusRepository statusRepository;

    @Override
    public BaseResponse<String> createProject(Project project) {

        if (!statusRepository.existsById(project.getStatusCode())) {
            return BaseResponse.failed("Project status not found");
        }

        EProjectEntity entity = new EProjectEntity();

        entity.setProjId(randomProjectId());
        entity.setProjName(project.getProjName());
        entity.setProjDes(project.getProjDes());
        entity.setProjType(project.getProjType());
        entity.setProjMgt(project.getProjMgt());
        entity.setProjOwner(project.getProjOwner());
        entity.setPriorCode(project.getPriorCode());
        entity.setStatusCode(project.getStatusCode());
        entity.setStartDate(project.getStartDate());
        entity.setEndDate(project.getEndDate());
        entity.setActualEndDate(project.getActualEndDate());
        entity.setProgress(project.getProgress());
        entity.setIsActive(project.getIsActive());
        entity.setCreatedBy(project.getCreatedBy());
        entity.setCreatedDate(LocalDate.now());
        entity.setUpdatedBy(project.getUpdatedBy());
        entity.setUpdatedDate(LocalDate.now());

        newProjectRepository.save(entity);

        return BaseResponse.success("Project created successfully");
    }

    @Override
    public BaseResponse<String> updateProject(Project project) {

        EProjectEntity entity = newProjectRepository
                .findByProjId(project.getProjId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!statusRepository.existsById(project.getStatusCode())) {
            return BaseResponse.failed("Project status not found");
        }

        entity.setProjName(project.getProjName());
        entity.setProjDes(project.getProjDes());
        entity.setProjType(project.getProjType());
        entity.setProjMgt(project.getProjMgt());
        entity.setProjOwner(project.getProjOwner());
        entity.setPriorCode(project.getPriorCode());
        entity.setStatusCode(project.getStatusCode());
        entity.setStartDate(project.getStartDate());
        entity.setEndDate(project.getEndDate());
        entity.setActualEndDate(project.getActualEndDate());
        entity.setProgress(project.getProgress());
        entity.setIsActive(project.getIsActive());

        entity.setUpdatedBy(project.getUpdatedBy());
        entity.setUpdatedDate(LocalDate.now());

        newProjectRepository.save(entity);

        return BaseResponse.success("Project updated successfully");
    }

    @Override
    public BaseResponse<List<ProjectResponse>> findAll() {

        List<ProjectResponse> result = newProjectRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

        return BaseResponse.success(result);
    }

    @Override
    public BaseResponse<ProjectResponse> findById(String id) {

        EProjectEntity project = newProjectRepository
                .findByProjId(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        ProjectResponse response = mapToResponse(project);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<String> deleteProject(DeleteProjectRequest request) {

        EProjectEntity project = newProjectRepository
                .findByProjId(request.getId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        newProjectRepository.delete(project);

        return BaseResponse.success("Project deleted successfully");
    }

    @Override
    public BaseResponse<String> createProjectStatus(ProjectStatus projectStatus) {

        String statusCode = projectStatus.getStatusName()
                .replaceAll("\\s+", "")
                .toUpperCase(Locale.ROOT);

        statusCode = statusCode.length() > 5
                ? statusCode.substring(0, 5)
                : "0".repeat(5 - statusCode.length()) + statusCode;

        if (statusRepository.existsById(statusCode)) {
            return BaseResponse.failed("Status code already exists");
        }

        EProjectStatusEntity entity = new EProjectStatusEntity();

        entity.setStatusCode(statusCode);
        entity.setStatusName(projectStatus.getStatusName());
        entity.setStatusOrder(projectStatus.getStatusOrder());
        entity.setStatusColor(projectStatus.getStatusColor());

        statusRepository.save(entity);

        return BaseResponse.success("Project status created successfully");
    }

    @Override
    public BaseResponse<String> updateProjectStatus(ProjectStatus projectStatus) {

        EProjectStatusEntity entity = statusRepository
                .findById(projectStatus.getStatusCode())
                .orElseThrow(() -> new RuntimeException("Project status not found"));

        entity.setStatusName(projectStatus.getStatusName());
        entity.setStatusOrder(projectStatus.getStatusOrder());
        entity.setStatusColor(projectStatus.getStatusColor());

        statusRepository.save(entity);

        return BaseResponse.success("Project status updated successfully");
    }

    @Override
    public BaseResponse<List<ProjectStatus>> findAllProjectStatus() {

        List<ProjectStatus> result = statusRepository
                .findAll()
                .stream()
                .map(this::mapToModel)
                .toList();

        return BaseResponse.success(result);
    }

    @Override
    public BaseResponse<String> deleteProjectStatus(DeleteStatusRequest request) {

        EProjectStatusEntity entity = statusRepository
                .findById(request.getStatusCode())
                .orElseThrow(() -> new RuntimeException("Project status not found"));

        boolean isUsed = newProjectRepository
                .existsByStatusCode(request.getStatusCode());

        if (isUsed) {
            return BaseResponse.failed(
                    "Status code already used",
                    ErrorCode.STATUS_ALREADY_USED.getCode()
            );
        }

        statusRepository.delete(entity);

        return BaseResponse.success("Project status deleted successfully");
    }

    private ProjectStatus mapToModel(EProjectStatusEntity entity) {

        ProjectStatus status = new ProjectStatus();

        status.setStatusCode(entity.getStatusCode());
        status.setStatusName(entity.getStatusName());
        status.setStatusOrder(entity.getStatusOrder());
        status.setStatusColor(entity.getStatusColor());

        return status;
    }

    public BaseResponse<ProjectDashboardResponseDto> getDashboardSummary() {

        ProjectDashboardProjection projectDashboardProjection = newProjectRepository.getDashboardSummary();

        ProjectDashboardResponseDto projectDashboardResponseDto = new ProjectDashboardResponseDto();

        projectDashboardResponseDto.setTotalProjects(
                projectDashboardProjection.getTotalProjects()
        );

        projectDashboardResponseDto.setTotalStatuses(
                projectDashboardProjection.getTotalStatuses()
        );

        projectDashboardResponseDto.setTotalPriors(
                projectDashboardProjection.getTotalPriors()
        );

        return BaseResponse.success(projectDashboardResponseDto);
    }

    private ProjectResponse mapToResponse(EProjectEntity project) {

        ProjectResponse response = new ProjectResponse();

        response.setProjId(project.getProjId());
        response.setProjName(project.getProjName());
        response.setProjDes(project.getProjDes());
        response.setProjType(project.getProjType());
        response.setProjMgt(project.getProjMgt());
        response.setProjOwner(project.getProjOwner());

        response.setStartDate(project.getStartDate());
        response.setEndDate(project.getEndDate());
        response.setActualEndDate(project.getActualEndDate());
        response.setProgress(project.getProgress());
        response.setIsActive(project.getIsActive());
        response.setCreatedBy(project.getCreatedBy());
        response.setCreatedDate(project.getCreatedDate());
        response.setUpdatedBy(project.getUpdatedBy());
        response.setUpdatedDate(project.getUpdatedDate());
        response.setPriorCode(project.getPriority().getPriorCode());
        response.setStatusCode(project.getStatus().getStatusCode());
        response.setProjStatusName(project.getStatus().getStatusName());
        response.setProjStatusColor(project.getStatus().getStatusColor());

        return response;
    }

    private String randomProjectId() {

        Random random = new Random();
        char letter = (char) ('A' + random.nextInt(26));
        int number = random.nextInt(1000);

        return String.format("%c%03d", letter, number);
    }
}