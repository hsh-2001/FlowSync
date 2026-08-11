package FlowSync.FlowSync.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TMS_TASK_PRIOR")
public class ETaskPriorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRIOR_CODE", nullable = false, length = 10)
    private String priorCode;

    @Column(name = "PRIOR_DESC", nullable = false, length = 100)
    private String priorDesc;

    @Column(name = "SORT_ORDER", nullable = false)
    private Integer sortOrder;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Integer isActive;

    @Column(name = "CREATED_DT", nullable = false)
    private LocalDateTime createdDt;
}