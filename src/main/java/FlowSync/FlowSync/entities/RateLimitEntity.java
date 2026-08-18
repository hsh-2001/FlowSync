package FlowSync.FlowSync.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TMS_RATE_LIMIT")
@Getter
@Setter
public class RateLimitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "API_PATTERN")
    private String apiPattern;

    @Column(name = "HTTP_METHOD")
    private String httpMethod;

    @Column(name = "MAX_REQUESTS")
    private Integer maxRequests;

    @Column(name = "WINDOW_SECONDS")
    private Integer windowSeconds;

    @Column(name = "IS_ACTIVE")
    private Integer isActive;
}