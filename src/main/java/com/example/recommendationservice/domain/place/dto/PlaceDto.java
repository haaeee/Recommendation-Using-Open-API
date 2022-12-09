package com.example.recommendationservice.domain.place.dto;

import com.example.recommendationservice.domain.pharmacy.entity.Pharmacy;
import lombok.Getter;

@Getter
public abstract class PlaceDto {

    private final Long id;
    private final double latitude;
    private final double longitude;

    // == 생성 메소드 == //
    public PlaceDto(Long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PlaceDto(Pharmacy pharmacy) {
        this.id = pharmacy.getId();
        this.latitude = pharmacy.getLatitude();
        this.longitude = pharmacy.getLongitude();
    }
}
