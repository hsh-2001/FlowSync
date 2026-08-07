package FlowSync.FlowSync.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

    private String projId;

    private String projName;

    private String projDes;

    private String projType;

    private String projMgt;

    private String projOwner;

    private String priorCode;

    private String statusCode;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate actualEndDate;

    private BigDecimal progress;

    private String isActive;

    private String createdBy;

    private LocalDateTime createdDate;

    private String updatedBy;

    private LocalDateTime updatedDate;
}