package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecommendationRepository extends JpaRepository<Recommendation, UUID> {
    List<Recommendation> findAllByUserIdOrderByPriorityAscCreatedAtDesc(UUID userId);
}
