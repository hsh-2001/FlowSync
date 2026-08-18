package FlowSync.FlowSync.dto;

import FlowSync.FlowSync.enums.ErrorCode;
import FlowSync.FlowSync.models.BaseResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasePageResponse<T> extends BaseResponse<List<T>> {

    private long totalCount;
    private int totalPage;
    private int page;
    private int pageSize;

    public BasePageResponse(
            boolean success,
            String message,
            List<T> data,
            Integer errorCode,
            long totalCount,
            int totalPage,
            int page,
            int pageSize
    ) {
        super(success, message, data, errorCode, java.time.LocalDateTime.now());
        this.totalCount = totalCount;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
    }

    public static <T> BasePageResponse<T> success(List<T> data, Long totalCount, int totalPage, int page, int pageSize) {
        return new BasePageResponse<>(
                true,
                "Success",
                data,
                ErrorCode.SUCCESS.getCode(),
                totalCount,
                totalPage,
                page,
                pageSize
        );
    }
}