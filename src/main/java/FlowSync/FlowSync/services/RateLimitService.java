package FlowSync.FlowSync.services;

import FlowSync.FlowSync.entities.RateLimitEntity;
import FlowSync.FlowSync.repositories.interfaces.RateLimitRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateLimitService {

    private final RateLimitRepository rateLimitRepository;

    private final Map<String, RateLimitEntity> rules =
            new ConcurrentHashMap<>();

    private final AntPathMatcher pathMatcher =
            new AntPathMatcher();


    /**
     * Load rules when application starts.
     */
    @PostConstruct
    public void init() {

        log.info("Loading rate limit rules...");

        reloadRules();
    }


    /**
     * Reload rules every 1 minute.
     */
    @Scheduled(fixedDelay = 60000)
    public void scheduledReload() {

        log.info("Reloading rate limit rules...");

        reloadRules();
    }

    public void reloadRules() {

        List<RateLimitEntity> rateLimits = rateLimitRepository.findByIsActive(1);

        Map<String, RateLimitEntity> newRules = new ConcurrentHashMap<>();

        for (RateLimitEntity rule : rateLimits) {

            String key = buildRuleKey(
                    rule.getApiPattern(),
                    rule.getHttpMethod()
            );

            newRules.put(key, rule);
        }

        // Replace old cache
        rules.clear();
        rules.putAll(newRules);

        log.info("Rate limit rules loaded: {} rules", rules.size());
    }


    public RateLimitEntity findRule(
            String path,
            String method
    ) {

        RateLimitEntity rule = rules.get(buildRuleKey(path, method));

        if (rule != null) {
            return rule;
        }

        for (RateLimitEntity candidate : rules.values()) {

            if (!candidate.getHttpMethod()
                    .equalsIgnoreCase(method)) {

                continue;
            }

            if (matches(
                    candidate.getApiPattern(),
                    path
            )) {

                return candidate;
            }
        }

        return null;
    }


    private String buildRuleKey(
            String path,
            String method
    ) {

        return method.toUpperCase()
                + ":"
                + path;
    }


    private boolean matches(
            String pattern,
            String path
    ) {

        return pathMatcher.match(
                pattern,
                path
        );
    }
}