package FlowSync.FlowSync.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateTaskPriorityRequest {
    @NonNull
    private Long id;
    private String priorCode;
    private String priorDesc;
    private Integer sortOrder;
}
