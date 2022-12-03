package com.example.recommendationservice.domain.pharmacy.entity;

import static javax.persistence.GenerationType.IDENTITY;

import com.example.recommendationservice.domain.place.entity.Place;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Pharmacy extends Place {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String pharmacyName;

    private String pharmacyAddress;

    protected Pharmacy() {
    }

    @Builder
    private Pharmacy(final double latitude, final double longitude, final String pharmacyName,
                     final String pharmacyAddress) {
        super(latitude, longitude);
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
    }
}
