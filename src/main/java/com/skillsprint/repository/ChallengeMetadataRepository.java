package com.skillsprint.repository;

import com.skillsprint.entity.ChallengeMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengeMetadataRepository extends JpaRepository<ChallengeMetadata,Long> {
    Optional<ChallengeMetadata> findByTopicAndDifficulty(String topic, String difficulty);
}
