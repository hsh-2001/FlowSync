package FlowSync.FlowSync.controllers;

import FlowSync.FlowSync.dto.DeleteProjectRequest;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;
import FlowSync.FlowSync.services.ProjectService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {
    private final ProjectService projectService;
    public MainController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String index() {
        return "Hello World";
    }

    @GetMapping("projects")
    public BaseResponse<List<Project>> projects(Authentication authentication) {
        String username = authentication.getName();
        System.out.println(username);
        return projectService.findAll();
    }

    @GetMapping("project/{id}")
    public BaseResponse<Project> project(@PathVariable String id) {
        return projectService.findById(id);
    }


    @PostMapping("project")
    public BaseResponse<String> project(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PostMapping("project/delete")
    public BaseResponse<String> deleteProject(@RequestBody DeleteProjectRequest request) {
        return projectService.deleteProject(request);
    }

    @GetMapping("project/status")
    public BaseResponse<List<ProjectStatus>> projectStatus() {
        return projectService.findAllProjectStatus();
    }

    @PostMapping("project/status")
    public BaseResponse<String> projectStatus(@RequestBody ProjectStatus projectStatus) {
        return projectService.createProjectStatus(projectStatus);
    }
}
