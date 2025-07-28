package com.skillsprint.service;

import com.skillsprint.entity.ChallengeEntity;
import com.skillsprint.exception.ChallengeNotFoundException;
import com.skillsprint.model.ChallengeRequest;
import com.skillsprint.model.ChallengeResponse;
import com.skillsprint.repository.ChallengeRepository;
import com.skillsprint.service.strategy.ChallengeStrategyInterface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ChallengeService {
    private final Map<String, ChallengeStrategyInterface> strategies;

    private final ChallengeRepository challengeRepository;
    @Autowired
    public ChallengeService(List<ChallengeStrategyInterface> strategyList,ChallengeRepository challengeRepository) {
        Map<String, ChallengeStrategyInterface> temp = new HashMap<>();
        for (ChallengeStrategyInterface strategy : strategyList) {
            temp.put(strategy.getTopicName().toUpperCase(), strategy);
        }
        this.strategies = Collections.unmodifiableMap(temp); // makes it immutable
        this.challengeRepository = challengeRepository;
    }

    @PostConstruct
    public void validateStrategies() {
        if (strategies.isEmpty()) {
            throw new IllegalStateException("No challenge strategies registered.");
        }
        strategies.forEach((k, v) -> System.out.println("Strategy registered: " + k));
    }

    public ChallengeEntity createChallenge(ChallengeEntity challenge) {
        return challengeRepository.save(challenge);
    }

    public List<ChallengeEntity> getAllChallenges() {
        return challengeRepository.findAll();
    }

    public ChallengeEntity getChallengeById(Long id) {
        return challengeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Challenge not found"));
    }

    public ChallengeResponse getChallenge(ChallengeRequest req){
        String skill = req.getSkill().toUpperCase();

        // Step 1: Try to find existing challenge in DB
        Optional<ChallengeEntity> optionalChallenge = challengeRepository
                .findFirstByTopicIgnoreCaseAndDifficultyIgnoreCase(skill, req.getDifficulty());

        if (optionalChallenge.isPresent()) {
            ChallengeEntity chosen = optionalChallenge.get();
            return new ChallengeResponse(chosen.getTitle(), chosen.getDescription(), chosen.getDifficulty());
        }
        // Step 2: Fallback to strategy if no DB match
        if (!strategies.containsKey(skill)) {
            throw new ChallengeNotFoundException("No strategy found for skill: " + skill);
        }

        ChallengeResponse generated = strategies.get(skill).generateChallenge(req.getDifficulty());
        ChallengeEntity challengeEntity = ChallengeEntity.builder()
                .title(generated.getTitle())
                .description(generated.getDescription())
                .difficulty(req.getDifficulty())
                .topic(skill)
                .build();

        challengeRepository.save(challengeEntity);

        return generated;
    }

    public ChallengeResponse getChallengeWithMetaData(ChallengeRequest req) {
        String skill = req.getSkill().toUpperCase();
        String difficulty = req.getDifficulty().toUpperCase();

        ChallengeEntity entity = challengeRepository
                .findFirstByTopicIgnoreCaseAndDifficultyIgnoreCase(skill, difficulty)
                .orElseThrow(() -> new ChallengeNotFoundException("No challenge found."));

        ChallengeResponse response = new ChallengeResponse(
                entity.getTitle(),
                entity.getDescription(),
                entity.getDifficulty(),
                null, null, null // Placeholder for hint/template/testcases
        );

        // Call strategy to enrich details
        ChallengeStrategyInterface strategy = strategies.get(skill);
        if (strategy != null) {
            strategy.enrichChallengeDetails(response, difficulty);
        }

        return response;
    }
}
