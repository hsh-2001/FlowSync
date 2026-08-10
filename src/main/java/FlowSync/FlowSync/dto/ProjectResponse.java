package FlowSync.FlowSync.dto;

import FlowSync.FlowSync.models.Project;
import FlowSync.FlowSync.models.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse extends Project {
    private String projStatusName;
    private String projStatusColor;
}