package FlowSync.FlowSync.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String token;
}
