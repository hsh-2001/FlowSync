package FlowSync.FlowSync.dto.project;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CreateProjectRequest {

    private String projName;

    private String projDes;

    private String projType;

    private String projMgt;

    private String projOwner;

    private String priorCode;

    private String statusCode;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal progress;

    private String isActive;
    private String createBy;
    private String updateBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}