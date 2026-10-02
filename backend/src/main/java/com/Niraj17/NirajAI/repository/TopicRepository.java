package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {

    List<Topic> findAllByOrderByCategoryAscNameAsc();

    Optional<Topic> findBySlug(String slug);
}
