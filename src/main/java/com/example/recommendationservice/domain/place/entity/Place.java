package com.example.recommendationservice.domain.place.entity;

import static javax.persistence.InheritanceType.JOINED;

import com.example.recommendationservice.global.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import lombok.Getter;

@Getter
@Inheritance(strategy = JOINED)
@DiscriminatorColumn
@Entity
public abstract class Place extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "place_id")
    private Long id;

    private double latitude;

    private double longitude;

    protected Place() {
    }

    public Place(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
