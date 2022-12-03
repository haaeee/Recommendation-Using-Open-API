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

    private final static String KAKAO_LOCAL_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public URI buildUriByAddressSearch(final String address) {
        final URI uri = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_URL)
                .queryParam("query", address)
                .build()
                .encode()
                .toUri();

        log.info("[KakaoUriBuilderService$buildUriByAddressSearch] address: {}, uri: {}", address, uri);

        return uri;
    }
}
