package FlowSync.FlowSync.dto;

import lombok.Builder;

@Builder
public record ProjectStatusResponse(
        String statusCode,
        String statusName,
        String statusColor,
        Integer statusOrder,
        Integer test
) {
}