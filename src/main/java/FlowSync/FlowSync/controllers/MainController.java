package FlowSync.FlowSync.controllers;

import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.repositories.ProjectRepository;
import FlowSync.FlowSync.services.ProjectService;
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
    public BaseResponse<List<Project>> projects() {
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

    @DeleteMapping("project/{id}")
    public BaseResponse<String> deleteProject(@PathVariable String id) {
        return projectService.deleteProject(id);
    }
}
