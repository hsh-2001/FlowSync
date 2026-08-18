package FlowSync.FlowSync.controllers;

import FlowSync.FlowSync.dao.ProjectResDao;
import FlowSync.FlowSync.dto.*;
import FlowSync.FlowSync.dto.priority.CreateTaskPriorityRequest;
import FlowSync.FlowSync.dto.priority.UpdateTaskPriorityRequest;
import FlowSync.FlowSync.dto.project.DeleteProjectRequest;
import FlowSync.FlowSync.dto.project.DeleteStatusRequest;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;
import FlowSync.FlowSync.models.TaskPriority;
import FlowSync.FlowSync.services.NewProjectService;
import FlowSync.FlowSync.services.NewTaskPriorityService;
import FlowSync.FlowSync.services.ProjectService;
import FlowSync.FlowSync.services.TaskPriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class MainController {
    private final NewProjectService newProjectService;
    private final NewTaskPriorityService taskPriorityService;

    @GetMapping
    public String index() {
        return "Hello 0022";
    }

    @GetMapping("projects")
    public BaseResponse<List<ProjectResponse>> getAllProjects() {
        return newProjectService.findAll();
    }

    @GetMapping("projects-procedure")
    public BasePageResponse<ProjectResDao> findAllProjectsProcedure(
            @ModelAttribute PageRequestDto request
    ) {
        return newProjectService.findAll(request);
    }

    @GetMapping("project/{id}")
    public BaseResponse<ProjectResponse> getProjectById(@PathVariable String id) {
        return newProjectService.findById(id);
    }


    @PostMapping("project/create")
    public BaseResponse<String> createProject(Authentication authentication, @RequestBody Project request) {
        request.setCreatedBy(authentication.getName());
        return newProjectService.createProject(request);
    }

    @PostMapping("project/update")
    public BaseResponse<String> updateProject(Authentication authentication, @RequestBody Project request) {
        request.setUpdatedBy(authentication.getName());
        return newProjectService.updateProject(request);
    }

    @PostMapping("project/delete")
    public BaseResponse<String> deleteProject(@RequestBody DeleteProjectRequest request) {
        return newProjectService.deleteProject(request);
    }

    @GetMapping("project/status")
    public BaseResponse<List<ProjectStatus>> getAllProjectStatuses() {
        return newProjectService.findAllProjectStatus();
    }

    @PostMapping("project/status/create")
    public BaseResponse<String> createProjectStatus(@RequestBody ProjectStatus projectStatus) {
        return newProjectService.createProjectStatus(projectStatus);
    }

    @PostMapping("project/status/update")
    public BaseResponse<String> updateProjectStatus(@RequestBody ProjectStatus projectStatus) {
        return newProjectService.updateProjectStatus(projectStatus);
    }

    @PostMapping("/project/status/delete")
    public BaseResponse<String> deleteProjectStatus(@RequestBody DeleteStatusRequest request) {
        return newProjectService.deleteProjectStatus(request);
    }

    @GetMapping("project/priority")
    public BaseResponse<List<TaskPriority>> projectPriority() {
        return taskPriorityService.findAll();
    }

    @PostMapping("/project/priority/create")
    public BaseResponse<Integer> createPriority(@RequestBody CreateTaskPriorityRequest request) {
        return taskPriorityService.create(request);
    }

    @PostMapping("/project/priority/update")
    public BaseResponse<Integer> updatePriority(@RequestBody UpdateTaskPriorityRequest request) {
        return taskPriorityService.update(request);
    }

    @GetMapping("/project/dashboard")
    public BaseResponse<ProjectDashboardResponseDto> getAllProjectDashboard() {
        return newProjectService.getDashboardSummary();
    }

    @GetMapping("/project/all-status")
    public BasePageResponse<ProjectStatusResponse> getAllProjectStatus(@ModelAttribute PageRequestDto request) {
        return newProjectService.findAllStatus(request);
    }
}
