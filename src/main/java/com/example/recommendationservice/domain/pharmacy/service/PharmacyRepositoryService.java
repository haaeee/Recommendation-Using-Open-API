package com.example.recommendationservice.domain.pharmacy.service;

import static java.util.stream.Collectors.toList;

import com.example.recommendationservice.domain.pharmacy.dto.PharmacyDto;
import com.example.recommendationservice.domain.pharmacy.entity.Pharmacy;
import com.example.recommendationservice.domain.pharmacy.repository.PharmacyRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PharmacyRepositoryService {

    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public void updateAddress(Long id, String address) {
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)) {
            log.error("[PharmacyService updateAddress] not found id : {}", id);
            return;
        }

        entity.changePharmacyAddress(address);
    }

    public List<PharmacyDto> findAll() {
        return pharmacyRepository.findAll().stream()
                .map(PharmacyDto::from)
                .collect(toList());
    }
}
