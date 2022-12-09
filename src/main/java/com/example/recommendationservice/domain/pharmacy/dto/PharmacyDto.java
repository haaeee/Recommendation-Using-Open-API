package com.example.recommendationservice.domain.pharmacy.dto;

import com.example.recommendationservice.domain.pharmacy.entity.Pharmacy;
import com.example.recommendationservice.domain.place.dto.PlaceDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PharmacyDto extends PlaceDto {

    private final String pharmacyName;
    private final String pharmacyAddress;

    @Builder
    public PharmacyDto(Long id, double latitude, double longitude, String pharmacyName, String pharmacyAddress) {
        super(id, latitude, longitude);
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
    }

    public PharmacyDto(Pharmacy pharmacy) {
        super(pharmacy);
        this.pharmacyName = pharmacy.getPharmacyName();
        this.pharmacyAddress = pharmacy.getPharmacyAddress();
    }

    public static PharmacyDto from(Pharmacy entity) {
        return PharmacyDto.builder()
                .id(entity.getId())
                .pharmacyName(entity.getPharmacyName())
                .pharmacyAddress(entity.getPharmacyAddress())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}
