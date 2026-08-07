package FlowSync.FlowSync.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPriority {
    private Long id;
    private String priorCode;
    private String priorDesc;
    private Integer sortOrder;
    private Integer isActive;
    private LocalDateTime createdDt;
}