package FlowSync.FlowSync.services.interfaces;

import FlowSync.FlowSync.dto.priority.CreateTaskPriorityRequest;
import FlowSync.FlowSync.dto.priority.UpdateTaskPriorityRequest;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.TaskPriority;

import java.util.List;

public interface ITaskPriorityService {
    BaseResponse<List<TaskPriority>> findAll();

    BaseResponse<TaskPriority> findById(Long id);

    BaseResponse<TaskPriority> findByCode(String priorCode);

    BaseResponse<Integer> create(CreateTaskPriorityRequest taskPriority);

    BaseResponse<Integer> update(UpdateTaskPriorityRequest taskPriority);

    BaseResponse<Integer> delete(Long id);
}
