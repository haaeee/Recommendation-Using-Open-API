package com.example.recommendationservice.domain.place.repository;

import com.example.recommendationservice.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
