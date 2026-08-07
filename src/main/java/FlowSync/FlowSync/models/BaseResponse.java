package FlowSync.FlowSync.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BaseResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;


    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(
                true,
                message,
                data,
                LocalDateTime.now()
        );
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(
                true,
                "Success",
                data,
                LocalDateTime.now()
        );
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(
                false,
                message,
                null,
                LocalDateTime.now()
        );
    }
}