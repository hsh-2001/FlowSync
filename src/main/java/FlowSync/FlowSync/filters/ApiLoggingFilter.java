package FlowSync.FlowSync.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ApiLoggingFilter extends OncePerRequestFilter {

    private static final Logger log =
            LoggerFactory.getLogger(ApiLoggingFilter.class);


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        ContentCachingRequestWrapper requestWrapper;
        requestWrapper = new ContentCachingRequestWrapper(request, 1024 * 1024);

        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper(response);


        long startTime = System.currentTimeMillis();


        filterChain.doFilter(requestWrapper, responseWrapper);


        long duration =
                System.currentTimeMillis() - startTime;


        String requestBody = new String(
                requestWrapper.getContentAsByteArray(),
                StandardCharsets.UTF_8
        );


        String responseBody = new String(
                responseWrapper.getContentAsByteArray(),
                StandardCharsets.UTF_8
        );


        log.info("""
                
                ===== API REQUEST =====
                Method: {}
                Endpoint: {}
                Request Body: {}
                
                ===== API RESPONSE =====
                Status: {}
                Response Body: {}
                Duration: {} ms
                
                """,
                request.getMethod(),
                request.getRequestURI(),
                requestBody,
                response.getStatus(),
                responseBody,
                duration
        );


        responseWrapper.copyBodyToResponse();
    }
}