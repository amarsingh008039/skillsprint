package com.skillsprint.repository;

import com.skillsprint.entity.ChallengeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeEntity,Long> {

    List<ChallengeEntity> findByTopicIgnoreCaseAndDifficultyIgnoreCase(String topic, String difficulty);

    Optional<ChallengeEntity> findFirstByTopicIgnoreCaseAndDifficultyIgnoreCase(String skill, String difficulty);
}
