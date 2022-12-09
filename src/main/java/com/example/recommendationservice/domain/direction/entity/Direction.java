package com.example.recommendationservice.domain.direction.entity;

import static javax.persistence.GenerationType.IDENTITY;

import com.example.recommendationservice.global.entity.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Direction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    // 사용자
    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    // 장소
    private String targetPlaceName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    // 사용자와 장소 까지의 거리
    private double distance;

    protected Direction() {
    }

    @Builder
    public Direction(Long id, String inputAddress, double inputLatitude, double inputLongitude, String targetPlaceName,
                     String targetAddress, double targetLatitude, double targetLongitude, double distance) {
        this.id = id;
        this.inputAddress = inputAddress;
        this.inputLatitude = inputLatitude;
        this.inputLongitude = inputLongitude;
        this.targetPlaceName = targetPlaceName;
        this.targetAddress = targetAddress;
        this.targetLatitude = targetLatitude;
        this.targetLongitude = targetLongitude;
        this.distance = distance;
    }
}
