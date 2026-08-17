package FlowSync.FlowSync.models;

import lombok.*;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Data
@AllArgsConstructor
public class ProjectStatus {
    private String statusCode;
    private String statusName;
    private Integer statusOrder;
    private String statusColor;
    private Optional<Integer> totalCount;

    public ProjectStatus() {
        this.statusCode = UUID.randomUUID().toString().substring(0, 8);
    }
}