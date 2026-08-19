package FlowSync.FlowSync.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TMS_USERS")
public class EUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_CODE", length = 8)
    private String userCode;

    @Column(name = "NAME", length = 20)
    private String name;

    @Column(name = "USERNAME", length = 50)
    private String username;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 250)
    private String password;

    @Column(name = "CREATED_DT", nullable = false)
    private LocalDateTime createdDt;

    @Column(name = "UPDATED_DT")
    private LocalDateTime updatedDt;

    @Column(name = "DELETED_DT")
    private LocalDateTime deletedDt;

    @Column(name = "RULE_ID")
    private Integer ruleId;

    @Column(name = "GRP_ID", length = 3)
    private String grpId;
}