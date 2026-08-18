package FlowSync.FlowSync.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageRequestDto {
    private int page = 1;
    private int pageSize = 10;
}
