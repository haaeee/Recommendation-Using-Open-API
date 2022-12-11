package com.example.recommendationservice.domain.direction.service;

import static java.util.stream.Collectors.toList;

import com.example.recommendationservice.domain.api.dto.DocumentDto;
import com.example.recommendationservice.domain.api.service.KakaoCategorySearchService;
import com.example.recommendationservice.domain.direction.entity.Direction;
import com.example.recommendationservice.domain.direction.repository.DirectionRepository;
import com.example.recommendationservice.domain.pharmacy.service.PharmacySearchService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3;  // 최대 검색 갯수
    private static final double RADIUS_KM = 10.0;  // 반경 10KM

    private final PharmacySearchService pharmacySearchService;
    private final DirectionRepository directionRepository;
    private final KakaoCategorySearchService kakaoCategorySearchService;

    /**
     * <h3>약국 데이터 중 가까운 3곳을 생성하는 메서드</h3>
     *
     * @param documentDto : kakaoAPI has user latitude, longitude
     */
    public List<Direction> buildDirectionList(DocumentDto documentDto) {

        if (Objects.isNull(documentDto)) {
            return Collections.emptyList();
        }

        return pharmacySearchService.searchPharmacyDtos().stream()
                .map(pharmacyDto ->
                        Direction.builder().
                                inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetPlaceName(pharmacyDto.getPharmacyName())
                                .targetAddress(pharmacyDto.getPharmacyAddress())
                                .targetLatitude(pharmacyDto.getLatitude())
                                .targetLongitude(pharmacyDto.getLongitude())
                                .distance(
                                        calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                                pharmacyDto.getLatitude(), pharmacyDto.getLongitude())
                                )
                                .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(toList());
    }

    @Transactional
    public List<Direction> saveAll(List<Direction> directions) {
        if (CollectionUtils.isEmpty(directions)) {
            return Collections.emptyList();
        }
        return directionRepository.saveAll(directions);
    }

    public List<Direction> buildDirectionsByCategoryApi(DocumentDto inputDocumentDto) {

        if (Objects.isNull(inputDocumentDto)) {
            return Collections.emptyList();
        }

        return kakaoCategorySearchService.requestPharmacyCategorySearch(inputDocumentDto.getLatitude(),
                        inputDocumentDto.getLongitude(), RADIUS_KM)
                .getDocumentDtos().stream()
                .map(resultDocumentDto ->
                        Direction.builder()
                                .inputAddress(inputDocumentDto.getAddressName())
                                .inputLatitude(inputDocumentDto.getLatitude())
                                .inputLongitude(inputDocumentDto.getLongitude())
                                .targetPlaceName(resultDocumentDto.getPlaceName())
                                .targetAddress(resultDocumentDto.getAddressName())
                                .targetLatitude(resultDocumentDto.getLatitude())
                                .targetLongitude(resultDocumentDto.getLongitude())
                                .distance(resultDocumentDto.getDistance() * 0.001) // km 단위
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(toList());
    }

    /**
     * <h3>Haversine formula</h3>
     *
     * @param lat1 : user latitude
     * @param lon1 : user longitude
     * @param lat2 : place latitude
     * @param lon2 : place longitude
     * @return distance
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(
                Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
