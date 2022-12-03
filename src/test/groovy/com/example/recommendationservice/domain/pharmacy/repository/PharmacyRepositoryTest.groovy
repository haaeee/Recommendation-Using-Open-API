package com.example.recommendationservice.domain.pharmacy.repository

import com.example.recommendationservice.AbstractIntegrationContainerBaseTest
import com.example.recommendationservice.domain.pharmacy.entity.Pharmacy
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "pharmacyRepository save"() {
        given:
        String address = "서울특별시 성북구 보문동"
        String name = "가까운 약국"
        double latitude = 37.58
        double longitude = 127.02

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = pharmacyRepository.saveAndFlush(pharmacy)

        then:
        result.getId() == 1L
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }

    def "pharmacyRepository saveAll"() {
        given:
        String address = "서울특별시 성북구 보문동"
        String name = "가까운 약국"
        double latitude = 37.58
        double longitude = 127.02

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = pharmacyRepository.saveAll(List.of(pharmacy))


        then:
        result.size() == 1
    }
}
