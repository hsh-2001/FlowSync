package FlowSync.FlowSync.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String userCode;
    private String name;
    private String username;
    private String email;
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;
    private Integer ruleId;
    private String grpId;
}
