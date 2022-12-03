package com.example.recommendationservice.domain.pharmacy.repository;

import com.example.recommendationservice.domain.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
