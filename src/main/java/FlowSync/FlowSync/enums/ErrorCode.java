package FlowSync.FlowSync.enums;

public enum ErrorCode {
    SUCCESS("0"),
    NOT_FOUND("404"),
    INVALID_REQUEST("4000"),
    VALIDATION_ERROR("4001"),
    INTERNAL_SERVER_ERROR("5000"),

    UNAUTHORIZED("4010"),
    FORBIDDEN("4030"),


    ORDER_NOT_FOUND("1001"),
    ORDER_ALREADY_EXISTS("1002"),
    INVALID_ORDER_STATUS("1003"),
    STATUS_NOT_FOUND("1005"),
    STATUS_ALREADY_EXISTS("1006"),
    STATUS_ALREADY_USED("1007"),

    USER_NOT_FOUND("2001"),
    USER_ALREADY_EXISTS("2002"),

    DATABASE_ERROR("9001");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public Integer getCode() {
        return Integer.parseInt(code);
    }
}
