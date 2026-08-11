package FlowSync.FlowSync.services;

import FlowSync.FlowSync.dto.priority.CreateTaskPriorityRequest;
import FlowSync.FlowSync.dto.priority.UpdateTaskPriorityRequest;
import FlowSync.FlowSync.entities.ETaskPriorityEntity;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.models.TaskPriority;
import FlowSync.FlowSync.repositories.NewTaskPriorityRepository;
import FlowSync.FlowSync.services.interfaces.ITaskPriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewTaskPriorityService implements ITaskPriorityService {

    private final NewTaskPriorityRepository newTaskPriorityRepository;

    @Override
    public BaseResponse<List<TaskPriority>> findAll() {

        List<ETaskPriorityEntity> entities =
                newTaskPriorityRepository.findAll();

        List<TaskPriority> result = entities.stream()
                .map(this::mapToModel)
                .toList();

        return BaseResponse.success(result);
    }

    @Override
    public BaseResponse<TaskPriority> findById(Long id) {

        ETaskPriorityEntity entity =
                newTaskPriorityRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Task priority not found"));

        return BaseResponse.success(mapToModel(entity));
    }

    @Override
    public BaseResponse<TaskPriority> findByCode(String priorCode) {

        ETaskPriorityEntity entity =
                newTaskPriorityRepository.findByPriorCode(priorCode)
                        .orElseThrow(() ->
                                new RuntimeException("Task priority not found"));

        return BaseResponse.success(mapToModel(entity));
    }

    @Override
    public BaseResponse<Integer> create(CreateTaskPriorityRequest request) {

        ETaskPriorityEntity entity = new ETaskPriorityEntity();

        entity.setPriorCode(request.getPriorCode());
        entity.setPriorDesc(request.getPriorDesc());
        entity.setSortOrder(request.getSortOrder());
        entity.setIsActive(request.getIsActive());
        entity.setCreatedDt(LocalDateTime.now());

        ETaskPriorityEntity saved = newTaskPriorityRepository.save(entity);

        return BaseResponse.success(saved.getId().intValue());
    }

    @Override
    public BaseResponse<Integer> update(UpdateTaskPriorityRequest request) {

        ETaskPriorityEntity entity = newTaskPriorityRepository.findById(request.getId())
                        .orElseThrow(() -> new RuntimeException("Task priority not found"));

        entity.setPriorCode(request.getPriorCode());
        entity.setPriorDesc(request.getPriorDesc());
        entity.setIsActive(request.getIsActive());

        ETaskPriorityEntity updated = newTaskPriorityRepository.save(entity);

        return BaseResponse.success(updated.getId().intValue());
    }

    @Override
    public BaseResponse<Integer> delete(Long id) {

        ETaskPriorityEntity entity = newTaskPriorityRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Task priority not found"));

        newTaskPriorityRepository.delete(entity);

        return BaseResponse.success(id.intValue());
    }

    private TaskPriority mapToModel(ETaskPriorityEntity entity) {

        TaskPriority model = new TaskPriority();

        model.setId(entity.getId());
        model.setIsActive(entity.getIsActive());
        model.setPriorDesc(entity.getPriorDesc());
        model.setPriorCode(entity.getPriorCode());
        model.setCreatedDt(entity.getCreatedDt());

        return model;
    }
}