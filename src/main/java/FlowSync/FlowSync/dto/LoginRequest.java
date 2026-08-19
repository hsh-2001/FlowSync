package FlowSync.FlowSync.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Schema(example = "senghong")
    private String username;
    @Schema(example = "SengHong123")
    private String password;
}
