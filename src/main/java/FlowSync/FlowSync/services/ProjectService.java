package FlowSync.FlowSync.services;

import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.Project;
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
    public BaseResponse<String> createProject(Project project) {
        return BaseResponse.success("Create Success" ,projectRepository.save(project));
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
    public BaseResponse<String> deleteProject(String id) {
        projectRepository.delete(id);
        return BaseResponse.success("Delete Success");
    }
}
