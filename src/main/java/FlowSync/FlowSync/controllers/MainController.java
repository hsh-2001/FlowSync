package FlowSync.FlowSync.controllers;

import FlowSync.FlowSync.dto.*;
import FlowSync.FlowSync.dto.priority.CreateTaskPriorityRequest;
import FlowSync.FlowSync.dto.priority.UpdateTaskPriorityRequest;
import FlowSync.FlowSync.dto.project.DeleteProjectRequest;
import FlowSync.FlowSync.dto.project.DeleteStatusRequest;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;
import FlowSync.FlowSync.models.TaskPriority;
import FlowSync.FlowSync.services.ProjectService;
import FlowSync.FlowSync.services.TaskPriorityService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class MainController {
    private final ProjectService projectService;
    private final TaskPriorityService taskPriorityService;
    public MainController(ProjectService projectService, TaskPriorityService taskPriorityService) {
        this.projectService = projectService;
        this.taskPriorityService = taskPriorityService;
    }

    @GetMapping
    public String index() {
        return "Hello World";
    }

    @GetMapping("projects")
    public BaseResponse<List<ProjectResponse>> getAllProjects() {
        return projectService.findAll();
    }

    @GetMapping("project/{id}")
    public BaseResponse<ProjectResponse> getProjectById(@PathVariable String id) {
        return projectService.findById(id);
    }


    @PostMapping("project/create")
    public BaseResponse<String> createProject(Authentication authentication, @RequestBody Project request) {
        request.setCreatedBy(authentication.getName());
        return projectService.createProject(request);
    }

    @PostMapping("project/update")
    public BaseResponse<String> updateProject(Authentication authentication, @RequestBody Project request) {
        request.setUpdatedBy(authentication.getName());
        return projectService.updateProject(request);
    }

    @PostMapping("project/delete")
    public BaseResponse<String> deleteProject(@RequestBody DeleteProjectRequest request) {
        return projectService.deleteProject(request);
    }

    @GetMapping("project/status")
    public BaseResponse<List<ProjectStatus>> getAllProjectStatuses() {
        return projectService.findAllProjectStatus();
    }

    @PostMapping("project/status/create")
    public BaseResponse<String> createProjectStatus(@RequestBody ProjectStatus projectStatus) {
        return projectService.createProjectStatus(projectStatus);
    }

    @PostMapping("project/status/update")
    public BaseResponse<String> updateProjectStatus(@RequestBody ProjectStatus projectStatus) {
        return projectService.updateProjectStatus(projectStatus);
    }

    @PostMapping("/project/status/delete")
    public BaseResponse<String> deleteProjectStatus(@RequestBody DeleteStatusRequest request) {
        return projectService.deleteProjectStatus(request);
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
}
