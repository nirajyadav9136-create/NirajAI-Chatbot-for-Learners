package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
}
