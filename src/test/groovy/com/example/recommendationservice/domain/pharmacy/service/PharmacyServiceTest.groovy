package com.example.recommendationservice.domain.pharmacy.service

import com.example.recommendationservice.AbstractIntegrationContainerBaseTest
import com.example.recommendationservice.domain.pharmacy.entity.Pharmacy
import com.example.recommendationservice.domain.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired

class PharmacyServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyService pharmacyService

    @Autowired
    private PharmacyRepository pharmacyRepository

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "updateAddress: dirty Checking 성공 경우"() {
        given:
        String address = "서울특별시 성북구 보문동"
        String modifiedAddress = "서울특별시 서초구 잠원동"
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
        // 트랜잭션 commit 되면서 insert 쿼리 발생
        def entity = pharmacyRepository.save(pharmacy)

        // 트랜잭션 commit 되면서 쿼리 발생
        pharmacyService.updateAddress(entity.getId(), modifiedAddress)

        // 다른 트랜잭션이기에 select 쿼리 발생
        println "==============================================================="
        def result = pharmacyRepository.findAll()
        println "==============================================================="

        then:
        result.get(0).getPharmacyAddress() == modifiedAddress
    }

    def "updateAddressWithoutTransaction: dirty Checking 실패 경우"() {
        given:
        String address = "서울특별시 성북구 보문동"
        String modifiedAddress = "서울특별시 서초구 잠원동"
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
        // 트랜잭션 commit 되면서 insert 쿼리 발생
        def entity = pharmacyRepository.save(pharmacy)

        // 트랜잭션 commit 되면서 쿼리 발생
        pharmacyService.updateAddressWithoutTransaction(entity.getId(), modifiedAddress)

        // 다른 트랜잭션이기에 select 쿼리 발생
        println "==============================================================="
        def result = pharmacyRepository.findAll()
        println "==============================================================="

        then:
        result.get(0).getPharmacyAddress() == address
    }

    def "self invocation"() {
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
        pharmacyService.bar(Arrays.asList(pharmacy))

        then:
        def e = thrown(RuntimeException.class)
        def result = pharmacyRepository.findAll()
        result.isEmpty()  // 트랜잭션이 적용되지 않는다. (롤백이 안됨)
    }
}
