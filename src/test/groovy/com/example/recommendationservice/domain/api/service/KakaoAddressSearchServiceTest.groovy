package com.example.recommendationservice.domain.api.service

import com.example.recommendationservice.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "requestAddressSearch: null이 들어오면 null 반환한다."() {
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "requestAddressSearch: 빈칸이 들어오면 null 반환한다."() {
        given:
        String address = ""

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "requestAddressSearch: 유효한 주소값이 들어오면 KakaoApiResoponseDto 반환한다."() {
        given:
        String address = "서울 서초구 잠원동 10길"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentDtos.size() > 0
        result.metaDto.totalCount > 0
        result.documentDtos.get(0).addressName != null
    }
}
