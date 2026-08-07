package FlowSync.FlowSync.models;

import lombok.Data;

import java.util.UUID;

@Data
public class ProjectStatus {
    private String statusCode;
    private String statusName;
    private Integer statusOrder;
    private String statusColor;

    public ProjectStatus() {
        this.statusCode = UUID.randomUUID().toString().substring(0, 8);
    }
}