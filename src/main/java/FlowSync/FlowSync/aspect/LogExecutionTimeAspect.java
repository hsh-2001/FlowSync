package FlowSync.FlowSync.aspect;

import FlowSync.FlowSync.anotations.LogExecutionTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LogExecutionTimeAspect {
    private static final Logger log = LoggerFactory.getLogger(LogExecutionTimeAspect.class);

    @Around("@annotation(logExecutionTime)")
    public Object logTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Map<String, Object> logMapping = new HashMap<String, Object>();
        for (int i = 0; i < parameterNames.length; i++) {
            String paramName = parameterNames[i];
            Object value = "password".equalsIgnoreCase(paramName) ? "****" : args[i];
            logMapping.put(paramName, value);
        }
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("Request: {} | Method: {} | Executed in {}ms | Status: SUCCESS",
                logMapping, signature.getMethod().getName(), executionTime);
        return result;
    }
}