package io.jayaprabahar.camunda.ecommerce.common;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

/**
 * Lightweight HTTP helper used by example services to call other microservices.
 *
 * <p>Refactored to reuse a single `WebClient` instance, apply a sensible
 * timeout, and provide synchronous, deterministic results for callers that
 * expect a boolean response.
 */
@UtilityClass
@Slf4j
public class ECommerceWebClient {

    private static final WebClient CLIENT = WebClient.builder()
            .build();

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    public boolean isPostResponseAccepted(String url, Object body) {
        try {
            var entity = CLIENT.post()
                    .uri(url)
                    .body(BodyInserters.fromValue(body))
                    .retrieve()
                    .toBodilessEntity()
                    .block(DEFAULT_TIMEOUT);

            return entity != null && entity.getStatusCode() == HttpStatus.ACCEPTED;
        } catch (Exception e) {
            log.error("POST request to {} failed: {}", url, e.getMessage());
            log.debug("POST request failure details", e);
            return false;
        }
    }

    public boolean isPatchResponseAccepted(String url) {
        try {
            var entity = CLIENT.patch()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity()
                    .block(DEFAULT_TIMEOUT);

            return entity != null && entity.getStatusCode() == HttpStatus.ACCEPTED;
        } catch (Exception e) {
            log.error("PATCH request to {} failed: {}", url, e.getMessage());
            log.debug("PATCH request failure details", e);
            return false;
        }
    }
}
