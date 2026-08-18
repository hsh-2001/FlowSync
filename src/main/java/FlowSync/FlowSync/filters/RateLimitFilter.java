package FlowSync.FlowSync.filters;

import FlowSync.FlowSync.entities.RateLimitEntity;
import FlowSync.FlowSync.models.BaseResponse;
import FlowSync.FlowSync.services.RateLimitService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;
    private final ObjectMapper objectMapper;

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        RateLimitEntity rule = rateLimitService.findRule(path, method);

        // No configuration → no rate limit
        if (rule == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = getClientIp(request);

        String bucketKey =
                clientIp + ":" +
                        method + ":" +
                        rule.getApiPattern();

        Bucket bucket = buckets.computeIfAbsent(
                bucketKey,
                key -> createBucket(rule)
        );

        if (bucket.tryConsume(1)) {

            filterChain.doFilter(request, response);
            return;
        }

        handleTooManyRequests(response);
    }

    private Bucket createBucket(
            RateLimitEntity rule
    ) {

        return Bucket.builder()
                .addLimit(limit -> limit
                        .capacity(rule.getMaxRequests())
                        .refillGreedy(
                                rule.getMaxRequests(),
                                Duration.ofSeconds(
                                        rule.getWindowSeconds()
                                )
                        )
                )
                .build();
    }

    private void handleTooManyRequests(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        var result = BaseResponse.failed("TOO_MANY_REQUESTS");

        response.getWriter().write(
                objectMapper.writeValueAsString(result)
        );
    }

    private String getClientIp(
            HttpServletRequest request
    ) {

        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor
                    .split(",")[0]
                    .trim();
        }

        return request.getRemoteAddr();
    }
}