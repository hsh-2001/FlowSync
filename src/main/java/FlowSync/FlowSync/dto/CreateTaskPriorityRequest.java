package FlowSync.FlowSync.dto;

import lombok.Data;

@Data
public class CreateTaskPriorityRequest {
    private String priorCode;
    private String priorDesc;
    private Integer sortOrder;
}
