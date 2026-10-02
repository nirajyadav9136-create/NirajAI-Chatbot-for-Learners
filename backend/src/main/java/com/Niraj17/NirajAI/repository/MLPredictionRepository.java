package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.MLPrediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MLPredictionRepository extends JpaRepository<MLPrediction, UUID> {
}
