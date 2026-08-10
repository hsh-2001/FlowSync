package FlowSync.FlowSync.dto.priority;

import lombok.Data;

@Data
public class CreateTaskPriorityRequest {
    private String priorCode;
    private String priorDesc;
    private Integer sortOrder;
    private Integer isActive;
}
