package com.example.recommendationservice.domain.pharmacy.service;

import static java.util.stream.Collectors.toList;

import com.example.recommendationservice.domain.pharmacy.dto.PharmacyDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyRepositoryService pharmacyRepositoryService;

    public List<PharmacyDto> searchPharmacyDtos() {
        // db
        return pharmacyRepositoryService.findAll().stream()
                .map(PharmacyDto::from)
                .collect(toList());
    }
}
