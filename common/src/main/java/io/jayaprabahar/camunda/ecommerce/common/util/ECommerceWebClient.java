package io.jayaprabahar.camunda.ecommerce.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Objects;

/**
 * Lightweight HTTP helper used by example services to call other microservices.
 *
 * <p>Internally uses Spring WebFlux's `WebClient` to perform simple POST and
 * PATCH calls and returns the resulting `HttpStatus`. This utility is
 * intentionally small for the reference implementation — in production prefer
 * a resilient client with retries, circuit breakers and better error
 * propagation (e.g. Resilience4j or Spring Cloud Circuit Breaker).
 */
@UtilityClass
@Slf4j
public class ECommerceWebClient {

    /**
     * Performs a POST request and returns the response status.
     *
     * @param url  target service URL
     * @param body request body (will be serialized)
     * @return HttpStatus returned by the remote service or
     *         {@code HttpStatus.INTERNAL_SERVER_ERROR} on failure
     */
    public HttpStatus isPostResponseAccepted(String url, Object body) {
        return getResponseCode(WebClient.create().post().uri(url).body(BodyInserters.fromValue(body)).retrieve());
    }

    /**
     * Performs a PATCH request and returns the response status.
     *
     * @param url target service URL
     * @return HttpStatus returned by the remote service or
     *         {@code HttpStatus.INTERNAL_SERVER_ERROR} on failure
     */
    public HttpStatus isPatchResponseAccepted(String url) {
        return getResponseCode(WebClient.create().patch().uri(url).retrieve());
    }

    private HttpStatus getResponseCode(WebClient.ResponseSpec responseSpec) {
        try {
            return Objects.requireNonNull(responseSpec
                    .toBodilessEntity()
                    .block(Duration.ofSeconds(30))).getStatusCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
