package com.agrihelp.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@Component
public class ApiRetryUtil {

    private static final int DEFAULT_MAX_RETRIES = 3;
    private static final long DEFAULT_DELAY_MS = 1000; // 1 second

    /**
     * Executes the supplier with retry logic using default retries and delay.
     *
     * @param supplier Function that makes the API call
     * @param <T>      Return type
     * @return Result from supplier
     */
    public <T> T executeWithRetry(Supplier<T> supplier) {
        return executeWithRetry(supplier, DEFAULT_MAX_RETRIES, DEFAULT_DELAY_MS);
    }

    /**
     * Executes supplier with configurable retries and delay.
     *
     * @param supplier   Function that makes the API call
     * @param maxRetries Max number of retries
     * @param delayMs    Delay between retries in milliseconds
     * @param <T>        Return type
     * @return Result from supplier
     */
    public <T> T executeWithRetry(Supplier<T> supplier, int maxRetries, long delayMs) {
        int attempt = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (RuntimeException e) { // ✅ Catch only RuntimeException (includes RestClientException)
                attempt++;
                if (attempt > maxRetries) {
                    throw e;
                }
                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted during API retry", ie);
                }
            }
        }
    }

    /**
     * Executes RestTemplate GET request with retry
     */
    public <T> T getForObjectWithRetry(RestTemplate restTemplate, String url, Class<T> responseType, Object... uriVariables) {
        return executeWithRetry(() -> restTemplate.getForObject(url, responseType, uriVariables));
    }
}
