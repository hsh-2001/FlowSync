package FlowSync.FlowSync.models;

import java.time.LocalDateTime;

import FlowSync.FlowSync.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private Integer errorCode;
    private LocalDateTime timestamp;


    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(
                true,
                message,
                data,
                0,
                LocalDateTime.now()
        );
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(
                true,
                "Success",
                data,
                ErrorCode.SUCCESS.getCode(),
                LocalDateTime.now()
        );
    }

    public static <T> BaseResponse<T> failed(String message) {
        return new BaseResponse<>(
                false,
                message,
                null,
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                LocalDateTime.now()
        );
    }

    public static <T> BaseResponse<T> failed(String message, Integer errorCode) {
        return new BaseResponse<>(
                false,
                message,
                null,
                errorCode,
                LocalDateTime.now()
        );
    }
}