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

    def "정상적인 주소를 입력했을 경우, 정상적으로 위도 경도로 변환 된다."() {
        given:
        boolean actualResult = false

        when:
        def searchResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)
        println searchResult
        then:
        if (searchResult == null) actualResult = false
        else actualResult = searchResult.getDocumentDtos().size() > 0

        where:
        inputAddress         | expectedResult
        "서울 특별시 성북구 종암동"     | true
        "서울 성북구 종암동 91"      | true
        "서울 대학로"             | true
        "서울 성북구 종암동 잘못된 주소"  | false
        "광진구 구의동 251-45"     | true
        "광진구 구의동 251-455555" | false
        ""                   | false
    }
}
