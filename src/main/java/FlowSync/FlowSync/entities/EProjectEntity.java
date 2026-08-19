package FlowSync.FlowSync.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "TMS_TBL_PROJECT")
public class EProjectEntity {

    @Id
    @Column(name = "PROJ_ID", length = 100)
    private String projId;

    @Column(name = "PROJ_NAME", nullable = false, length = 200)
    private String projName;

    @Lob
    @Column(name = "PROJ_DES")
    private String projDes;

    @Column(name = "PROJ_TYPE", length = 30)
    private String projType;

    @Column(name = "PROJ_MGT", length = 30)
    private String projMgt;

    @Column(name = "PROJ_OWNER", length = 30)
    private String projOwner;

    @Column(name = "PRIOR_CODE", length = 10)
    private String priorCode;

    @Column(name = "STATUS_CODE", length = 20)
    private String statusCode;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "ACTUAL_END_DATE")
    private LocalDate actualEndDate;

    @Column(name = "PROGRESS", precision = 5, scale = 2)
    private BigDecimal progress;

    @Column(name = "IS_ACTIVE", length = 1)
    private String isActive;

    @Column(name = "CREATED_BY", length = 30)
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "UPDATED_BY", length = 30)
    private String updatedBy;

    @Column(name = "UPDATED_DATE")
    private LocalDate updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "STATUS_CODE",
        referencedColumnName = "STATUS_CODE",
        insertable = false,
        updatable = false
    )
    private EProjectStatusEntity status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "PRIOR_CODE",
        referencedColumnName = "PRIOR_CODE",
        insertable = false,
        updatable = false
    )
    private ETaskPriorityEntity priority;
}