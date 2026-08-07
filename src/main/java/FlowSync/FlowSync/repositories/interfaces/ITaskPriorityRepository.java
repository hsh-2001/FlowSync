package FlowSync.FlowSync.repositories.interfaces;

import FlowSync.FlowSync.dto.CreateTaskPriorityRequest;
import FlowSync.FlowSync.dto.UpdateTaskPriorityRequest;
import FlowSync.FlowSync.models.TaskPriority;

import java.util.List;
import java.util.Optional;

public interface ITaskPriorityRepository {
    List<TaskPriority> findAll();

    Optional<TaskPriority> findById(Long id);

    Optional<TaskPriority> findByCode(String priorCode);

    int create(CreateTaskPriorityRequest request);

    int update(UpdateTaskPriorityRequest request);

    int delete(Long id);
}
