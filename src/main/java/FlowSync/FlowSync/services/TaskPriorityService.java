package FlowSync.FlowSync.services;

import FlowSync.FlowSync.dto.priority.CreateTaskPriorityRequest;
import FlowSync.FlowSync.dto.priority.UpdateTaskPriorityRequest;
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
        return BaseResponse.success(taskPriorityRepository.findByCode(priorCode).orElse(null));
    }

    @Override
    public BaseResponse<Integer> create(CreateTaskPriorityRequest request) {
        TaskPriority taskPriority = taskPriorityRepository.findByCode(request.getPriorCode()).orElse(null);
        if (taskPriority != null) {
            return BaseResponse.failed("Task priority already exists");
        }
        return BaseResponse.success(taskPriorityRepository.create(request));
    }

    @Override
    public BaseResponse<Integer> update(UpdateTaskPriorityRequest request) {
        int result = taskPriorityRepository.update(request);
        return result != 0 ? BaseResponse.success(taskPriorityRepository.update(request))  : BaseResponse.failed("Update failed");
    }

    @Override
    public BaseResponse<Integer> delete(Long id) {
        return BaseResponse.success(taskPriorityRepository.delete(id));
    }
}
