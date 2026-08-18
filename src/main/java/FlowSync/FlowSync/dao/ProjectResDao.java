package FlowSync.FlowSync.dao;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResDao {
    private Integer rowNumber;
    private String projStatusName;
    private String projStatusColor;
    private LocalDate actualEndDate;
    private String createdBy;
    private LocalDate createdDate;
    private LocalDate endDate;
    private String priorCode;
    private String projDes;
    private String projId;
    private String projMgt;
    private String projName;
    private String projOwner;
    private String projType;
    private LocalDate startDate;
    private String statusCode;
//    private String updatedBy;
//    private LocalDate updatedDate;
}
