package com.Niraj17.NirajAI.repository;

import com.Niraj17.NirajAI.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserActivityRepository extends JpaRepository<UserActivity, UUID> {
}
