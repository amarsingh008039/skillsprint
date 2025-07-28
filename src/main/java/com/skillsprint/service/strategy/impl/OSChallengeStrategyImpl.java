package com.skillsprint.service.strategy.impl;

import com.skillsprint.entity.ChallengeMetadata;
import com.skillsprint.exception.ChallengeNotFoundException;
import com.skillsprint.model.ChallengeResponse;
import com.skillsprint.repository.ChallengeMetadataRepository;
import com.skillsprint.repository.ChallengeRepository;
import com.skillsprint.service.strategy.ChallengeStrategyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("OS")
public class OSChallengeStrategyImpl implements ChallengeStrategyInterface {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMetadataRepository metadataRepository;

    @Autowired
    public OSChallengeStrategyImpl(ChallengeRepository challengeRepository, ChallengeMetadataRepository challengeMetadataRepository) {
        this.challengeRepository = challengeRepository;
        this.metadataRepository = challengeMetadataRepository;
    }

    @Override
    public String getTopicName() {
        return "OS";
    }

    /**
     * @param response
     * @param difficulty
     */
    @Override
    public void enrichChallengeDetails(ChallengeResponse response, String difficulty) {
        Optional<ChallengeMetadata> meta = metadataRepository
                .findByTopicAndDifficulty("OS", difficulty.toUpperCase());
        meta.ifPresent(data -> {
            response.setHint(data.getHint());
            response.setCodeTemplate(data.getCodeTemplate());
            response.setTestCases(data.getTestCases());
        });
    }

    @Override
    public ChallengeResponse generateChallenge(String difficulty) {
       return challengeRepository.findByTopicIgnoreCaseAndDifficultyIgnoreCase("DSA",difficulty)
               .stream()
               .findAny()
               .map(ch -> new ChallengeResponse(ch.getTitle(),ch.getDescription(),ch.getDifficulty()))
               .orElseThrow(()->new ChallengeNotFoundException("No OS challenge found for difficulty: "+difficulty));
    }


}
