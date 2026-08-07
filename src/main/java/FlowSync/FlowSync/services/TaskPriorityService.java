package FlowSync.FlowSync.services;

import FlowSync.FlowSync.dto.CreateTaskPriorityRequest;
import FlowSync.FlowSync.dto.UpdateTaskPriorityRequest;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.TaskPriority;
import FlowSync.FlowSync.repositories.TaskPriorityRepository;
import FlowSync.FlowSync.services.interfaces.ITaskPriorityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TaskPriorityService implements ITaskPriorityService {
    private final TaskPriorityRepository taskPriorityRepository;
    public TaskPriorityService(TaskPriorityRepository taskPriorityRepository) {
        this.taskPriorityRepository = taskPriorityRepository;
    }

    @Override
    public BaseResponse<List<TaskPriority>> findAll() {
        return BaseResponse.success(taskPriorityRepository.findAll());
    }

    @Override
    public BaseResponse<TaskPriority> findById(Long id) {

        Optional<TaskPriority> taskPriority = taskPriorityRepository.findById(id);

        if (taskPriority.isEmpty()) {
            return BaseResponse.failed("Task priority not found");
        }

        return BaseResponse.success(taskPriority.get());
    }

    @Override
    public BaseResponse<TaskPriority> findByCode(String priorCode) {
        return null;
    }

    @Override
    public BaseResponse<Integer> create(CreateTaskPriorityRequest request) {
        return BaseResponse.success(taskPriorityRepository.create(request));
    }

    @Override
    public BaseResponse<Integer> update(UpdateTaskPriorityRequest request) {
        return BaseResponse.success(taskPriorityRepository.update(request));
    }

    @Override
    public BaseResponse<Integer> delete(Long id) {
        return BaseResponse.success(taskPriorityRepository.delete(id));
    }
}
