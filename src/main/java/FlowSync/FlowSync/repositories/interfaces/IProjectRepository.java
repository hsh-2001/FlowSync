package FlowSync.FlowSync.repositories.interfaces;

import FlowSync.FlowSync.models.Project;

import java.util.List;

public interface IProjectRepository {
    String save(Project request);
    String update(Project request);
    void delete(String id);
    List<Project> findAll();
    Project findById(String id);
}
