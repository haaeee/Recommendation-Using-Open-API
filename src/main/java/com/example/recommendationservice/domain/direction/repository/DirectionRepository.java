package com.example.recommendationservice.domain.direction.repository;

import com.example.recommendationservice.domain.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
