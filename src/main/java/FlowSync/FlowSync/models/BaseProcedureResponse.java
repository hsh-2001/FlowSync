package FlowSync.FlowSync.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseProcedureResponse {

    private String message;
    private Integer errorCode;

    public BaseProcedureResponse(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public BaseProcedureResponse(Object result) {
        Object[] values = (Object[]) result;

        this.message = (String) values[0];
        this.errorCode = ((Number) values[1]).intValue();
    }
}