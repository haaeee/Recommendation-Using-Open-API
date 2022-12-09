package com.example.recommendationservice.domain.direction.service

import com.example.recommendationservice.domain.api.dto.DocumentDto
import com.example.recommendationservice.domain.pharmacy.dto.PharmacyDto
import com.example.recommendationservice.domain.pharmacy.service.PharmacySearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {

    private PharmacySearchService pharmacySearchService = Mock()

    private DirectionService directionService = new DirectionService(pharmacySearchService)

    private List<PharmacyDto> pharmacyDtos

    void setup() {
        pharmacyDtos = new ArrayList<>()
        pharmacyDtos.addAll(
                PharmacyDto.builder()
                        .pharmacyName("호수온누리약국")
                        .pharmacyAddress("서울특별시 성북구 동소문로47길 12, 1층 (길음동)")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build()
                ,
                PharmacyDto.builder()
                        .pharmacyName("돌곶이온누리약국")
                        .pharmacyAddress("서울특별시 성북구 화랑로 248, 장위뉴타워 102호, 102-1호 (석관동)")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build()
        )
    }

    def "buildDirections - 결과 값이 거리 순으로 정렬이 되는지 확인"() {
        given:
        def addressName = "서울 성복구 종암로 10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.of(null, addressName, inputLatitude, inputLongitude, null)

        when:
        pharmacySearchService.searchPharmacyDtos() >> pharmacyDtos  // mock

        def result = directionService.buildDirectionList(documentDto)

        then:
        result.size() == 2
        result.get(0).targetPlaceName == "호수온누리약국"
        result.get(1).targetPlaceName == "돌곶이온누리약국"

    }

    def "buildDirections - 정해진 반경 10 km 내에 검색이 되는지 확인"() {
        given:
        pharmacyDtos.add(
                PharmacyDto.builder()
                        .id(3L)
                        .pharmacyName("더블유 약국")
                        .pharmacyAddress("서울 동작구 양녕로 264")
                        .latitude(37.5033084382313)
                        .longitude(126.948568078474)
                        .build()
        )

        def addressName = "서울 성복구 종암로 10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.of(null, addressName, inputLatitude, inputLongitude, null)

        when:
        pharmacySearchService.searchPharmacyDtos() >> pharmacyDtos  // mock

        def result = directionService.buildDirectionList(documentDto)

        then:
        result.size() == 2
        result.get(0).targetName == "호수온누리약국"
        result.get(1).targetName == "돌곶이온누리약국"
    }
}
