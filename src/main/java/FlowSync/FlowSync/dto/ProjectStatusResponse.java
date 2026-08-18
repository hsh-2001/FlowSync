package FlowSync.FlowSync.dto;

public record ProjectStatusResponse(
        String statusCode,
        String statusName,
        String statusColor,
        Integer statusOrder,
        Integer test
) {
}