package com.example.recommendationservice.domain.api.service;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class KakaoUriBuilderService {

    private static final String KAKAO_LOCAL_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    public URI buildUriByAddressSearch(String address) {
        final URI uri = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_URL)
                .queryParam("query", address)
                .build()
                .encode()
                .toUri();

        log.info("[KakaoUriBuilderService$buildUriByAddressSearch] address: {}, uri: {}", address, uri);

        return uri;
    }

    public URI buildUriByCategorySearch(String category, double latitude, double longitude, double radius) {

        // km -> m
        double meterRadius = 1000 * radius;

        URI uri = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_URL)
                .queryParam("category_group_code", category)
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .queryParam("radius", meterRadius)
                .queryParam("sort", "distance")
                .build()
                .encode()
                .toUri();

        log.info("[KakaoUriBuilderService$buildUriByCategorySearch] uri: {}", uri);

        return uri;
    }
}
