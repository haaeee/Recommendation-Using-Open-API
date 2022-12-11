package com.example.recommendationservice.domain.pharmacy.service;

import com.example.recommendationservice.domain.api.dto.DocumentDto;
import com.example.recommendationservice.domain.api.dto.KakaoApiResponseDto;
import com.example.recommendationservice.domain.api.service.KakaoAddressSearchService;
import com.example.recommendationservice.domain.direction.entity.Direction;
import com.example.recommendationservice.domain.direction.service.DirectionService;
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
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendPharmacies(String address) {

        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentDtos())) {
            log.error("[PharmacyRecommendationService$recommendPharmacies fail], Input Address: {}", address);
            return;
        }

        // 한 군데만 pick
        DocumentDto documentDto = kakaoApiResponseDto.getDocumentDtos().get(0);

        // DB를 통한 조회
        List<Direction> directions = directionService.buildDirectionList(documentDto);

        // 고객의 요청과 요청에 해당하는 약국 정보를 DB에 저장
        directionService.saveAll(directions);
    }
}
