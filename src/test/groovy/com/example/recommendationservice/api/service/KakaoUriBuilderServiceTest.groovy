package com.example.recommendationservice.api.service

import com.example.recommendationservice.domain.api.service.KakaoUriBuilderService
import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoUriBuilderServiceTest extends Specification {

    private KakaoUriBuilderService kakaoUriBuilderService

    def setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService()
    }

    def "buildUriByAddressSearch: 한글 파라미터의 경우 정상적으로 인코딩"() {
        given:
        String address = "서울 서초구"
        def charset = StandardCharsets.UTF_8

        // 정적 언어: String uri = kakaoUriBuilderService.buildUriByAddressSearch(address)
        when:
        def uri = kakaoUriBuilderService.buildUriByAddressSearch(address)  // groovy 동적 타입의 언어
        def decodedResult = URLDecoder.decode(uri.toString(), charset)

        then:
        decodedResult == "https://dapi.kakao.com/v2/local/search/address.json?query=서울 서초구"
    }
}
