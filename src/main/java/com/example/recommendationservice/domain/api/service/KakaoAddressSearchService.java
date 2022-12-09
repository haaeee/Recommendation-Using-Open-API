package com.example.recommendationservice.domain.api.service;

import com.example.recommendationservice.domain.api.dto.KakaoApiResponseDto;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class KakaoAddressSearchService {

    private final RestTemplate restTemplate;
    private final KakaoUriBuilderService kakaoUriBuilderService;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000)
    )
    public KakaoApiResponseDto requestAddressSearch(final String address) {
        if (ObjectUtils.isEmpty(address) || address.isBlank()) {
            return null;
        }

        final URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);

        final HttpEntity httpEntity = new HttpEntity<>(headers);

        // kakao api 호출
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class)
                .getBody();
    }

    @Recover
    public KakaoApiResponseDto recover(RuntimeException e, String address) {
        log.error("All the retries failed. Request Address: {}, error: {}", address, e.getMessage());
        return null;
    }
}
