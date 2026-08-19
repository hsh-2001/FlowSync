package FlowSync.FlowSync.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TMS_PROJ_STATUS")
public class EProjectStatusEntity {

    @Id
    @Column(name = "STATUS_CODE", length = 20)
    private String statusCode;

    @Column(name = "STATUS_NAME", nullable = false, length = 100)
    private String statusName;

    @Column(name = "STATUS_ORDER", nullable = false)
    private Integer statusOrder;

    @Column(name = "STATUS_COLOR", length = 20)
    private String statusColor;
}